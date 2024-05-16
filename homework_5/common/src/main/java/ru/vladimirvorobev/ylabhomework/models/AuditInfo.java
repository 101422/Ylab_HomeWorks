package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;
import org.springframework.lang.NonNull;


import java.sql.Timestamp;

/**
 * Сущность информации для аудита.
 **/
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class AuditInfo {
    private int id;
    @NonNull
    private String event;
    @NonNull
    private Timestamp timestamp ;

}
