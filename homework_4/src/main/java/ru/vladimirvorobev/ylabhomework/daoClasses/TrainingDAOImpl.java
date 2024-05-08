package ru.vladimirvorobev.ylabhomework.daoClasses;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingDAO;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных тренировок.
 **/
@Component
public class TrainingDAOImpl implements TrainingDAO {

    private final JdbcTemplate jdbcTemplate;

    public TrainingDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    public List<Training> findAll() {
        return jdbcTemplate.query(GET_ALL_TRAININGS, resultSet -> {
            List<Training> trainings = new LinkedList<>();

            Training training = null;

            while (resultSet.next()) {
                training = new Training();

                training.setId(resultSet.getInt("id"));
                training.setDate(resultSet.getDate("date"));
                training.setDuration(resultSet.getInt("duration"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));

                Person foundedPerson = new Person();

                foundedPerson.setId(resultSet.getInt("person_id"));
                foundedPerson.setName(resultSet.getString("person_name"));
                foundedPerson.setPassword(resultSet.getString("person_password"));
                foundedPerson.setRole(Role.valueOf(resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);

                trainings.add(training);
            }
            return trainings;
        });
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    public List<Training> findByPerson(Person person) {
        return  jdbcTemplate.query(GET_TRAININGS_BY_PERSON_ID, new Object[]{person.getId()}, resultSet -> {
            List<Training> trainings = new LinkedList<>();

            Training training = null;

            while (resultSet.next()) {
                training = new Training();

                training.setId(resultSet.getInt("id"));
                training.setDate(resultSet.getDate("date"));
                training.setDuration(resultSet.getInt("duration"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));

                Person foundedPerson = new Person();

                foundedPerson.setId(resultSet.getInt("person_id"));
                foundedPerson.setName(resultSet.getString("person_name"));
                foundedPerson.setPassword(resultSet.getString("person_password"));
                foundedPerson.setRole(Role.valueOf(resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);

                trainings.add(training);
            }
            return trainings;
        });
    }

    /**
     * Получение тренировки по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return тренировка.
     **/
    public Optional<Training>  findByPersonAndTrainingTypeAndDate(Person person, TrainingType trainingType, Date date) {
        return  jdbcTemplate.query(GET_TRAINING_BY_PERSON_ID_AND_TRAINING_TYPE_ID_AND_DATE, new Object[]{person.getId(), trainingType.getId(), date}, resultSet -> {
            Training training = null;

            while (resultSet.next()) {
                training = new Training();

                training.setId(resultSet.getInt("id"));
                training.setDate(resultSet.getDate("date"));
                training.setDuration(resultSet.getInt("duration"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));

                Person foundedPerson = new Person();

                foundedPerson.setId(resultSet.getInt("person_id"));
                foundedPerson.setName(resultSet.getString("person_name"));
                foundedPerson.setPassword(resultSet.getString("person_password"));
                foundedPerson.setRole(Role.valueOf (resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);
            }

            return Optional.ofNullable(training);
        });
    }

    /**
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    public Optional<Training> findById(int id) {
        return  jdbcTemplate.query(GET_TRAINING_BY_ID, new Object[]{id}, resultSet -> {
            Training training = null;

            while (resultSet.next()) {
                training = new Training();

                training.setId(resultSet.getInt("id"));
                training.setDate(resultSet.getDate("date"));
                training.setDuration(resultSet.getInt("duration"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));
                training.setAmountOfCalories(resultSet.getInt("amount_of_calories"));

                Person foundedPerson = new Person();

                foundedPerson.setId(resultSet.getInt("person_id"));
                foundedPerson.setName(resultSet.getString("person_name"));
                foundedPerson.setPassword(resultSet.getString("person_password"));
                foundedPerson.setRole(Role.valueOf (resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);
            }

            return Optional.ofNullable(training);
        });
    }

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    public void save(Training training) {
        jdbcTemplate.update(SAVE_TRAINING, training.getDate(), training.getDuration(), training.getAmountOfCalories(), training.getPerson().getId(), training.getTrainingType().getId());
    }

    /**
     * Обновление тренировки в базе (удаление по id существующей и запись новой.
     *
     * @param id
     * @param training
     **/
    public void update(int id, Training training) {
        jdbcTemplate.update(UPDATE_TRAINING, training.getDate(), training.getDuration(), training.getAmountOfCalories(), training.getPerson().getId(), training.getTrainingType().getId(), training.getId());
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void delete(int id) {
        jdbcTemplate.update(DELETE_TRAINING, id);
    }

    /**
     * Удаление всех тренировок.
     *
     **/
    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_TRAININGS);
    }

}
