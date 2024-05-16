package ru.vladimirvorobev.ylabhomework.security;

/**
 * enum с ролями пользователей.
 **/
public enum Role {

    ADMIN("ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
