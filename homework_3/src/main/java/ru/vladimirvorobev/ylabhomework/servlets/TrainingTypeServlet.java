package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingTypeMapper;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.TrainingTypeValidator;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет получения и создания типа тренировки.
 **/
public class TrainingTypeServlet extends HttpServlet {

    private final TrainingService trainingService = new TrainingService();
    private final TrainingTypeMapper trainingTypeMapper = Mappers.getMapper( TrainingTypeMapper.class );
    private final TrainingTypeValidator trainingTypeValidator = new TrainingTypeValidator();
    ObjectMapper objectMapper = new ObjectMapper();

    public TrainingTypeServlet() throws IOException {
    }

    /**
     * POST-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining());

        TrainingTypeDTO trainingTypeDTO = objectMapper.readValue(body, TrainingTypeDTO.class);


        List<String> trainingTypeValidatorResult = trainingTypeValidator.validate(trainingTypeDTO);

        if (trainingTypeValidatorResult.isEmpty()) {
            TrainingType trainingType =  trainingTypeMapper.trainingTypeDTOToTrainingType(trainingTypeDTO);

            trainingService.createTrainingType(trainingType.getName());

            response.setStatus(HttpServletResponse.SC_CREATED);

        }
        else {
            trainingTypeValidatorResult.forEach(System.out::println);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}
