package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Сущность тренировки.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Training {

    private int id;
    private Person person;
    private Date date;
    private TrainingType trainingType;
    private int duration;
    private int amountOfCalories;

}
