package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * DAO тренировок интерфейс.
 **/
public interface TrainingDAO {

    /**
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    List<Training> findAll();

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    List<Training> findByPerson(Person person);

    /**
     * Получение списка тренировок с отбором по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    Optional<Training> findByPersonAndTrainingTypeAndDate(Person person, TrainingType trainingType, Date date);

    /**
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    Optional<Training> findById(int id);

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    void save(Training training);

    /**
     * Обновление тренировки в базе (удаление по id существующей и запись новой.
     *
     * @param id
     * @param training
     **/
    void update(int id, Training training);

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    void delete(int id);

    /**
     * Удаление всех тренировок.
     *
     **/
    void deleteAll();

}
