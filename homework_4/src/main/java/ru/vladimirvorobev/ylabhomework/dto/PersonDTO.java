package ru.vladimirvorobev.ylabhomework.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * DTO пользователя.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PersonDTO {
    @NotEmpty(message = "Person name should not be empty")
    private String name;
    @NotEmpty(message = "Password should not be empty")
    private String password;

}
