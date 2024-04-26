package ru.vladimirvorobev.ylabhomework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO типа тренировкок.
 **/
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor*/
public class TrainingTypeDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingTypeDTO(String name) {
        this.name = name;
    }

    public TrainingTypeDTO() {

    }
}
