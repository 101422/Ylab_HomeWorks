package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DAO для работы с виртуальной базой данных тренировок в виде ArrayList.
 **/
public class TrainingDAOImpl implements TrainingDAO {


    private static List<Training> trainings;

    {
        trainings = new ArrayList<>();
    }


    /**
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    public List<Training> findAll(){
        return trainings;
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    public List<Training> findByPerson(Person person) {
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
    public List<Training> findByPersonAndTrainingTypeAndDate(Person person, TrainingType trainingType, Date date) {
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
    public Training findById(int id) {
        Optional<Training> foundedTrainings = Optional.ofNullable(trainings.get(id-1));

        return foundedTrainings.orElse(null);
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
        trainings.remove(trainings.get(id - 1));
    }


}
