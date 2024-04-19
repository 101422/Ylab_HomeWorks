package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

/**
 * Сущность типа тренировкок.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingAdditionalInformation {

    private int id;
    private String key;
    private String value;

}
