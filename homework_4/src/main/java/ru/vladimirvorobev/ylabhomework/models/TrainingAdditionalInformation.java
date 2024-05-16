package ru.vladimirvorobev.ylabhomework.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Сущность дополнительной информации о тренировках.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingAdditionalInformation {

    private int id;
    @NotEmpty(message = "Training additional information key should not be empty")
    private String key;
    private String value;

}
