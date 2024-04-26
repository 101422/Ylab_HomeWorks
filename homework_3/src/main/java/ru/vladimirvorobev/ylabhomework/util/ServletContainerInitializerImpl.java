package ru.vladimirvorobev.ylabhomework.util;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingAdditionalInformationDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingDAOImpl;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.security.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ServletContainerInitializerImpl implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Servlet started");
      try {
            AuthorizationService authorizationService = new AuthorizationService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            TrainingService trainingService = new TrainingService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String args[]) {

    }

}
