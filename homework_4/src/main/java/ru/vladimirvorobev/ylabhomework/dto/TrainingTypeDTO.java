package ru.vladimirvorobev.ylabhomework.dto;

import lombok.*;
import jakarta.validation.constraints.NotEmpty;

/**
 * DTO типа тренировкок.
 **/
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTypeDTO {

    @NotEmpty(message = "Training type name should not be empty")
    private String name;

}
