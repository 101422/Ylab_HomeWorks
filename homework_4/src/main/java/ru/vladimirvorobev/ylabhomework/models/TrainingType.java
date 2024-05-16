package ru.vladimirvorobev.ylabhomework.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Сущность типа тренировкок.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingType {

    private int id;

    @NotEmpty(message = "Training type name should not be empty")
    private String name;

}
