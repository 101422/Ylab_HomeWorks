package ru.vladimirvorobev.ylabhomework.responseClasses;

import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import java.util.List;

/**
 * Класс для упаковки дополнительной информации о тренировках для передачи в виде json.
 **/
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
