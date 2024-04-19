import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainingTestService {

    private final DBConnectionProvider connectionProvider;

    public TrainingTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createTrainingTableIfNotExists();
    }

    public void createTraining(Training training) {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO training(id, date, duration, amount_of_calories, person_id, training_type_id) values (?,?,?,?,?,?)"
            );
            preparedStatement.setInt(1, training.getId());
            preparedStatement.setDate(2, training.getDate());
            preparedStatement.setInt(3, training.getDuration());
            preparedStatement.setInt(4, training.getAmountOfCalories());
            preparedStatement.setInt(5, training.getPerson().getId());
            preparedStatement.setInt(6, training.getTrainingType().getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Training> getAllTraining() {
        List<Training> trainings = new ArrayList<>();

        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM training JOIN person ON training.person_id = person.id JOIN training_type ON training.training_type_id = training_type.id"
            );
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Training training = new Training();

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainings;
    }

    private void createTrainingTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {

            PreparedStatement pstmt3 = conn.prepareStatement(
                    """
                    create table if not exists training (
                            id int PRIMARY KEY,
                            date date NOT NULL,
                            duration int NOT NULL,
                            amount_of_calories int NOT NULL,
                            person_id int REFERENCES person(id) ON DELETE SET NULL,
                            training_type_id int REFERENCES training_type(id) ON DELETE SET NULL
                     )
                    """
            );
            pstmt3.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
