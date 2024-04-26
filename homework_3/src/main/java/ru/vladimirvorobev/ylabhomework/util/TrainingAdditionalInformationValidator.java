package ru.vladimirvorobev.ylabhomework.util;

import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class TrainingAdditionalInformationValidator {

    public List<String> validate(Object o) {
        TrainingAdditionalInformationDTO trainingAdditionalInformationDTO = (TrainingAdditionalInformationDTO) o;

        List<String>  errors = new ArrayList<>();

        if (trainingAdditionalInformationDTO.getKey().isEmpty())
            errors.add("Ключ дополнительнй информации не может быть пустым");

        return errors;
    }
}
