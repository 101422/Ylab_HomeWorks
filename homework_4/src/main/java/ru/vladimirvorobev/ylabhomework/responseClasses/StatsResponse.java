package ru.vladimirvorobev.ylabhomework.responseClasses;

import ru.vladimirvorobev.ylabhomework.util.Stats;
import java.util.List;

/**
 * Класс для упаковки статистики о тренировках для передачи в виде json.
 **/
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
