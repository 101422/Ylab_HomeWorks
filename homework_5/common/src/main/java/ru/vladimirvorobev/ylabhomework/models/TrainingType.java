package ru.vladimirvorobev.ylabhomework.models;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @NotEmpty(message = "Training type name should not be empty")
    private String name;

}
