package ru.vladimirvorobev.ylabhomework.util;

import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;

import java.util.ArrayList;
import java.util.List;

public class TrainingValidator {

    public  List<String>  validate(Object o) {
        TrainingDTO trainingDTO = (TrainingDTO) o;

        List<String> errors = new ArrayList<>();

        if (trainingDTO.getDate() == null)
            errors.add("Дата не может быть пустым");
        if (trainingDTO.getPersonName().equals(""))
            errors.add("Пользователь не может быть пустым");
        if (trainingDTO.getTrainingTypeName().equals(""))
            errors.add("Тип тренировки не может быть пустым");

        return errors;
    }
}
