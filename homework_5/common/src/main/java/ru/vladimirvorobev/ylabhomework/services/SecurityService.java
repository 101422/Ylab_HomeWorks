package ru.vladimirvorobev.ylabhomework.services;

import java.util.HashMap;

/**
 * Сервис полномочий пользователей.
 **/
public class SecurityService {
    public static HashMap<String, Boolean> credentials = new HashMap<>();
    public static String name = "";

    static {
        credentials.put("isAuthorized", false);
        credentials.put("isAdmin", false);
    }

}
