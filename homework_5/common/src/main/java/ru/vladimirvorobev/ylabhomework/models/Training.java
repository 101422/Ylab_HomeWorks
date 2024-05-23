package ru.vladimirvorobev.ylabhomework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * Сущность тренировки.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Training {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    private Person person;
    private Date date;
    private TrainingType trainingType;
    private int duration;
    private int amountOfCalories;

}

