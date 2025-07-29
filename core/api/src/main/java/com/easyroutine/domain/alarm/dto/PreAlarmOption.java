package com.easyroutine.domain.alarm.dto;

public enum PreAlarmOption {
    NONE(0),
    MINUTES_30(30),
    MINUTES_60(60),
    MINUTES_120(120),
    ;

    private final int minutes;

    PreAlarmOption(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
