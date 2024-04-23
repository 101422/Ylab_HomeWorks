package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.dao.TrainingAdditionalInformationDAO;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных дополнительной информации о тренировках.
 **/
public class TrainingAdditionalInformationDAOImpl implements TrainingAdditionalInformationDAO {

    private final DatabaseService databaseService;

    public TrainingAdditionalInformationDAOImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Получение списка дополнительный информации по тренировке.
     * @param training тренировка
     * @return список дополнительный информации по тренировке
     **/
    @Override
    public List<TrainingAdditionalInformation> findByTraining(Training training) {
        try (Connection connection = databaseService.connect()){

            String query = GET_TRAINING_ADDITIONAL_INFORMATION_BY_TRAINING_ID;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, training.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<TrainingAdditionalInformation> trainingAdditionalInformationList = new LinkedList<>();

            TrainingAdditionalInformation trainingAdditionalInformation = null;

            while (resultSet.next()) {
                trainingAdditionalInformation = new TrainingAdditionalInformation();

                trainingAdditionalInformation.setId(resultSet.getInt("id"));
                trainingAdditionalInformation.setKey(resultSet.getString("key"));
                trainingAdditionalInformation.setValue(resultSet.getString("value"));


                trainingAdditionalInformationList.add(trainingAdditionalInformation);
            }

            return trainingAdditionalInformationList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение дополнительный информации по тренировке в базе.
     *
     * @param trainingAdditionalInformation дополнительный информации по тренировке
     * @param trainingAdditionalInformation тренировка
     **/
    @Override
    public void save(TrainingAdditionalInformation trainingAdditionalInformation, Training training) {
        try (Connection connection = databaseService.connect()){
            String query = SAVE_TRAINING_ADDITIONAL_INFORMATION;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainingAdditionalInformation.getKey());
            preparedStatement.setString(2, trainingAdditionalInformation.getValue());
            preparedStatement.setInt(3, training.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление всех записей о дополнительный информации по тренировкам.
     *
     **/
    @Override
    public void deleteAll() {
        try (Connection connection = databaseService.connect()){
            String query = DELETE_ALL_TRAINING_ADDITIONAL_INFORMATION;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
