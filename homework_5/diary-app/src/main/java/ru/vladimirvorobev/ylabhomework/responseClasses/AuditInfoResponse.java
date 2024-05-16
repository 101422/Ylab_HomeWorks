package ru.vladimirvorobev.ylabhomework.responseClasses;

import ru.vladimirvorobev.ylabhomework.dto.AuditInfoDTO;
import java.util.List;

/**
 * Класс для упаковки информации для аудита для передачи в виде json.
 **/
public class AuditInfoResponse {

    private List<AuditInfoDTO> auditInfoDTO;

    public List<AuditInfoDTO> getAuditInfoDTO() {
        return auditInfoDTO;
    }

    public void setAuditInfoDTO(List<AuditInfoDTO> auditInfoDTO) {
        this.auditInfoDTO = auditInfoDTO;
    }

    public AuditInfoResponse(List<AuditInfoDTO> auditInfoDTO) {
        this.auditInfoDTO = auditInfoDTO;
    }
}
