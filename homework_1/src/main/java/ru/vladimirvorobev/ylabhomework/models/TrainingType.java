package ru.vladimirvorobev.ylabhomework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность типа тренировкок.
 **/
@Getter
@Setter
@AllArgsConstructor
public class TrainingType {

    private int id;
    private String name;

    /**
     * Получение представления сущности типа тренировкок.
     *
     * @return представление сущности типа тренировок.
     **/
    @Override
    public String toString() {
        return "TrainingType {" +
                "id = " + id +
                ", name = " + name +
                '}';
    }

}
