package ru.vladimirvorobev.ylabhomework.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.vladimirvorobev.ylabhomework.security.Role;

/**
 * Сущность пользователя.
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Person {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    @NotEmpty(message = "Person name should not be empty")
    private String name;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NonNull
    private Role role;

}
