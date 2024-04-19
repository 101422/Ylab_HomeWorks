package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

import java.util.List;

public interface TrainingAdditionalInformationDAO {



    /**
     * Получение списка дополнительный информации по тренировке.
     * @param training тренировка
     * @return список дополнительный информации по тренировке
     **/
    List<TrainingAdditionalInformation> findByTraining(Training training);

    /**
     * Сохранение дополнительный информации по тренировке в базе.
     *
     * @param trainingAdditionalInformation тип тренировки
     * @param training тренировка
     **/
    void save(TrainingAdditionalInformation trainingAdditionalInformation, Training training);

}
