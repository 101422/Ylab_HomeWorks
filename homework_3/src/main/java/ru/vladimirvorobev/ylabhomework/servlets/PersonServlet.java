package ru.vladimirvorobev.ylabhomework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.mappers.PersonMapper;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.util.PersonValidator;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


/**
 * Сервлет пользователей.
 **/
public class PersonServlet extends HttpServlet {
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class );
    private final PersonValidator personValidator = new PersonValidator();
    ObjectMapper objectMapper = new ObjectMapper();

    public PersonServlet() throws InstantiationException, IllegalAccessException, IOException {}

    /**
     * GET-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        authorizationService.login(name, password);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * POST-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining());

        PersonDTO personDTO = objectMapper.readValue(body, PersonDTO.class);

        List<String> personValidatorResult = personValidator.validate(personDTO);

        if (personValidatorResult.isEmpty()) {
            Person person =  personMapper.personDTOToPerson(personDTO);

            authorizationService.registration(person.getName(), person.getPassword());

            response.setStatus(HttpServletResponse.SC_CREATED);

        }
        else {
            personValidatorResult.forEach(System.out::println);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
