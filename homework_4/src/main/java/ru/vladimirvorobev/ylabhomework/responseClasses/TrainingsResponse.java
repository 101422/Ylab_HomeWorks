package ru.vladimirvorobev.ylabhomework.responseClasses;

import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import java.util.List;

/**
 * Класс для упаковкитренировок для передачи в виде json.
 **/
public class TrainingsResponse {

    private List<TrainingDTO> trainings;


    public List<TrainingDTO> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingDTO> trainings) {
        this.trainings = trainings;
    }

    public TrainingsResponse(List<TrainingDTO> trainings) {
        this.trainings = trainings;
    }
}
