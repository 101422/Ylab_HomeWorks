package ru.vladimirvorobev.ylabhomework.services;

import org.springframework.stereotype.Service;
import ru.vladimirvorobev.ylabhomework.annotations.AuthorizationLoggable;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.util.HashMap;
import java.util.Optional;

/**
 * Сервис авторизации и регистрации пользователей.
 **/
@Service
public class AuthorizationService {

    private final PersonDAOImpl personDAOImpl;

    public AuthorizationService(PersonDAOImpl personDAOImpl) {
        this.personDAOImpl = personDAOImpl;
    }

    /**
     * Регистрация пользователя в базе.
     *
     * @param name имя пользователя
     * @param password пароль
     **/
    public void registration(String name, String password) {
        Person person = new Person();
        person.setName(name);
        person.setPassword(password);
        person.setRole(Role.ROLE_USER);
        personDAOImpl.save(person);
    }

    /**
     * Авторизация пользователя.
     *
     * @param name имя пользователя
     * @param password пароль
     * @return HashMap с данными о полномочиях пользователя, состоит из пар: авторизован - булево, это админ - булево.
     **/
    @AuthorizationLoggable
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

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    public Optional<Person> findPersonByName(String name){
        Optional<Person> foundedTrainingType = personDAOImpl.findByName(name);

        return Optional.ofNullable(foundedTrainingType.orElse(null));
    }

}
