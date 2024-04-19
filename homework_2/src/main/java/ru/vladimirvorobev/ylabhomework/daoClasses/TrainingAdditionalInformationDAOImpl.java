package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.JDBC.JDBCService;
import ru.vladimirvorobev.ylabhomework.dao.TrainingAdditionalInformationDAO;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;

import java.util.List;

/**
 * DAO для работы с виртуальной базой данных типов тренировок в виде ArrayList.
 **/
public class TrainingAdditionalInformationDAOImpl implements TrainingAdditionalInformationDAO {

    JDBCService jdbcService;

    {
        jdbcService = new JDBCService();
    }

    /**
     * Получение списка дополнительный информации по тренировке.
     * @param training тренировка
     * @return список дополнительный информации по тренировке
     **/
    @Override
    public List<TrainingAdditionalInformation> findByTraining(Training training) {
        return jdbcService.findTrainingAdditionalInformationByTraining(training);
    }

    /**
     * Сохранение дополнительный информации по тренировке в базе.
     *
     * @param trainingAdditionalInformation дополнительный информации по тренировке
     * @param trainingAdditionalInformation тренировка
     **/
    @Override
    public void save(TrainingAdditionalInformation trainingAdditionalInformation, Training training) {
        jdbcService.saveTrainingAdditionalInformation(trainingAdditionalInformation, training);
    }



}
