package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.util.List;
import java.util.Optional;

public interface TrainingTypeDAO {

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    List<TrainingType> findAll();

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    Optional<TrainingType> findByName(String name);

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    void save(TrainingType trainingType);

}
