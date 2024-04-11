package ru.vvi.sbertask.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String name;

    private String region;

    private String district;

    private int population;

    private String foundation;

    @Override
    public String toString() {
        return "City" + String.format("{name='%s',region='%s',district='%s',population=%d,foundation='%s'}", name, region, district, population, foundation);

    }

}
