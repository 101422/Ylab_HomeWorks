package ru.vladimirvorobev.ylabhomework.security;

import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.models.Person;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;


/**
 * Сервис авторизации пользователя.
 **/
public class AuthorizationService {

    private PersonDAOImpl personDAOImpl;
    static Properties property;

    public AuthorizationService() throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBCSettings.properties");

        property = new Properties();
        property.load(in);

        DatabaseService databaseService = new DatabaseService(property.getProperty("db.conn.url"),
                property.getProperty("db.username"),
                property.getProperty("db.password"));

        this.personDAOImpl = new PersonDAOImpl(databaseService);
    }

    /**
     * Регистрация пользователя в базе.
     *
     * @param name имя пользователя
     * @param password пароль
     **/
    public void registration(String name, String password) {
        personDAOImpl.save(new Person(name, password,  Role.ROLE_USER));
    }

    /**
     * Авторизация пользователя.
     *
     * @param name имя пользователя
     * @param password пароль
     * @return HashMap с данными о полномочиях пользователя, состоит из пар: авторизован - булево, это админ - булево.
     **/

    public HashMap<String, Boolean> login(String name, String password) {
        Optional<Person> optionalPerson = personDAOImpl.findByName(name);

        Person person = null;

        boolean isAuthorized, isAdmin;

        if (optionalPerson.isEmpty()) {
            System.out.println("A person with name " + name + " wasn't found!");

            isAuthorized = false;
        }
        else {
            person = optionalPerson.get();

            if (!person.getPassword().equals(password)) {
                System.out.println("Incorrect password!");

                isAuthorized =  false;
            }
            else {
                System.out.println("Hello " + name);

                isAuthorized =  true;
            }
        }

        isAdmin = isAuthorized && person.getRole() == Role.ADMIN;

        HashMap<String, Boolean> credentials = new HashMap<>();

        credentials.put("isAuthorized", isAuthorized);
        credentials.put("isAdmin", isAdmin);

        return credentials;
    }

}
