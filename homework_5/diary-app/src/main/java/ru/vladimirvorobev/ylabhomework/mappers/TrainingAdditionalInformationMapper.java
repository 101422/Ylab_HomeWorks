package ru.vladimirvorobev.ylabhomework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import ru.vladimirvorobev.ylabhomework.models.TrainingAdditionalInformation;

/**
 * Преобразование сущность-DTO дополнительной информации о тренировке интерфейс.
 **/
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrainingAdditionalInformationMapper {

    TrainingAdditionalInformationDTO trainingAdditionalInformationToTrainingAdditionalInformationDTO(TrainingAdditionalInformation trainingAdditionalInformation);
    TrainingAdditionalInformation trainingAdditionalInformationDTOToTrainingAdditionalInformation(TrainingAdditionalInformationDTO trainingAdditionalInformationDTO);

}
