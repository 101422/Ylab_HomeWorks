package ru.vladimirvorobev.ylabhomework.responseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;

import java.util.List;
/*
@Getter
@Setter
@AllArgsConstructor*/
public class TrainingAdditionalInformationResponse {

    private List<TrainingAdditionalInformationDTO> trainingAdditionalInformation;

    public List<TrainingAdditionalInformationDTO> getTrainingAdditionalInformation() {
        return trainingAdditionalInformation;
    }

    public void setTrainingAdditionalInformation(List<TrainingAdditionalInformationDTO> trainingAdditionalInformation) {
        this.trainingAdditionalInformation = trainingAdditionalInformation;
    }

    public TrainingAdditionalInformationResponse(List<TrainingAdditionalInformationDTO> trainingAdditionalInformation) {
        this.trainingAdditionalInformation = trainingAdditionalInformation;
    }
}
