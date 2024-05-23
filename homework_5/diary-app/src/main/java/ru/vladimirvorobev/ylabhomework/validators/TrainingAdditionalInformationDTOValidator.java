package ru.vladimirvorobev.ylabhomework.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladimirvorobev.ylabhomework.daoClasses.TrainingTypeDAOImpl;
import ru.vladimirvorobev.ylabhomework.dto.TrainingAdditionalInformationDTO;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

/**
 * Класс-валидатор для DTO дополнительой информации о тренировке.
 **/
@Component
public class TrainingAdditionalInformationDTOValidator implements Validator {

    private final TrainingTypeDAOImpl trainingTypeDAOImpl;

    public TrainingAdditionalInformationDTOValidator(TrainingTypeDAOImpl trainingTypeDAOImpl) {
        this.trainingTypeDAOImpl = trainingTypeDAOImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingType.class.equals(aClass);
    }

    /**
     * Валидация DTO дополнительой информации о тренировке.
     *
     * @param o объект валидации
     * @param errors ошибки валидации
     **/
    @Override
    public void validate(Object o, Errors errors) {
        TrainingAdditionalInformationDTO trainingAdditionalInformationDTO = (TrainingAdditionalInformationDTO) o;

        if (trainingAdditionalInformationDTO.getKey().isEmpty())
            errors.rejectValue("key", "", "Training additional information key should not be empty");
    }

}
