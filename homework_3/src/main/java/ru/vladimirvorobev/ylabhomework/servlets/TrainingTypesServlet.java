package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.responseClasses.TrainingTypesResponse;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingTypeMapper;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет получения типов тренировок.
 **/
public class TrainingTypesServlet extends HttpServlet {
    private final TrainingService trainingService = new TrainingService();

    public final TrainingTypeMapper trainingTypeMapper = Mappers.getMapper(TrainingTypeMapper.class);

    public TrainingTypesServlet() throws IOException {
    }

    /**
     * GET-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {


        List<TrainingTypeDTO> trainingTypeDTOList = trainingService.findAllTrainingTypes().stream().map(this::trainingTypeDTOConvert)
                .collect(Collectors.toList());

        TrainingTypesResponse trainingTypesResponse = new TrainingTypesResponse(trainingTypeDTOList);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(trainingTypesResponse);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        out.print(json);
        out.flush();
    }

    /**
     * Преобразование типа тренировке в ДТО типа тренировки.
     *
     * @param trainingType Дополнительная информация о тренировке
     **/
    private TrainingTypeDTO trainingTypeDTOConvert(TrainingType trainingType) {
        return trainingTypeMapper.trainingTypeToTrainingTypeDTO(trainingType);
    }
}
