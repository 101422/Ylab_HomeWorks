package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;

@Mapper
public interface TrainingAdditionalInformationMapper {

   /* @Mapping(target = "name", source = "name")
    TrainingTypeDTO trainingTypeToTrainingTypeDTO(TrainingType trainingType);*/


    TrainingAdditionalInformationDTO trainingAdditionalInformationToTrainingAdditionalInformationDTO(TrainingAdditionalInformation trainingAdditionalInformation);
    TrainingAdditionalInformation trainingAdditionalInformationDTOToTrainingAdditionalInformation(TrainingAdditionalInformationDTO trainingAdditionalInformationDTO);

}
