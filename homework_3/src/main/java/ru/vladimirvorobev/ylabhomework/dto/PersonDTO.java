package ru.vladimirvorobev.ylabhomework.dto;

import lombok.*;

/**
 * DTO пользователя.
 **/
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor*/
public class PersonDTO {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public PersonDTO() {

    }
}
