package ru.vladimirvorobev.ylabhomework.daoClasses;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.dao.PersonDAO;
import java.util.Optional;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных пользователей.
 **/
@Component
public class PersonDAOImpl implements PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение пользователя по имени.
     *
     * @param name имя пользователя
     * @return пользователь
     **/
    @Override
    public Optional<Person> findByName(String name) {
        return jdbcTemplate.query(GET_PERSON_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    /**
     * Сохранение пользователя.
     *
     * @param person пользователь
     **/
    @Override
    public void save(Person person) {
        jdbcTemplate.update(SAVE_PERSON, person.getName(), person.getPassword(), person.getRole().toString());
    }

    /**
     * Удаление всех пользователей.
     *
     **/
    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_PERSONS);
    }
}
