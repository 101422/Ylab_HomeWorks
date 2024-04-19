package ru.vladimirvorobev.ylabhomework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.vladimirvorobev.ylabhomework.security.Role;

/**
 * Сущность пользователя.
 **/
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Person {

    private String name;
    private String password;
    private Role role;

}
