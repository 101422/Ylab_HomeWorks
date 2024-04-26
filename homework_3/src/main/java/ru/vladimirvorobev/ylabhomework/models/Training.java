package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Сущность тренировки.
 **/
/*@Getter
@Setter*/
/*@AllArgsConstructor
@NoArgsConstructor
@ToString*/
public class Training {

    private int id;
    private Person person;
    private Date date;
    private TrainingType trainingType;
    private int duration;
    private int amountOfCalories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmountOfCalories() {
        return amountOfCalories;
    }

    public void setAmountOfCalories(int amountOfCalories) {
        this.amountOfCalories = amountOfCalories;
    }

    public Training(int id, Person person, Date date, TrainingType trainingType, int duration, int amountOfCalories) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.trainingType = trainingType;
        this.duration = duration;
        this.amountOfCalories = amountOfCalories;
    }


    public Training() {
    }

}

