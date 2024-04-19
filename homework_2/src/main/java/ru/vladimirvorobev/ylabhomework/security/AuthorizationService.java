package ru.vladimirvorobev.ylabhomework.security;

import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import java.util.HashMap;
/**
 * Сервис авторизации пользователя.
 **/
public class AuthorizationService {

    private PersonDAOImpl personDAOImpl;

    public AuthorizationService(){
        this.personDAOImpl = new PersonDAOImpl();
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
        Person person = personDAOImpl.findByName(name);

        boolean isAuthorized, isAdmin;

        if (person == null) {
            System.out.println("A person with name " + name + " wasn't found!");

            isAuthorized = false;
        }
        else {
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
