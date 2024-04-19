import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.security.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonsTestService {

    private final DBConnectionProvider connectionProvider;

    public PersonsTestService(DBConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createPersonTableIfNotExists();
    }

    public void createPerson(Person person) {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into person(id,name, password, role) values(?,?,?,?)"
            );
            pstmt.setInt(1, person.getId());
            pstmt.setString(2, person.getName());
            pstmt.setString(3, person.getPassword());
            pstmt.setString(4, String.valueOf(person.getRole()));
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();

        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "select id,name, password, role from person"
            );
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {



                persons.add(new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"), Role.valueOf (resultSet.getString("role")) ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    private void createPersonTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                    create table if not exists person (
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
