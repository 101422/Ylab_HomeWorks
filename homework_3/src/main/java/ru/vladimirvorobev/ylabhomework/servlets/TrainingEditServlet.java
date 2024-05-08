package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.mappers.PersonMapper;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingAdditionalInformationMapper;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingMapper;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingTypeMapper;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.security.Role;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.PersonValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingAdditionalInformationValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingTypeValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingValidator;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет редактирования тренировок.
 **/
public class TrainingEditServlet extends HttpServlet {

    private final TrainingService trainingService = new TrainingService();
    ObjectMapper objectMapper = new ObjectMapper();
    private final TrainingValidator trainingValidator = new TrainingValidator();

    private final TrainingAdditionalInformationValidator trainingAdditionalInformationValidator = new TrainingAdditionalInformationValidator();

    private final AuthorizationService authorizationService = new AuthorizationService();

    public TrainingEditServlet() throws IOException {
    }

    /**
     * POST-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Date date = java.sql.Date.valueOf(request.getParameter("date"));
        String trainingTypeName = request.getParameter("trainingType");

        HashMap<String, Boolean> credentials = authorizationService.login(name, password);

        if (credentials.get("isAuthorized")) {

            if (date == null || trainingTypeName.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            else {
                List<HashMap<String, String>> additionalInformation = new ArrayList<>();

                String body = request.getReader().lines().collect(Collectors.joining());

                TrainingDTO trainingDTO = objectMapper.readValue(body, TrainingDTO.class);

                List<String> trainingValidatorResult = trainingValidator.validate(trainingDTO);

                if (trainingValidatorResult.isEmpty()) {
                    trainingDTO.getTrainingAdditionalInformation().stream().forEach(trainingAdditionalInformationDTO -> {
                        List<String> trainingAdditionalInformationValidatorResult = trainingAdditionalInformationValidator.validate(trainingAdditionalInformationDTO);

                        if (trainingAdditionalInformationValidatorResult.isEmpty()) {
                            HashMap<String, String> trainingAdditionalInformationMap = new HashMap<>();

                            trainingAdditionalInformationMap.put(trainingAdditionalInformationDTO.getKey(), trainingAdditionalInformationDTO.getValue());

                            additionalInformation.add(trainingAdditionalInformationMap);
                        } else {
                            trainingAdditionalInformationValidatorResult.forEach(System.out::println);

                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        }
                    });

                    try {
                        trainingService.updateTrainingByPersonTypeDate(name, password, date, trainingDTO.getPersonName(), trainingDTO.getDate(), trainingDTO.getTrainingTypeName(), trainingDTO.getDuration(), trainingDTO.getAmountOfCalories(), additionalInformation);
                        response.setStatus(HttpServletResponse.SC_CREATED);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
}
