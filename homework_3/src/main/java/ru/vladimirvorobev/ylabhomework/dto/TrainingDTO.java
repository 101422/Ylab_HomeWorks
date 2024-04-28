package ru.vladimirvorobev.ylabhomework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

import java.sql.Date;
import java.util.List;

/**
 * DTO тренировки.
 **/
/*@Getter
@Setter
@NoArgsConstructor*/
public class TrainingDTO {

    private String personName;
    private String trainingTypeName;
    private Date date;
    private int duration;
    private int amountOfCalories;
    private List<TrainingAdditionalInformationDTO> trainingAdditionalInformation;

    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public String getTrainingTypeName() {
        return trainingTypeName;
    }
    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmountOfCalories() {
        return amountOfCalories;
    }
    public void setAmountOfCalories(int amountOfCalories) {
        this.amountOfCalories = amountOfCalories;
    }

    public List<TrainingAdditionalInformationDTO> getTrainingAdditionalInformation() {
        return trainingAdditionalInformation;
    }

    public void setTrainingAdditionalInformation(List<TrainingAdditionalInformationDTO> trainingAdditionalInformation) {
        this.trainingAdditionalInformation = trainingAdditionalInformation;
    }

    public TrainingDTO(String personName, String trainingTypeName, Date date, int duration, int amountOfCalories, List<TrainingAdditionalInformationDTO> trainingAdditionalInformation) {
        this.personName = personName;
        this.trainingTypeName = trainingTypeName;
        this.date = date;
        this.duration = duration;
        this.amountOfCalories = amountOfCalories;
        this.trainingAdditionalInformation = trainingAdditionalInformation;
    }

    public TrainingDTO() {

    }
}
