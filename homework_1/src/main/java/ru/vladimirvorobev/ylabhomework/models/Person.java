package ru.vladimirvorobev.ylabhomework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.security.Role;

/**
 * Сущность пользователя.
 **/
@Getter
@Setter
@AllArgsConstructor
public class Person {

    private int id;
    private String name;
    private String password;
    private Role role;

    /**
     * Получение представления сущности пользователя.
     *
     * @return представление сущности пользователя.
     **/
    @Override
    public String toString() {
        return "Person {" +
                "id = " + id +
                ", name = " + name +
                ", role = '" + role +
                '}';
    }
}
