package ru.vladimirvorobev.ylabhomework.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
