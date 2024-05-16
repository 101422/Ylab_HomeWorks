package ru.vladimirvorobev.ylabhomework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vladimirvorobev.ylabhomework.dto.AuditInfoDTO;
import ru.vladimirvorobev.ylabhomework.mappers.AuditInfoMapper;
import ru.vladimirvorobev.ylabhomework.models.AuditInfo;
import ru.vladimirvorobev.ylabhomework.responseClasses.AuditInfoResponse;
import ru.vladimirvorobev.ylabhomework.services.AuditInfoService;
import ru.vladimirvorobev.ylabhomework.services.SecurityService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер типов тренировок.
 **/
@Tag(name = "Audit info", description = "Audit info management")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/audit")
public class AuditController extends HttpServlet {

    private final AuditInfoMapper auditInfoMapper;
    private final AuditInfoService auditInfoService;

    public AuditController(AuditInfoMapper auditInfoMapper, AuditInfoService auditInfoService) {
        this.auditInfoMapper = auditInfoMapper;
        this.auditInfoService = auditInfoService;
    }

    /**
     * GET-запрос получения информации для аудита.
     *
     * @param response HTTP-ответ
     *
     * @return информация для аудита
     **/
    @Operation(summary = "Get audit info", tags = { "audit info", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuditInfo.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @GetMapping()
    public AuditInfoResponse getAuditInfo(HttpServletResponse response) {
        if (SecurityService.credentials.get("isAuthorized") && SecurityService.credentials.get("isAdmin")) {

            List<AuditInfoDTO> auditInfoDTOList = auditInfoService.findAll().stream().map(this::auditInfoDTOConvert)
                    .collect(Collectors.toList());

            return new AuditInfoResponse(auditInfoDTOList);
        }
        else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return new AuditInfoResponse(new ArrayList<>());
        }
    }

    /**
     * Преобразование информации для аудита в ДТО информации для аудита.
     *
     * @param auditInfo Информации для аудита
     *
     * @return ДТО информации для аудита
     **/
    private AuditInfoDTO auditInfoDTOConvert(AuditInfo auditInfo) {
        return auditInfoMapper.auditInfoToAuditInfoDTO(auditInfo);
    }

}
