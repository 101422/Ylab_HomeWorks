package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.JDBC.JDBCService;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;

import java.util.Optional;


/**
 * DAO для работы с виртуальной базой данных пользователей в виде ArrayList.
 **/
public class PersonDAOImpl implements PersonDAO {

    JDBCService jdbcService;

    {
        jdbcService = new JDBCService();
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    public Person findByName(String name) {
        Optional<Person> foundedPerson = Optional.ofNullable(jdbcService.findPersonByName(name));

        return foundedPerson.orElse(null);
    }

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    public void save(Person person) {
        jdbcService.savePerson(person);
    }

}
