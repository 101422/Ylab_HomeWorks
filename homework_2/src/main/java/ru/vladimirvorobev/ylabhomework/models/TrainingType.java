package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

/**
 * Сущность типа тренировкок.
 **/
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class TrainingType {

    private int id;
    @NonNull
    private String name;

}
