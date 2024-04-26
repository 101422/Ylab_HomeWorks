package ru.vladimirvorobev.ylabhomework.util;

import ru.vladimirvorobev.ylabhomework.dto.PersonDTO;
import ru.vladimirvorobev.ylabhomework.dto.TrainingTypeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonValidator {

    public  List<String>  validate(Object o) {
        PersonDTO personDTO = (PersonDTO) o;

        List<String> errors = new ArrayList<>();

        if (personDTO.getName().isEmpty())
            errors.add("Имя пользователя не может быть пустым");
        if (personDTO.getPassword().isEmpty())
            errors.add("Пароль пользователя не может быть пустым");

        return errors;
    }
}
