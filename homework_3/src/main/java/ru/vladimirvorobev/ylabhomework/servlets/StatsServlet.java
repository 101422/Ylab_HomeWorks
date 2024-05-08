package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import ru.vladimirvorobev.ylabhomework.annotations.TrainingGettingStatsLoggable;
import ru.vladimirvorobev.ylabhomework.dto.*;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingAdditionalInformationMapper;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingMapper;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.responseClasses.StatsResponse;
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.Stats;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Сервлет статистики о тренировках.
 **/
public class StatsServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final TrainingService trainingService = new TrainingService();
    private final TrainingMapper trainingMapper = Mappers.getMapper( TrainingMapper.class );

    private final AuthorizationService authorizationService = new AuthorizationService();

    private final TrainingAdditionalInformationMapper trainingAdditionalInformationMapper = Mappers.getMapper(TrainingAdditionalInformationMapper.class );
    public StatsServlet() throws IOException {
    }

    /**
     * GET-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @TrainingGettingStatsLoggable
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        HashMap<String, Boolean> credentials = authorizationService.login(name, password);

        if (credentials.get("isAuthorized")&& credentials.get("isAdmin")) {

            List<TrainingDTO> trainingsDTO = new ArrayList<>();

            trainingService.findAllTrainings().stream().forEach(trainingElement -> {
                TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);


                trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                        .collect(Collectors.toList()));

                trainingDTO.setPersonName(trainingElement.getPerson().getName());

                trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                trainingsDTO.add(trainingDTO);

            });

            Map<Date, List<TrainingDTO>> mapOfTrainingsByPersonName = trainingsDTO.stream()
                    .collect(groupingBy(TrainingDTO::getDate));

            List<Stats> statsList= new ArrayList<>();

            mapOfTrainingsByPersonName.forEach((key, value) -> {

                Stats stats = new Stats();

                stats.setDate(key);
                stats.setAmountOfCalories(value.stream().mapToInt(TrainingDTO::getAmountOfCalories).sum());

                statsList.add(stats);
            });

            StatsResponse statsResponse = new StatsResponse(statsList);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(statsResponse);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (credentials.get("isAuthorized")&& !credentials.get("isAdmin")) {
            List<TrainingDTO> trainingsDTO = new ArrayList<>();

            trainingService.findTrainingsByPersonName(name).stream().forEach(trainingElement -> {
                TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);


                trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                        .collect(Collectors.toList()));

                trainingDTO.setPersonName(trainingElement.getPerson().getName());

                trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                trainingsDTO.add(trainingDTO);
            });

            Map<Date, List<TrainingDTO>> mapOfTrainingsByPersonName = trainingsDTO.stream()
                    .collect(groupingBy(TrainingDTO::getDate));

            List<Stats> statsList= new ArrayList<>();

            mapOfTrainingsByPersonName.forEach((key, value) -> {

                Stats stats = new Stats();

                stats.setDate(key);
                stats.setAmountOfCalories(value.stream().mapToInt(TrainingDTO::getAmountOfCalories).sum());

                statsList.add(stats);
        });

            StatsResponse statsResponse = new StatsResponse(statsList);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(statsResponse);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (!credentials.get("isAuthorized")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    /**
     * Преобразование тренировки в ДТО тренировки.
     *
     * @param training Тренировка
     **/
    private TrainingDTO trainingToTrainingDTO(Training training) {
        return trainingMapper.trainingToTrainingDTO(training);
    }

    /**
     * Преобразование дополнительный информации о тренировке в ДТО дополнительный информации о тренировке.
     *
     * @param trainingAdditionalInformation Дополнительная информация о тренировке
     **/
    private TrainingAdditionalInformationDTO trainingAdditionalInformationToTrainingAdditionalInformationDTO(TrainingAdditionalInformation trainingAdditionalInformation) {
        return trainingAdditionalInformationMapper.trainingAdditionalInformationToTrainingAdditionalInformationDTO(trainingAdditionalInformation);
    }
}
