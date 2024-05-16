package ru.vladimirvorobev.ylabhomework.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladimirvorobev.ylabhomework.dto.TrainingDTO;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;
import ru.vladimirvorobev.ylabhomework.services.AuthorizationService;
import ru.vladimirvorobev.ylabhomework.services.TrainingService;
import java.util.Optional;

/**
 * Класс-валидатор для DTO тренировки.
 **/
@Component
public class TrainingDTOValidator implements Validator {

    private final TrainingService trainingService;
    private final AuthorizationService authorizationService;

    public TrainingDTOValidator(TrainingService trainingService, AuthorizationService authorizationService) {
        this.trainingService = trainingService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingType.class.equals(aClass);
    }

    /**
     * Валидация DTO тренировки.
     *
     * @param o объект валидации
     * @param errors ошибки валидации
     **/
    @Override
    public void validate(Object o, Errors errors) {
        TrainingDTO trainingDTO = (TrainingDTO) o;

        Optional<Person> optionalPerson = authorizationService.findPersonByName(trainingDTO.getPersonName());

        if (optionalPerson.isEmpty())
            errors.rejectValue("personName", "", "A person wasn't found");

        Optional<TrainingType> optionalTrainingType = trainingService.findTrainingTypeByName(trainingDTO.getTrainingTypeName());

        if (optionalTrainingType.isEmpty())
            errors.rejectValue("trainingTypeName", "", "A training type wasn't found");

        if (optionalPerson.isPresent() && optionalTrainingType.isPresent())
            if (trainingService.findTrainingsByPersonNameTypeNameDate(trainingDTO.getPersonName(), trainingDTO.getTrainingTypeName(), trainingDTO.getDate()).isPresent())
                errors.rejectValue("personName", "", "Training of this person, type and date already exists");

        trainingDTO.getTrainingAdditionalInformation().stream().forEach(trainingAdditionalInformationDTO -> {

            if (trainingAdditionalInformationDTO.getKey().isEmpty())
                errors.rejectValue("trainingAdditionalInformation", "", "Training additional information key should not be empty");
        });
    }

}
