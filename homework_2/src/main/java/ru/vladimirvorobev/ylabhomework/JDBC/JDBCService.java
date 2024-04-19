package ru.vladimirvorobev.ylabhomework.JDBC;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.security.Role;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class JDBCService {

    private static String URL;
    private static String USER_NAME;
    private static String PASSWORD;
    private static String CHANGELOG_FILE;
    static Properties property;

    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBCSettings.properties");

            property = new Properties();
            property.load(in);

            URL = property.getProperty("db.conn.url");
            USER_NAME = property.getProperty("db.username");
            PASSWORD = property.getProperty("db.password");
            CHANGELOG_FILE = property.getProperty("db.changelog_file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database);

            liquibase.update();

        } catch (SQLException | DatabaseException e) {
            System.out.println("SQL Exception during migration " + e.getMessage());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

    }

    public JDBCService() {
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findTrainingTypes() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM util.training_type";

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
     * Получение списка всех тренировок.
     *
     * @return список тренировок
     **/
    public List<Training> findTrainings() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id ";

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
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public TrainingType findTrainingTypeByName(String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT * FROM util.training_type WHERE name=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            TrainingType trainingType = null;

            while (resultSet.next()) {
                trainingType = new TrainingType();

                trainingType.setId(resultSet.getInt("id"));
                trainingType.setName(resultSet.getString("name"));
            }

            return trainingType;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    public Person findPersonByName(String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT * FROM entities.person WHERE name=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            Person person = null;

            while (resultSet.next()) {
                person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setPassword(resultSet.getString("password"));
                person.setRole(Role.valueOf (resultSet.getString("role")));
            }

            return person;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение списка дополнительный информации по тренировке.
     * @param training тренировка
     * @return список дополнительный информации по тренировке
     **/
    public List<TrainingAdditionalInformation> findTrainingAdditionalInformationByTraining(Training training) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String query = "SELECT * FROM util.training_additional_information WHERE training_id=?";

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
     * Получение тренировки по пользователю, типу тренировки и дате.
     *
     * @param person пользователь
     * @param trainingType тип тренировки
     * @param date дата
     * @return тренировка.
     **/
    public Training findTrainingByPersonTrainingTypeDate(Person person, TrainingType trainingType, Date date) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE person_id=? AND training_type_id=? AND date=? ";

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

            return training;

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
    public List<Training> findTrainingsByPerson(Person person) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE person_id=? ";

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
     * Получение тренировки по id.
     *
     * @param id
     * @return тренировка.
     **/
    public Training findTrainingById(int  id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "SELECT entities.training.* , person.name as person_name, person.password as person_password, person.role as person_role, training_type.name as training_type_name FROM entities.training JOIN entities.person ON training.person_id = person.id JOIN util.training_type ON training.training_type_id = training_type.id  WHERE training.id=? ";

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

            return training;

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
    public void updateTraining(int id, Training training) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "UPDATE entities.training set date=?, duration=?, amount_of_calories=?, person_id=?, training_type_id=? WHERE id=?";

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
    public void deleteTraining(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "DELETE FROM entities.training WHERE id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    public void savePerson(Person person) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "INSERT INTO entities.person(id, name, password, role) VALUES(nextval('person_id_autoincrement'),?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, person.getRole().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение тренировки в базе.
     *
     * @param training тренировка
     **/
    public void saveTraining(Training training) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "INSERT INTO entities.training(id, date, duration, amount_of_calories, person_id, training_type_id) VALUES(nextval('training_id_autoincrement'),?,?,?,?,?)";

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
     * Сохранение дополнительный информации по тренировке в базе.
     *
     * @param trainingAdditionalInformation дополнительный информации по тренировке
     * @param trainingAdditionalInformation тренировка
     **/
    public void saveTrainingAdditionalInformation(TrainingAdditionalInformation trainingAdditionalInformation, Training training) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "INSERT INTO util.training_additional_information(id, key, value, training_id) VALUES(nextval('training_additional_information_id_autoincrement'),?,?,?)";

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
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    public void saveTrainingTypes(TrainingType trainingType) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "INSERT INTO util.training_type(id, name) VALUES (nextval('training_type_id_autoincrement'),?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, trainingType.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
