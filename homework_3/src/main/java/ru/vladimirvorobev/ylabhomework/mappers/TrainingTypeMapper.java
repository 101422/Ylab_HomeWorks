package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingTypeMapper {

    TrainingTypeDTO trainingTypeToTrainingTypeDTO(TrainingType trainingType);
    TrainingType trainingTypeDTOToTrainingType(TrainingTypeDTO trainingTypeDTO);

}
