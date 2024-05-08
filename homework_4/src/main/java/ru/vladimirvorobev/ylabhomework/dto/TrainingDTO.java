package ru.vladimirvorobev.ylabhomework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.List;

/**
 * DTO тренировки.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {

    private int id;
    private String personName;
    private String trainingTypeName;
    private Date date;
    private int duration;
    private int amountOfCalories;
    private List<TrainingAdditionalInformationDTO> trainingAdditionalInformation;

}
