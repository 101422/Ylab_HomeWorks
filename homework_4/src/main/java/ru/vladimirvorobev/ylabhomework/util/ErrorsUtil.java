package ru.vladimirvorobev.ylabhomework.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

/**
 * Класс для передачи сообщения об ошибке при получении некорректрых данных по HTTP.
 **/
public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        throw new TrainingsDiaryException(errorMsg.toString());
    }
}
