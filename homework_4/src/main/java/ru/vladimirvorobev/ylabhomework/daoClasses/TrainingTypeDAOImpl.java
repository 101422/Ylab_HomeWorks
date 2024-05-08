package ru.vladimirvorobev.ylabhomework.daoClasses;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.dao.TrainingTypeDAO;
import java.util.List;
import java.util.Optional;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных типов тренировок.
 **/
@Component
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    private final JdbcTemplate jdbcTemplate;

    public TrainingTypeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение списка всех типов тренировок.
     *
     * @return список всех типов тренировок
     **/
    public List<TrainingType> findAll(){
        return jdbcTemplate.query(GET_ALL_TRAINING_TYPES, new BeanPropertyRowMapper<>(TrainingType.class));
    }

    /**
     * Получение типа тренировок по имени.
     * @param name имя типа тренировки
     * @return тип тренировки
     **/
    public Optional<TrainingType>  findByName(String name) {
        return jdbcTemplate.query(GET_TRAINING_TYPE_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(TrainingType.class))
                .stream().findAny();
    }

    /**
     * Сохранение типа тренировки в базе.
     *
     * @param trainingType тип тренировки
     **/
    public void save(TrainingType trainingType) {
        jdbcTemplate.update(SAVE_TRAINING_TYPE, trainingType.getName());
    }

    /**
     * Удаление всех типов тренировок.
     *
     **/
    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_TRAINING_TYPES);
    }

}
