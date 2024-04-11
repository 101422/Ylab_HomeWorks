package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO для работы с виртуальной базой данных тренировок в виде ArrayList.
 **/
public class TrainingDAO {

    public static int TRAININGS_COUNT;

    private static List<Training> trainings;

    {
        trainings = new ArrayList<>();
    }


    /**
     * Получение списка всех тренировок.
     *
     * @return список всех тренировок
     **/
    public List<Training> showAll(){
        return trainings;
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    public List<Training> getTrainingsByPerson(Person person) {
        return trainings.stream().filter(training -> training.getPerson().equals(person)).collect(Collectors.toList());
    }

    /**
     * Получение списка тренировок с отбором по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return список тренировок с отбором по пользователю, типу тренировки и дате.
     **/
    public List<Training> getTrainingsByPersonTypeDate(Person person, TrainingType trainingType, Date date) {
        return trainings.stream().filter(training ->
                training.getPerson().equals(person) &&
                        training.getTrainingType().equals(trainingType) &&
                        training.getDate().compareTo(date) == 0).collect(Collectors.toList());

    }

    /**
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    public Training getTrainingById(int id) {
        return trainings.stream().filter(training -> training.getId()==id).findAny().orElse(null);
    }

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    public void save(Training training) {
        trainings.add(training);
    }

    /**
     * Обновление тренировки в базе (удаление по id существующей и запись новой.
     *
     * @param id
     * @param training
     **/
    public void update(int id, Training training) {
        delete(id);

        trainings.add(training);
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void delete(int id) {
        trainings.remove(trainings.indexOf(getTrainingById(id)));
    }


}
