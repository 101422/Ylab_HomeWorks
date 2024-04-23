package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingTypeDAO;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;


/**
 * DAO для работы с базой данных типов тренировок.
 **/
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    private final DatabaseService databaseService;

    public TrainingTypeDAOImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findAll(){
        try (Connection connection = databaseService.connect()){
            Statement statement = connection.createStatement();

            String query = GET_ALL_TRAINING_TYPES;

            ResultSet resultSet = statement.executeQuery(query);

            List<TrainingType> trainingTypes = new LinkedList<>();

            while (resultSet.next()) {
                TrainingType trainingType = new TrainingType();

                trainingType.setId(resultSet.getInt("id"));
                trainingType.setName(resultSet.getString("name"));

                trainingTypes.add(trainingType);
            }

            return trainingTypes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public Optional<TrainingType>  findByName(String name) {
        try (Connection connection = databaseService.connect()){
            String query = GET_TRAINING_TYPE_BY_NAME;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            TrainingType trainingType = null;

            while (resultSet.next()) {
                trainingType = new TrainingType();

                trainingType.setId(resultSet.getInt("id"));
                trainingType.setName(resultSet.getString("name"));
            }

            return Optional.ofNullable(trainingType);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    public void save(TrainingType trainingType) {
        try (Connection connection = databaseService.connect()){
            String query = SAVE_TRAINING_TYPE;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainingType.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление всех типов тренировок.
     *
     **/
    @Override
    public void deleteAll() {
        try (Connection connection = databaseService.connect()){
            String query = DELETE_ALL_TRAINING_TYPES;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
