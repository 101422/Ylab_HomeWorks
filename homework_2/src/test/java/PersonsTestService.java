import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.security.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

public class PersonsTestService {

    private final DBConnectionProvider connectionProvider;

    public PersonsTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createSchemaIfNotExists();
        createPersonTableIfNotExists();
    }

    public Optional<Person> findByName(String name) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    public void save(Person person) {
        try (Connection connection = this.connectionProvider.getConnection()){
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

    private void createPersonTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                        CREATE SEQUENCE if not exists person_id_autoincrement
                        START WITH 1
                        INCREMENT BY 1;       
                    create table if not exists entities.person (
                        id int PRIMARY KEY,
                        name varchar(100) NOT NULL UNIQUE,
                        password varchar(100) NOT NULL,
                        role varchar(100) NOT NULL
                    )
                    """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
