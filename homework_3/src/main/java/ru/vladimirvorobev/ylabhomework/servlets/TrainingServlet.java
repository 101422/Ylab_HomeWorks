package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.TrainingAdditionalInformationValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingValidator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервлет получения и создания тренировки.
 **/
public class TrainingServlet extends HttpServlet {

    private final TrainingService trainingService = new TrainingService();
    ObjectMapper objectMapper = new ObjectMapper();
    private final TrainingValidator trainingValidator = new TrainingValidator();
    private final TrainingAdditionalInformationValidator trainingAdditionalInformationValidator = new TrainingAdditionalInformationValidator();
    public TrainingServlet() throws IOException {
    }

    /**
     * POST-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                }
                else {
                    trainingAdditionalInformationValidatorResult.forEach(System.out::println);

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            });

            try {
                trainingService.createTraining(trainingDTO.getPersonName(), trainingDTO.getDate(), trainingDTO.getTrainingTypeName(), trainingDTO.getDuration(), trainingDTO.getAmountOfCalories(),  additionalInformation);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
