package ru.vladimirvorobev.ylabhomework.responseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;

import java.util.List;

/*
@Getter
@Setter
@AllArgsConstructor*/
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
