package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingTypeDAO;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * DAO для работы с виртуальной базой данных типов тренировок в виде ArrayList.
 **/
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    private static List<TrainingType> trainingTypes;

    {
        trainingTypes = new LinkedList<>();
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findAll(){
        return trainingTypes;
    }

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public TrainingType findByName(String name) {
        Optional<TrainingType> foundedTrainingType =  trainingTypes.stream().filter(trainingType -> trainingType.getName().equals(name)).findAny();

        return foundedTrainingType.orElse(null);
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
