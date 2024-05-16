package ru.vladimirvorobev.ylabhomework.daoClasses;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.dao.TrainingAdditionalInformationDAO;
import ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;

import java.util.List;

import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO для работы с базой данных дополнительной информации о тренировках.
 **/
@Component
public class TrainingAdditionalInformationDAOImpl implements TrainingAdditionalInformationDAO {

    private final JdbcTemplate jdbcTemplate;

    public TrainingAdditionalInformationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение списка дополнительный информации по тренировке.
     * @param training тренировка
     * @return список дополнительный информации по тренировке
     **/
    @Override
    public List<TrainingAdditionalInformation> findByTraining(Training training) {
        return jdbcTemplate.query(SQLQueryConstants.GET_TRAINING_ADDITIONAL_INFORMATION_BY_TRAINING_ID, new Object[]{training.getId()}, new BeanPropertyRowMapper<>(TrainingAdditionalInformation.class));
    }

    /**
     * Сохранение дополнительный информации по тренировке в базе.
     *
     * @param trainingAdditionalInformation дополнительный информации по тренировке
     * @param trainingAdditionalInformation тренировка
     **/
    @Override
    public void save(TrainingAdditionalInformation trainingAdditionalInformation, Training training) {
        jdbcTemplate.update(SQLQueryConstants.SAVE_TRAINING_ADDITIONAL_INFORMATION, trainingAdditionalInformation.getKey(), trainingAdditionalInformation.getValue(), training.getId());
    }

    /**
     * Удаление всех записей о дополнительный информации по тренировкам.
     *
     **/
    @Override
    public void deleteAll() {
        jdbcTemplate.update(SQLQueryConstants.DELETE_ALL_TRAINING_TYPES);
    }

}
