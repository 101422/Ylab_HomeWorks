import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

public class TrainingTestService {

    private final DBConnectionProvider connectionProvider;

    public TrainingTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createSchemaIfNotExists();
        createTrainingTableIfNotExists();
    }

    public List<Training> findAll(){
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public List<Training> findByPerson(Person person) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public Optional<Training> findByPersonAndTrainingTypeAndDate(Person person, TrainingType trainingType, Date date) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public Optional<Training> findById(int id) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public void save(Training training) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public void update(int id, Training training) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public void delete(int id) {
        try (Connection connection = this.connectionProvider.getConnection()){
            String query = DELETE_TRAINING;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSchemaIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                    CREATE SCHEMA if not exists entities
                    """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTrainingTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {

            PreparedStatement pstmt3 = conn.prepareStatement(
                    """
                    CREATE SEQUENCE if not exists training_id_autoincrement
                        START WITH 1
                        INCREMENT BY 1;
                    create table if not exists entities.training (
                                              id int PRIMARY KEY,
                                              date date NOT NULL,
                                              duration int NOT NULL,
                                              amount_of_calories int NOT NULL,
                                              person_id int REFERENCES entities.person(id) ON DELETE SET NULL,
                                              training_type_id int REFERENCES util.training_type(id) ON DELETE SET NULL
                    )
                    """
            );
            pstmt3.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
