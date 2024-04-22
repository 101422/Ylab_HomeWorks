import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

public class TrainingAdditionalInformationTestService {

    private final DBConnectionProvider connectionProvider;

    public TrainingAdditionalInformationTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createSchemaIfNotExists();
        createTrainingTypeTableIfNotExists();
    }

    public List<TrainingAdditionalInformation> findByTraining(Training training) {
        try (Connection connection = this.connectionProvider.getConnection()){

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

    public void save(TrainingAdditionalInformation trainingAdditionalInformation, Training training) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    private void createSchemaIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                    CREATE SCHEMA if not exists util
                    """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTrainingTypeTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                  CREATE SEQUENCE if not exists training_additional_information_id_autoincrement
                      START WITH 1
                      INCREMENT BY 1;
                      create table if not exists util.training_additional_information (
                                           id int PRIMARY KEY,
                                           key varchar(100) NOT NULL,
                                           value varchar(100) NOT NULL,
                                           training_id int REFERENCES entities.training(id) ON DELETE SET NULL
                     )
                    """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
