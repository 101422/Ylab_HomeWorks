package ru.vladimirvorobev.ylabhomework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.mappers.PersonMapper;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.services.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.SecurityService;
import ru.vladimirvorobev.ylabhomework.util.*;
import ru.vladimirvorobev.ylabhomework.validators.PersonAuthorizationValidator;
import ru.vladimirvorobev.ylabhomework.validators.PersonRegistrationValidator;
import static ru.vladimirvorobev.ylabhomework.util.ErrorsUtil.returnErrorsToClient;

/**
 * Контроллер пользователей.
 **/
@Tag(name = "Persons", description = "Persons management")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
public class PersonController extends HttpServlet {

    private final AuthorizationService authorizationService;
    private final PersonMapper personMapper;
    private final PersonRegistrationValidator personRegistrationValidator;
    private final PersonAuthorizationValidator personAuthorizationValidator;

    public PersonController(AuthorizationService authorizationService, PersonMapper personMapper,
                            PersonRegistrationValidator personRegistrationValidator, PersonAuthorizationValidator personAuthorizationValidator) {
        this.authorizationService = authorizationService;
        this.personMapper = personMapper;
        this.personRegistrationValidator = personRegistrationValidator;
        this.personAuthorizationValidator = personAuthorizationValidator;
    }

    /**
     * POST-запрос авторизации.
     *
     * @param personDTO DTO пользователя
     * @param bindingResult результат привязки
     *
     * @return статус
     **/
    @Operation(summary = "Login", tags = { "persons", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema())  }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid PersonDTO personDTO,
                                          BindingResult bindingResult) {
        Person person =  personMapper.personDTOToPerson(personDTO);

        personAuthorizationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        SecurityService.credentials = authorizationService.login(person.getName(), person.getPassword());


        if (SecurityService.credentials.get("isAuthorized")) {
            SecurityService.name = person.getName();

            return ResponseEntity.ok(HttpStatus.OK);
        }
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN);
    }

    /**
     * POST-запрос авторизации.
     *
     * @param personDTO DTO пользователя
     * @param bindingResult результат привязки
     *
     * @return статус
     **/
    @Operation(summary = "Registration", tags = { "persons", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema())  }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid PersonDTO personDTO,
                                          BindingResult bindingResult) {
        Person person =  personMapper.personDTOToPerson(personDTO);

        personRegistrationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        authorizationService.registration(person.getName(), person.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
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
