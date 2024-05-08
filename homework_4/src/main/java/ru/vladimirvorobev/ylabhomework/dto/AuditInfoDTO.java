package ru.vladimirvorobev.ylabhomework.dto;

import lombok.*;
import java.sql.Timestamp;

/**
 * DTO информации для аудита.
 **/
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class AuditInfoDTO {

    private int id;
    @NonNull
    private String event;
    @NonNull
    private Timestamp timestamp ;

}
