package com.easyroutine.domain.routine_history;

import java.time.LocalDate;

public enum RoutineHistoryPeriod {
    WEEK, MONTH, QUARTER, HALF, YEAR, ALL;

    public LocalDate toStartDate(LocalDate endDate) {
        return switch (this) {
            case WEEK -> endDate.minusWeeks(1);
            case MONTH -> endDate.minusMonths(1);
            case QUARTER -> endDate.minusMonths(3);
            case HALF -> endDate.minusMonths(6);
            case YEAR -> endDate.minusYears(1);
            case ALL -> LocalDate.of(2000, 1, 1);
        };
    }
}

