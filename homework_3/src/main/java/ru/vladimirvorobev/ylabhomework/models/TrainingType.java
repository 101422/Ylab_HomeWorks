package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

/**
 * Сущность типа тренировкок.
 **/
/*@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString*/
public class TrainingType {

    private int id;
    @NonNull
    private String name;

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

    public TrainingType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TrainingType(String name) {
        this.id = id;
        this.name = name;
    }

    public TrainingType() {
    }
}
