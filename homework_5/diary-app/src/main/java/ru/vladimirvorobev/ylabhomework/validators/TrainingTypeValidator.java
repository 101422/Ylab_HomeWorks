package ru.vladimirvorobev.ylabhomework.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

/**
 * Класс-валидатор для типов тренировок.
 **/
@Component
public class TrainingTypeValidator implements Validator {

    private final TrainingTypeDAOImpl trainingTypeDAOImpl;
    
    public TrainingTypeValidator(TrainingTypeDAOImpl trainingTypeDAOImpl) {
        this.trainingTypeDAOImpl = trainingTypeDAOImpl;
    }

    public boolean supports(Class<?> aClass) {
        return TrainingType.class.equals(aClass);
    }


    /**
     * Валидация типа тренировок.
     *
     * @param o объект валидации
     * @param errors ошибки валидации
     **/
    @Override
    public void validate(Object o, Errors errors) {
        TrainingType trainingType = (TrainingType) o;

        if (trainingTypeDAOImpl.findByName(trainingType.getName()).isPresent())
            errors.rejectValue("name", "", "Training type already exists");
    }

}
