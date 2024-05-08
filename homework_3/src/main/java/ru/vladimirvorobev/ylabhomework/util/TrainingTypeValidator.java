package ru.vladimirvorobev.ylabhomework.util;

import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingTypeValidator {

    public List<String> validate(Object o) {
        TrainingTypeDTO trainingTypeDTO = (TrainingTypeDTO) o;

        List<String>  errors = new ArrayList<>();

        if (trainingTypeDTO.getName().isEmpty())
            errors.add("Имя типа тренировки не может быть пустым");

        return errors;
    }
}
