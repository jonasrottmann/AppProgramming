package de.jonasrottmann.planerapp.data;

import android.util.SparseArray;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Course {

    private Integer id;
    private String name;
    private String teacher;
    private String room;
    private int timeslot;
    private int weekday;
    private int category;
    private int starred;

    Course(int id, String name, String teacher, String room, int timeslot, int weekday, int category, int starred) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.room = room;
        this.timeslot = timeslot;
        this.weekday = weekday;
        this.category = category;
        this.starred = starred;
    }

    Course(String name, String teacher, String room, int timeslot, int weekday, int category) {
        this.id = null;
        this.name = name;
        this.teacher = teacher;
        this.room = room;
        this.timeslot = timeslot;
        this.weekday = weekday;
        this.category = category;
        this.starred = 0;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", starred=" + starred +
            '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public int getWeekday() {
        return weekday;
    }

    public int getCategory() {
        return category;
    }

    public int getStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred ? 1 : 0;
    }

    public static class TimeSlot {

        private TimeSlot() {
        }

        private static SparseArray<String> timeSlots = new SparseArray<>();

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

        public static String getTimeSlotForId(int id) {
            return timeSlots.get(id);
        }
    }

    public static class Category {

        private Category() {
        }

        private static SparseArray<String> categories = new SparseArray<>();

        static {
            categories.append(0, "Denken");
            categories.append(1, "Handwerk/Kunst");
            categories.append(2, "Musik");
            categories.append(3, "Sozialwissenschaften");
            categories.append(4, "Sport/Tanz");
            categories.append(5, "Deutsch und Fremdsprachen");
            categories.append(6, "Wissenschaften");
            categories.append(7, "Sonstiges");
        }

        public static String getCategoryForId(int id) {
            return categories.get(id);
        }
    }
}
