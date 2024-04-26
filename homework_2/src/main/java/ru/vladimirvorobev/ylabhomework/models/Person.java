package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;
import ru.vladimirvorobev.ylabhomework.security.Role;

/**
 * Сущность пользователя.
 **/
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Person {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private Role role;

}
