package ru.vladimirvorobev.ylabhomework.responseClasses;

import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import java.util.List;

/**
 * Класс для упаковки типов тренировок для передачи в виде json.
 **/
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
