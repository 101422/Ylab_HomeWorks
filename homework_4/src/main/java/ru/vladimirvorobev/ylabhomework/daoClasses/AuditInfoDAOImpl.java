package ru.vladimirvorobev.ylabhomework.daoClasses;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vladimirvorobev.ylabhomework.dao.AuditInfoDAO;
import ru.vladimirvorobev.ylabhomework.models.AuditInfo;
import java.util.List;
import static ru.vladimirvorobev.ylabhomework.dataBase.SQLQueryConstants.*;

/**
 * DAO аудита.
 **/
@Component
public class AuditInfoDAOImpl implements AuditInfoDAO {

    private final JdbcTemplate jdbcTemplate;

    public AuditInfoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение всех записей информации для аудита.
     *
     * @return список всех записей информации для аудита
     **/
    @Override
    public List<AuditInfo> findAll() {
        return jdbcTemplate.query(GET_ALL_AUDIT_INFO, new BeanPropertyRowMapper<>(AuditInfo.class));
    }

    /**
     * Сохранение информации для аудита.
     *
     * @param auditInfo информация для аудита
     **/
    @Override
    public void save(AuditInfo auditInfo) {
        jdbcTemplate.update(SAVE_AUDIT_INFO, auditInfo.getEvent(), auditInfo.getTimestamp());
    }

    /**
     * Удаление всей информации для аудита.
     *
     **/
    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_AUDIT_INFO);
    }

}
