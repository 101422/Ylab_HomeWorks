package ru.vladimirvorobev.ylabhomework.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladimirvorobev.ylabhomework.daoClasses.PersonDAOImpl;
import ru.vladimirvorobev.ylabhomework.models.Person;
import ru.vladimirvorobev.ylabhomework.models.TrainingType;

/**
 * Класс-валидатор для авторизации пользователей.
 **/
@Component
public class PersonAuthorizationValidator implements Validator {

    private final PersonDAOImpl personDAOImpl;

    public PersonAuthorizationValidator(PersonDAOImpl personDAOImpl) {
        this.personDAOImpl = personDAOImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TrainingType.class.equals(aClass);
    }

    /**
     * Валидация пользователей.
     *
     * @param o объект валидации
     * @param errors ошибки валидации
     **/
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (person.getName().isEmpty ())
            errors.rejectValue("name", "", "Person name should not be empty");
    }

}
