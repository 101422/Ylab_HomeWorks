package ru.vladimirvorobev.ylabhomework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Сущность тренировки.
 **/
@Getter
@Setter
@AllArgsConstructor
public class Training {

    private int id;
    private Person person;
    private Date date;
    private TrainingType trainingType;
    private int duration;
    private int amountOfCalories;
    List<HashMap<String, String>> additionalInformation;

    /**
     * Получение представления сущности тренировки.
     *
     * @return представление сущности тренировки.
     **/
    @Override
    public String toString() {

        AtomicReference<String> additionalInformationString = new AtomicReference<>("");

        additionalInformation.stream().forEach((c) -> {

            c.forEach((key, tab) -> {
                additionalInformationString.set(additionalInformationString + key + ": " + tab + "\n");
            });
            });

        return "Training {"  + "\n" +
                "id = " + id +
                ", person = " + person.getName() +
                ", date = " + date +
                ", training type = " + trainingType.getName() +
                ", duration = " + duration +
                ", amountOfCalories = " + amountOfCalories + "\n" +
                "additional information:" + "\n" +
                additionalInformationString +
        '}';


    }

}
