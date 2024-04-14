package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Person;

public interface PersonDAO {

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    Person findByName(String name);

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    void save(Person person);
}
