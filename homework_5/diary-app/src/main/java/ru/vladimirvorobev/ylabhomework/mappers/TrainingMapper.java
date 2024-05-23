package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.models.Training;

/**
 * Преобразование сущность-DTO тренировки интерфейс.
 **/
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingMapper {

    TrainingDTO trainingToTrainingDTO(Training training);
    Training trainingDTOToTraining(TrainingDTO trainingDTO);

}
