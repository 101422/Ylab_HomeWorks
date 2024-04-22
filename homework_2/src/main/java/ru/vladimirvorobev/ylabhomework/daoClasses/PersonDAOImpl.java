package ru.vladimirvorobev.ylabhomework.daoClasses;

import ru.vladimirvorobev.ylabhomework.dataBase.DatabaseService;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.sql.*;
import java.util.Optional;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных пользователей.
 **/
public class PersonDAOImpl implements PersonDAO {

    DatabaseService databaseService;

    {
        databaseService = new DatabaseService();
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    public Optional<Person> findByName(String name) {
        try (Connection connection = databaseService.connect()){
            String query = GET_PERSON_BY_NAME;

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

            return Optional.ofNullable(person);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    public void save(Person person) {
        try (Connection connection = databaseService.connect()){
            String query = SAVE_PERSON;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, person.getRole().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
