package ru.vladimirvorobev.ylabhomework.models;

import lombok.*;

/**
 * Сущность дополнительной информации о тренировках.
 **/
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString*/
public class TrainingAdditionalInformation {

    private int id;
    private String key;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TrainingAdditionalInformation(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public TrainingAdditionalInformation() {

    }
}
