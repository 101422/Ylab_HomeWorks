package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

/**
 * Преобразование сущность-DTO типов тренирово интерфейс.
 **/
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingTypeMapper {

    TrainingTypeDTO trainingTypeToTrainingTypeDTO(TrainingType trainingType);
    TrainingType trainingTypeDTOToTrainingType(TrainingTypeDTO trainingTypeDTO);

}
