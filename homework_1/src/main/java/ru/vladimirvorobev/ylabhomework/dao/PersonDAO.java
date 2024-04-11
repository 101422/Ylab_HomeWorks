package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.security.Role;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO для работы с виртуальной базой данных пользователей в виде ArrayList.
 **/
public class PersonDAO {

    public static int PERSONS_COUNT;
    private static List<Person> persons;

    {
        persons = new ArrayList<>();

        // Создается пользователь по умолчанию с правами администратора.

        persons.add(new Person(++PERSONS_COUNT,"admin", "admin", Role.ADMIN));
    }

    /**
     * Отображение всех пользователей.
     **/
    public List<Person> showAll(){
        return persons;
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     **/
    public Person getPersonByName(String name) {
        return persons.stream().filter(person -> person.getName().equals(name)).findAny().orElse(null);
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
