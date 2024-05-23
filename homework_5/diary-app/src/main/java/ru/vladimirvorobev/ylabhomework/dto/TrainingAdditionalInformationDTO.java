package ru.vladimirvorobev.ylabhomework.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO дополнительной информации о тренировках.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingAdditionalInformationDTO {
    @NotEmpty(message = "Training additional information key should not be empty")
    private String key;
    private String value;

}
