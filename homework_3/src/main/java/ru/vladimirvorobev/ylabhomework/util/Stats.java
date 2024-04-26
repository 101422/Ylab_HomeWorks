package ru.vladimirvorobev.ylabhomework.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/*@Getter
@Setter*/
/*
@NoArgsConstructor*/
public class Stats {

    private Date date;
    private int amountOfCalories;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmountOfCalories() {
        return amountOfCalories;
    }

    public void setAmountOfCalories(int amountOfCalories) {
        this.amountOfCalories = amountOfCalories;
    }

    public Stats(Date date, int amountOfCalories) {
        this.date = date;
        this.amountOfCalories = amountOfCalories;
    }

    public Stats() {

    }

}
