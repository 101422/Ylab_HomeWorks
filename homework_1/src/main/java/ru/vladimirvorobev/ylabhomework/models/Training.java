package ru.vladimirvorobev.ylabhomework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Сущность тренировки.
 **/
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Training {

    private Person person;
    private Date date;
    private TrainingType trainingType;
    private int duration;
    private int amountOfCalories;
    private List<HashMap<String, String>> additionalInformation;

}
