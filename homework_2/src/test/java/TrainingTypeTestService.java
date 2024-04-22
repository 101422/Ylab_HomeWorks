
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

public class TrainingTypeTestService {

    private final DBConnectionProvider connectionProvider;

    public TrainingTypeTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createSchemaIfNotExists();
        createTrainingTypeTableIfNotExists();
    }

    public List<TrainingType> findAll(){
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public Optional<TrainingType>  findByName(String name) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public void save(TrainingType trainingType) {
        try (Connection connection = this.connectionProvider.getConnection()){
            String query = SAVE_TRAINING_TYPE;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainingType.getName());

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
                  CREATE SEQUENCE if not exists training_type_id_autoincrement
                      START WITH 1
                      INCREMENT BY 1;                        
                  create table if not exists util.training_type (
                       id int PRIMARY KEY,
                       name varchar(100) NOT NULL
                   )
                    """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
