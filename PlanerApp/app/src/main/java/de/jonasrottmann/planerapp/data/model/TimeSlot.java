package de.jonasrottmann.planerapp.data.model;

import android.util.SparseArray;

/**
 * Created by Jonas Rottmann on 04.02.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class TimeSlot {

    private static final SparseArray<String> timeSlots = new SparseArray<>();

    static {
        timeSlots.append(0, "08:30");
        timeSlots.append(1, "09:00");
        timeSlots.append(2, "10:00");
        timeSlots.append(3, "11:00");
        timeSlots.append(4, "12:00");
        timeSlots.append(5, "13:00");
        timeSlots.append(6, "14:00");
        timeSlots.append(7, "15:00");
        timeSlots.append(8, "16:00");
    }

    private TimeSlot() {
    }

    public static String getTimeSlotForId(int id) {
        return timeSlots.get(id);
    }
}
