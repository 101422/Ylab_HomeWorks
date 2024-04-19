package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 * DAO для работы с виртуальной базой данных пользователей в виде ArrayList.
 **/
public class PersonDAOImpl implements PersonDAO {

    private static List<Person> persons;

    {
        persons = new LinkedList<>();

        persons.add(new Person("admin", "admin", Role.ADMIN));
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    public Person findByName(String name) {
        Optional<Person> foundedPerson =  persons.stream().filter(person -> person.getName().equals(name)).findAny();

        return foundedPerson.orElse(null);
    }

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    public void save(Person person) {
        persons.add(person);
    }

}
