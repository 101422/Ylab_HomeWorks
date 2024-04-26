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
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import ru.vladimirvorobev.ylabhomework.util.PersonValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingAdditionalInformationValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingTypeValidator;
import ru.vladimirvorobev.ylabhomework.util.TrainingValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервлет удаления тренировок.
 **/
public class TrainingDeleteServlet extends HttpServlet {

    private final TrainingService trainingService = new TrainingService();

    private final AuthorizationService authorizationService = new AuthorizationService();

    public TrainingDeleteServlet() throws IOException {
    }

    /**
     * DELETE-запрос.
     *
     * @param request Запрос
     * @param response Ответ
     **/
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        int trainingId = Integer.parseInt(request.getParameter("id"));

        HashMap<String, Boolean> credentials = authorizationService.login(name, password);

        if (credentials.get("isAuthorized")) {
            trainingService.deleteTraining(trainingId);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
}
