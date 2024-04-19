import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainingTypeTestService {

    private final DBConnectionProvider connectionProvider;

    public TrainingTypeTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createTrainingTypeTableIfNotExists();
    }

    public void createTrainingType(TrainingType trainingType) {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into training_type(id,name) values(?,?)"
            );
            pstmt.setInt(1, trainingType.getId());
            pstmt.setString(2, trainingType.getName());

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TrainingType> getAllTrainingTypes() {
        List<TrainingType> trainingTypes = new ArrayList<>();

        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "select id,name from training_type"
            );
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                trainingTypes.add(new TrainingType(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainingTypes;
    }

    private void createTrainingTypeTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                    create table if not exists training_type (
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
