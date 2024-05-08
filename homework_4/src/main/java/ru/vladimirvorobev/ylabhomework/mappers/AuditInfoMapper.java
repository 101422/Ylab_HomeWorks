package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.AuditInfoDTO;
import ru.vladimirvorobev.ylabhomework.models.AuditInfo;

/**
 * Преобразование сущность-DTO информации для аудита интерфейс.
 **/
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuditInfoMapper {

    AuditInfoDTO auditInfoToAuditInfoDTO(AuditInfo auditInfo);
    AuditInfo auditInfoDTOToAuditInfo(AuditInfoDTO auditInfoDTO);

}
