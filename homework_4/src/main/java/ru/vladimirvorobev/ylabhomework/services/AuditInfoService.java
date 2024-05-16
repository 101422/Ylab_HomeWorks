package ru.vladimirvorobev.ylabhomework.services;

import org.springframework.stereotype.Service;
import ru.vladimirvorobev.ylabhomework.daoClasses.*;
import ru.vladimirvorobev.ylabhomework.models.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Сервис по взаимодействию с таблицей информации для аудита в базе данных.
 **/
@Service
public class AuditInfoService {

    private final AuditInfoDAOImpl auditInfoDAOImpl;

    public AuditInfoService(AuditInfoDAOImpl auditInfoDAOImpl) {
        this.auditInfoDAOImpl = auditInfoDAOImpl;
    }

    /**
     * Получение всех записей информации для аудита.
     *
     * @return список всех записей информации для аудита
     **/
    public List<AuditInfo> findAll() {
        return auditInfoDAOImpl.findAll();
    }

    /**
     * Сохранение информации для аудита.
     *
     * @param event событие для аудита
     * @param timestamp время события
     **/
    public void save(String event, Timestamp timestamp) {
        auditInfoDAOImpl.save(new AuditInfo(event, timestamp));
    }

    /**
     * Удаление всей информации для аудита.
     *
     **/
    public void deleteAll() {
        auditInfoDAOImpl.deleteAll();
    }

}
