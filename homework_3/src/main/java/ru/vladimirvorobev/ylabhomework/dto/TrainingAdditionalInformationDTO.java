package ru.vladimirvorobev.ylabhomework.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO дополнительной информации о тренировках.
 **/
/*@Getter
@Setter*/
public class TrainingAdditionalInformationDTO {


    private String key;
    private String value;

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

    public TrainingAdditionalInformationDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public TrainingAdditionalInformationDTO() {}

}
