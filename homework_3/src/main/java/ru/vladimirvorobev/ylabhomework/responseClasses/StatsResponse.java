package ru.vladimirvorobev.ylabhomework.responseClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vladimirvorobev.ylabhomework.util.Stats;

import java.util.List;
/*
@Getter
@Setter
@AllArgsConstructor*/
public class StatsResponse {

    private List<Stats> stats;

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public StatsResponse(List<Stats> stats) {
        this.stats = stats;
    }
}
