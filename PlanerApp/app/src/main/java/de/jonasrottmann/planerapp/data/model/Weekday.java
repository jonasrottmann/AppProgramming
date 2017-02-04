package de.jonasrottmann.planerapp.data.model;

import android.util.SparseArray;

/**
 * Created by Jonas Rottmann on 04.02.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Weekday {

    private static final SparseArray<String> weekdayStrings = new SparseArray<>();

    static {
        weekdayStrings.append(0, "Montag");
        weekdayStrings.append(1, "Dienstag");
        weekdayStrings.append(2, "Mittwoch");
        weekdayStrings.append(3, "Donnerstag");
        weekdayStrings.append(4, "Freitag");
    }

    private Weekday() {
    }

    public static String getWeekdayStringForId(int id) {
        return weekdayStrings.get(id);
    }
}
