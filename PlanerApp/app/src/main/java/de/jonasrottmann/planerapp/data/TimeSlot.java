package de.jonasrottmann.planerapp.data;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public enum TimeSlot {
    MORNING("08:30"),
    FIRST("09:00"),
    SECOND("10:00"),
    THRID("11:00"),
    BREAK("12:00"),
    FORTH("13:00"),
    FIFTH("14:00"),
    SIXTH("15:00"),
    SEVENTH("16:00"),
    ;

    private final String text;

    TimeSlot(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
