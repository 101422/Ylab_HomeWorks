package ru.vladimirvorobev.ylabhomework.dao;

import ru.vladimirvorobev.ylabhomework.models.AuditInfo;

import java.util.List;

/**
 * DAO аудита интерфейс.
 **/
public interface AuditInfoDAO {

    /**
     * Получение всех записей информации для аудита.
     *
     * @return список всех записей информации для аудита
     **/
    List<AuditInfo> findAll();

    /**
     * Сохранение информации для аудита.
     *
     * @param auditInfo информация для аудита
     **/
    void save(AuditInfo auditInfo);

    /**
     * Удаление всей информации для аудита.
     *
     **/
    void deleteAll();
}
