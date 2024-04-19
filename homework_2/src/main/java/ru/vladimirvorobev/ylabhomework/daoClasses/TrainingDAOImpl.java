package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.JDBC.JDBCService;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingDAO;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * DAO для работы с виртуальной базой данных тренировок в виде ArrayList.
 **/
public class TrainingDAOImpl implements TrainingDAO {

    JDBCService jdbcService;

    {
        jdbcService = new JDBCService();
    }


    /**
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    public List<Training> findAll(){
        return jdbcService.findTrainings();
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    public List<Training> findByPerson(Person person) {
        return jdbcService.findTrainingsByPerson(person);
    }

    /**
     * Получение тренировки по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return тренировка.
     **/
    public Training findByPersonAndTrainingTypeAndDate(Person person, TrainingType trainingType, Date date) {
        Optional<Training> foundedTraining = Optional.ofNullable(jdbcService.findTrainingByPersonTrainingTypeDate(person, trainingType, date));

        return foundedTraining.orElse(null);
    }

    /**
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    public Training findById(int id) {
        Optional<Training> foundedTraining = Optional.ofNullable(jdbcService.findTrainingById(id));

        return foundedTraining.orElse(null);
    }

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    public void save(Training training) {
        jdbcService.saveTraining(training);
    }

    /**
     * Обновление тренировки в базе (удаление по id существующей и запись новой.
     *
     * @param id
     * @param training
     **/
    public void update(int id, Training training) {
        jdbcService.updateTraining(id,training);
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void delete(int id) {
        jdbcService.deleteTraining(id);
    }


}
