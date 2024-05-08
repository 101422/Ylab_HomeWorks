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
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.mappers.TrainingTypeMapper;
import ru.vladimirvorobev.ylabhomework.models.AuditInfo;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.responseClasses.TrainingTypesResponse;
import ru.vladimirvorobev.ylabhomework.services.SecurityService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.validators.TrainingTypeValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingsDiaryErrorResponse;
import ru.vladimirvorobev.ylabhomework.util.TrainingsDiaryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static ru.vladimirvorobev.ylabhomework.util.ErrorsUtil.returnErrorsToClient;

/**
 * Контроллер типов тренировок.
 **/
@Tag(name = "Training types", description = "Training types management")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/types")
public class TrainingTypesController extends HttpServlet {

    private final TrainingService trainingService;
    private final TrainingTypeMapper trainingTypeMapper;
    private final TrainingTypeValidator trainingTypeValidator;

    public TrainingTypesController(TrainingService trainingService, TrainingTypeMapper trainingTypeMapper,
                                   TrainingTypeValidator trainingTypeValidator) throws IOException {
        this.trainingService    = trainingService;
        this.trainingTypeMapper = trainingTypeMapper;
        this.trainingTypeValidator = trainingTypeValidator;
    }

    /**
     * GET-запрос получения типов тренирово.
     *
     * @param response HTTP-ответ
     *
     * @return типы тренировок
     **/
    @Operation(summary = "Get training types", tags = { "training type", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuditInfo.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @GetMapping()
    public TrainingTypesResponse getTrainingTypes(HttpServletResponse response) throws IOException {
        if (SecurityService.credentials.get("isAuthorized")) {

            List<TrainingTypeDTO> trainingTypeDTOList = trainingService.findAllTrainingTypes().stream().map(this::trainingTypeToTrainingTypeDTO)
                    .collect(Collectors.toList());

            response.setStatus(HttpStatus.OK.value());
            return new TrainingTypesResponse(trainingTypeDTOList);
        }
        else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return new TrainingTypesResponse(new ArrayList<>());
        }
    }

    /**
     * POST-запрос создания типов тренировок.
     *
     * @param trainingTypeDTO DTO типа тренировок
     * @param bindingResult результат привязки
     *
     * @return статус
     **/
    @Operation(summary = "Add training type", tags = { "training type", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid TrainingTypeDTO trainingTypeDTO,
                                          BindingResult bindingResult) {
        if (SecurityService.credentials.get("isAuthorized")) {
            TrainingType trainingType = trainingTypeDTOToTrainingType(trainingTypeDTO);

            trainingTypeValidator.validate(trainingType, bindingResult);

            if (bindingResult.hasErrors())
                returnErrorsToClient(bindingResult);

            trainingService.saveTrainingType(trainingType);
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
        }
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN);
    }

    /**
     * Преобразование типа тренировки в DTO типа тренировки.
     *
     * @param trainingType тип тренировки
     *
     * @return DTO типа тренировки
     **/
    private TrainingTypeDTO trainingTypeToTrainingTypeDTO(TrainingType trainingType) {
        return trainingTypeMapper.trainingTypeToTrainingTypeDTO(trainingType);
    }

    /**
     * Преобразование DTO типа тренировки в тип тренировки.
     *
     * @param trainingTypeDTO DTO типа тренировки
     *
     * @return тип тренировки
     **/
    public TrainingType trainingTypeDTOToTrainingType(TrainingTypeDTO trainingTypeDTO) {
        return trainingTypeMapper.trainingTypeDTOToTrainingType(trainingTypeDTO);
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
