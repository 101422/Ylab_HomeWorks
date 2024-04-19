package ru.vladimirvorobev.ylabhomework.security;

import lombok.RequiredArgsConstructor;

/**
 * enum с ролями пользователей.
 **/
@RequiredArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String value;



}
