package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO для работы с виртуальной базой данных типов тренировок в виде ArrayList.
 **/
public class TrainingTypeDAO {

    public static int TRAINING_TYPES_COUNT;

    private static List<TrainingType> trainingTypes;

    {
        trainingTypes = new ArrayList<>();
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> showAll(){
        return trainingTypes;
    }

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public TrainingType getTrainingTypeByName(String name) {
        return trainingTypes.stream().filter(trainingType -> trainingType.getName().equals(name)).findAny().orElse(null);
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    public void save(TrainingType trainingType) {
        trainingTypes.add(trainingType);
    }

}
