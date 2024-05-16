package ru.vladimirvorobev.ylabhomework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingAdditionalInformationMapper;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingMapper;
import ru.vladimirvorobev.ylabhomework.models.AuditInfo;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.responseClasses.StatsResponse;
import ru.vladimirvorobev.ylabhomework.responseClasses.TrainingsResponse;
import ru.vladimirvorobev.ylabhomework.services.SecurityService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.Stats;
import ru.vladimirvorobev.ylabhomework.util.TrainingsDiaryErrorResponse;
import ru.vladimirvorobev.ylabhomework.util.TrainingsDiaryException;
import ru.vladimirvorobev.ylabhomework.validators.TrainingAdditionalInformationDTOValidator;
import ru.vladimirvorobev.ylabhomework.validators.TrainingDTOValidator;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static ru.vladimirvorobev.ylabhomework.util.ErrorsUtil.returnErrorsToClient;

/**
 * Контроллер тренировок.
 **/
@Tag(name = "Trainings", description = "Trainings management")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/trainings")
public class TrainingsController extends HttpServlet {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingAdditionalInformationMapper trainingAdditionalInformationMapper;
    private final TrainingDTOValidator trainingDTOValidator;

    public TrainingsController(TrainingService trainingService, TrainingMapper trainingMapper,
                               TrainingAdditionalInformationMapper trainingAdditionalInformationMapper, TrainingDTOValidator trainingDTOValidator, TrainingAdditionalInformationDTOValidator trainingAdditionalInformationDTOValidator) throws IOException {
        this.trainingService    = trainingService;
        this.trainingMapper = trainingMapper;
        this.trainingAdditionalInformationMapper = trainingAdditionalInformationMapper;
        this.trainingDTOValidator = trainingDTOValidator;
    }

    /**
     * GET-запрос получения тренировок.
     *
     * @param response HTTP-ответ
     *
     * @return типы тренировок
     **/
    @Operation(summary = "Get trainings", tags = { "trainings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuditInfo.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @GetMapping()
    public TrainingsResponse getTrainings(HttpServletResponse response) throws IOException {
        if (SecurityService.credentials.get("isAuthorized")) {
            if (SecurityService.credentials.get("isAdmin")) {
                List<TrainingDTO> trainingsDTO = new ArrayList<>();

                trainingService.findAllTrainings().stream().forEach(trainingElement -> {
                    TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);


                    trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                            .collect(Collectors.toList()));

                    trainingDTO.setPersonName(trainingElement.getPerson().getName());

                    trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                    trainingsDTO.add(trainingDTO);
                });

                return new TrainingsResponse(trainingsDTO);
            }
            if (!SecurityService.credentials.get("isAdmin")) {
                List<TrainingDTO> trainingsDTO = new ArrayList<>();

                trainingService.findTrainingsByPersonName(SecurityService.name).stream().forEach(trainingElement -> {
                    TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);


                    trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                            .collect(Collectors.toList()));

                    trainingDTO.setPersonName(trainingElement.getPerson().getName());

                    trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                    trainingsDTO.add(trainingDTO);
                });

                return new TrainingsResponse(trainingsDTO);
            }
        }
            response.setStatus(HttpStatus.FORBIDDEN.value());

            return new TrainingsResponse(new ArrayList<>());
    }

    /**
     * GET-запрос получения тренировок по дате.
     *
     * @param response HTTP-ответ
     *
     * @return типы тренировок
     **/
    @Operation(summary = "Get trainings ordered by date", tags = { "trainings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuditInfo.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/byDate")
    public TrainingsResponse getTrainingsByDate(HttpServletResponse response) {
        if (SecurityService.credentials.get("isAuthorized")) {
            if (SecurityService.credentials.get("isAdmin")) {
                List<TrainingDTO> trainingsDTO = new ArrayList<>();

                trainingService.findAllTrainings().stream().forEach(trainingElement -> {
                    TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);

                    trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                            .collect(Collectors.toList()));

                    trainingDTO.setPersonName(trainingElement.getPerson().getName());

                    trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                    trainingsDTO.add(trainingDTO);

                });

                trainingsDTO.sort(Comparator.comparing(TrainingDTO::getDate));

                return new TrainingsResponse(trainingsDTO);
            }
            if (!SecurityService.credentials.get("isAdmin")) {
                List<TrainingDTO> trainingsDTO = new ArrayList<>();

                trainingService.findTrainingsByPersonName(SecurityService.name).stream().forEach(trainingElement -> {
                    TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);

                    trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                            .collect(Collectors.toList()));

                    trainingDTO.setPersonName(trainingElement.getPerson().getName());

                    trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                    trainingsDTO.add(trainingDTO);
                });

                trainingsDTO.sort(Comparator.comparing(TrainingDTO::getDate));

                return new TrainingsResponse(trainingsDTO);
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());

        return new TrainingsResponse(new ArrayList<>());
    }
    /**
     * GET-запрос получения статистики тренировок.
     *
     * @param response HTTP-ответ
     *
     * @return статистика тренировок
     **/
    @Operation(summary = "Get trainings stats", tags = { "trainings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuditInfo.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/stats")
    public StatsResponse getStats(HttpServletResponse response) throws IOException {
        if (SecurityService.credentials.get("isAuthorized")) {

            if (SecurityService.credentials.get("isAdmin")) {

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

                List<Stats> statsList = new ArrayList<>();

                mapOfTrainingsByPersonName.forEach((key, value) -> {

                    Stats stats = new Stats();

                    stats.setDate(key);
                    stats.setAmountOfCalories(value.stream().mapToInt(TrainingDTO::getAmountOfCalories).sum());

                    statsList.add(stats);
                });

                return new StatsResponse(statsList);
            }
            if (!SecurityService.credentials.get("isAdmin")) {
                List<TrainingDTO> trainingsDTO = new ArrayList<>();

                trainingService.findTrainingsByPersonName(SecurityService.name).stream().forEach(trainingElement -> {
                    TrainingDTO trainingDTO = trainingToTrainingDTO(trainingElement);


                    trainingDTO.setTrainingAdditionalInformation(trainingService.findTrainingAdditionalInformationByTraining(trainingElement).stream().map(this::trainingAdditionalInformationToTrainingAdditionalInformationDTO)
                            .collect(Collectors.toList()));

                    trainingDTO.setPersonName(trainingElement.getPerson().getName());

                    trainingDTO.setTrainingTypeName(trainingElement.getTrainingType().getName());

                    trainingsDTO.add(trainingDTO);
                });

                Map<Date, List<TrainingDTO>> mapOfTrainingsByPersonName = trainingsDTO.stream()
                        .collect(groupingBy(TrainingDTO::getDate));

                List<Stats> statsList = new ArrayList<>();

                mapOfTrainingsByPersonName.forEach((key, value) -> {

                    Stats stats = new Stats();

                    stats.setDate(key);
                    stats.setAmountOfCalories(value.stream().mapToInt(TrainingDTO::getAmountOfCalories).sum());

                    statsList.add(stats);
                });

                return new StatsResponse(statsList);
            }
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());

        return new StatsResponse(new ArrayList<>());
    }

    /**
     * POST-запрос создания тренировок.
     *
     * @param trainingDTO DTO тренировок
     * @param bindingResult результат привязки
     *
     * @return статус
     **/
    @Operation(summary = "Add training", tags = { "training", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema())  }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid TrainingDTO trainingDTO,
                                          BindingResult bindingResult) {
        if (SecurityService.credentials.get("isAuthorized")) {
            List<HashMap<String, String>> additionalInformation = new ArrayList<>();
            
            trainingDTOValidator.validate(trainingDTO, bindingResult);

            if (bindingResult.hasErrors())
                returnErrorsToClient(bindingResult);

                trainingDTO.getTrainingAdditionalInformation().stream().forEach(trainingAdditionalInformationDTO -> {
                        HashMap<String, String> trainingAdditionalInformationMap = new HashMap<>();

                        trainingAdditionalInformationMap.put(trainingAdditionalInformationDTO.getKey(), trainingAdditionalInformationDTO.getValue());

                        additionalInformation.add(trainingAdditionalInformationMap);
                });

                try {
                    trainingService.createTraining(trainingDTO.getPersonName(), trainingDTO.getDate(), trainingDTO.getTrainingTypeName(), trainingDTO.getDuration(), trainingDTO.getAmountOfCalories(), additionalInformation);

                    return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
                } catch (InstantiationException | IllegalAccessException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
                }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN);
    }

    /**
     * POST-запрос редактирования тренировок.
     *
     * @param trainingDTO DTO тренировок
     * @param bindingResult результат привязки
     * @param id id тренировки
     *
     * @return статус
     **/
    @Operation(summary = "Edit training", tags = { "training", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema())  }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/edit")
    public ResponseEntity<HttpStatus> edit(@RequestBody @Valid TrainingDTO trainingDTO,
                                          BindingResult bindingResult, @RequestParam(name = "id") String id) {
        if (SecurityService.credentials.get("isAuthorized")) {
            List<HashMap<String, String>> additionalInformation = new ArrayList<>();

            trainingDTOValidator.validate(trainingDTO, bindingResult);

            if (bindingResult.hasErrors())
                returnErrorsToClient(bindingResult);

            trainingDTO.getTrainingAdditionalInformation().stream().forEach(trainingAdditionalInformationDTO -> {
                HashMap<String, String> trainingAdditionalInformationMap = new HashMap<>();

                trainingAdditionalInformationMap.put(trainingAdditionalInformationDTO.getKey(), trainingAdditionalInformationDTO.getValue());

                additionalInformation.add(trainingAdditionalInformationMap);
            });

            try {
                trainingService.updateTraining(Integer.parseInt(id), trainingDTO.getPersonName(), trainingDTO.getDate(), trainingDTO.getTrainingTypeName(), trainingDTO.getDuration(), trainingDTO.getAmountOfCalories(), additionalInformation);

                return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
            } catch (InstantiationException | IllegalAccessException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN);
    }

    /**
     * DELETE-запрос удаления тренировки.
     *
     * @param id id тренировки
     *
     * @return статус
     **/
    @Operation(summary = "Delete training", tags = { "training", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "202", content = { @Content(schema = @Schema())  }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "id") String id) {
        if (SecurityService.credentials.get("isAuthorized")) {
            trainingService.deleteTraining(Integer.parseInt(id));

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN);
    }
    /**
     * Преобразование тренировки в DTO тренировки.
     *
     * @param training тренировка
     *
     * @return DTO тренировки
     **/
    private TrainingDTO trainingToTrainingDTO(Training training) {
        return trainingMapper.trainingToTrainingDTO(training);
    }

    /**
     * Преобразование DTO тренировки в тренировку.
     *
     * @param trainingDTO DTO тренировки
     *
     * @return тренировка
     **/
    private Training trainingDTOToTraining(TrainingDTO trainingDTO) {
        return trainingMapper.trainingDTOToTraining(trainingDTO);
    }

    /**
     * Преобразование дополнительный информации о тренировке в ДТО дополнительный информации о тренировке.
     *
     * @param trainingAdditionalInformation Дополнительная информация о тренировке
     **/
    private TrainingAdditionalInformationDTO trainingAdditionalInformationToTrainingAdditionalInformationDTO(TrainingAdditionalInformation trainingAdditionalInformation) {
        return trainingAdditionalInformationMapper.trainingAdditionalInformationToTrainingAdditionalInformationDTO(trainingAdditionalInformation);
    }

    /**
     * Преобразование дополнительный информации о тренировке в ДТО дополнительный информации о тренировке.
     *
     * @param trainingAdditionalInformationDTO Дополнительная информация о тренировке DTO
     **/
    private TrainingAdditionalInformation trainingAdditionalInformationDTOToTrainingAdditionalInformation(TrainingAdditionalInformationDTO trainingAdditionalInformationDTO) {
        return trainingAdditionalInformationMapper.trainingAdditionalInformationDTOToTrainingAdditionalInformation(trainingAdditionalInformationDTO);
    }

    /**
     * Ответчик для ошибочных ситуаций.
     *
     * @param e ошибка типа TrainingsDiaryException
     *
     * @return ответ об ошибке
     **/
    @ExceptionHandler
    private ResponseEntity<TrainingsDiaryErrorResponse> handleException(TrainingsDiaryException e) {
        TrainingsDiaryErrorResponse response = new TrainingsDiaryErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
