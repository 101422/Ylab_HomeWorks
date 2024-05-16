package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Person;
import java.util.Optional;

/**
 * DAO пользователей интерфейс.
 **/
public interface PersonDAO {

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    Optional<Person> findByName(String name);

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    void save(Person person);

    /**
     * Удаление всех пользователей.
     *
     **/
    void deleteAll();
}
