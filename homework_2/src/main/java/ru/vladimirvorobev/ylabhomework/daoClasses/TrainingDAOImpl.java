package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
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
public class TrainingDAOImpl implements TrainingDAO {

    private DatabaseService databaseService;

    public TrainingDAOImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    public List<Training> findAll(){
        try (Connection connection = databaseService.connect()){
            String query = GET_ALL_TRAININGS;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

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
                foundedPerson.setRole(Role.valueOf (resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);

                trainings.add(training);
            }

            return trainings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение списка всех тренировок пользователя.
     *
     * @param person пользователь
     * @return список тренировок пользователя
     **/
    public List<Training> findByPerson(Person person) {
        try (Connection connection = databaseService.connect()){
            String query = GET_TRAININGS_BY_PERSON_ID;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, person.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

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
                foundedPerson.setRole(Role.valueOf (resultSet.getString("person_role")));

                training.setPerson(foundedPerson);

                TrainingType foundedTrainingType = new TrainingType();

                foundedTrainingType.setId(resultSet.getInt("training_type_id"));
                foundedTrainingType.setName(resultSet.getString("training_type_name"));

                training.setTrainingType(foundedTrainingType);

                trainings.add(training);
            }

            return trainings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try (Connection connection = databaseService.connect()){
            String query = GET_TRAINING_BY_PERSON_ID_AND_TRAINING_TYPE_ID_AND_DATE;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, person.getId());
            preparedStatement.setInt(2, trainingType.getId());
            preparedStatement.setDate(3, date);

            ResultSet resultSet = preparedStatement.executeQuery();

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    public Optional<Training> findById(int id) {
        try (Connection connection = databaseService.connect()){
            String query = GET_TRAINING_BY_ID;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    public void save(Training training) {
        try (Connection connection = databaseService.connect()){
            String query = SAVE_TRAINING;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDate(1, training.getDate());
            preparedStatement.setInt(2, training.getDuration());
            preparedStatement.setInt(3, training.getAmountOfCalories());
            preparedStatement.setInt(4, training.getPerson().getId());
            preparedStatement.setInt(5, training.getTrainingType().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновление тренировки в базе (удаление по id существующей и запись новой.
     *
     * @param id
     * @param training
     **/
    public void update(int id, Training training) {
        try (Connection connection = databaseService.connect()){
            String query = UPDATE_TRAINING;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDate(1, training.getDate());
            preparedStatement.setInt(2, training.getDuration());
            preparedStatement.setInt(3, training.getAmountOfCalories());
            preparedStatement.setInt(4, training.getPerson().getId());
            preparedStatement.setInt(5, training.getTrainingType().getId());
            preparedStatement.setInt(6, training.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление тренировки из базеы.
     *
     * @param id
     **/
    public void delete(int id) {
        try (Connection connection = databaseService.connect()){
            String query = DELETE_TRAINING;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
