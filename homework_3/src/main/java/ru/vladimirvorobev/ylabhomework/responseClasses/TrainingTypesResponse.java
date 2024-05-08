package ru.vladimirvorobev.ylabhomework.responseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;

import java.util.List;
/*
@Getter
@Setter
@AllArgsConstructor*/
public class TrainingTypesResponse {

    private List<TrainingTypeDTO> trainingTypes;

    public List<TrainingTypeDTO> getTrainingTypes() {
        return trainingTypes;
    }

    public void setTrainingTypes(List<TrainingTypeDTO> trainingTypes) {
        this.trainingTypes = trainingTypes;
    }

    public TrainingTypesResponse(List<TrainingTypeDTO> trainingTypes) {
        this.trainingTypes = trainingTypes;
    }
}
