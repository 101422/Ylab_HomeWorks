package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.models.Training;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

@Mapper
public interface TrainingMapper {

   /* @Mapping(target = "name", source = "name")
    TrainingTypeDTO trainingTypeToTrainingTypeDTO(TrainingType trainingType);*/


    TrainingDTO trainingToTrainingDTO(Training training);
    Training trainingDTOToTraining(TrainingDTO trainingDTO);

}
