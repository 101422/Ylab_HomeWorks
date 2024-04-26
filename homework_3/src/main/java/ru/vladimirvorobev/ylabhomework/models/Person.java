package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;
import ru.vladimirvorobev.ylabhomework.security.Role;

/**
 * Сущность пользователя.
 **/
/*@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString*/
public class Person {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Person(int id, @NonNull String name, @NonNull String password, @NonNull Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }


    public Person(@NonNull String name, @NonNull String password, @NonNull Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Person() {
    }
}
