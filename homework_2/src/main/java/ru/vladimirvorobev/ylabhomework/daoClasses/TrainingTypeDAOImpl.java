package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.JDBC.JDBCService;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingTypeDAO;

import java.util.List;
import java.util.Optional;

/**
 * DAO для работы с виртуальной базой данных типов тренировок в виде ArrayList.
 **/
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    public static int TRAINING_TYPES_COUNT;
    JDBCService jdbcService;

    {
        jdbcService = new JDBCService();
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findAll(){
        return jdbcService.findTrainingTypes();
    }

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public TrainingType findByName(String name) {
        Optional<TrainingType> foundedTrainingType  = Optional.ofNullable(jdbcService.findTrainingTypeByName(name));

        return foundedTrainingType.orElse(null);
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    public void save(TrainingType trainingType) {
        jdbcService.saveTrainingTypes(trainingType);
    }

}
