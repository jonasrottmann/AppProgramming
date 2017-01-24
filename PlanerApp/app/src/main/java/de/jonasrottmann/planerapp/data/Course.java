package de.jonasrottmann.planerapp.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Course implements Parcelable {

    static final String TABLE_COURSES = "course";
    static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEACHER = "teacher";
    public static final String COLUMN_ROOM = "room";
    public static final String COLUMN_TIMESLOT = "timeslot";
    public static final String COLUMN_WEEKDAY = "weekday";
    public static final String COLUMN_CAT = "category";
    public static final String COLUMN_STAR = "starred";
    public static final String[] COLUMNS = { COLUMN_ID, COLUMN_NAME, COLUMN_TEACHER, COLUMN_ROOM, COLUMN_TIMESLOT, COLUMN_WEEKDAY, COLUMN_CAT, COLUMN_STAR };

    private final Integer id;
    private final String name;
    private final String teacher;
    private final String room;
    private final int timeslot;
    private final int weekday;
    private final int category;
    private int starred;

    /**
     * Used when querying {@link Course}s from the database.
     */
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

    /**
     * Used to add {@link Course} to database. Id and starred aren't passed because they have default values.
     */
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

    public boolean getStarred() {
        return starred != 0;
    }

    public void setStarred(boolean starred) {
        this.starred = starred ? 1 : 0;
    }

    public static class TimeSlot {

        private TimeSlot() {
        }

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

        public static String getTimeSlotForId(int id) {
            return timeSlots.get(id);
        }
    }

    public static class Category {

        private Category() {
        }

        private static final SparseArray<String> categories = new SparseArray<>();

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


    //region Parcelable
    private Course(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        teacher = in.readString();
        room = in.readString();
        timeslot = in.readInt();
        weekday = in.readInt();
        category = in.readInt();
        starred = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(teacher);
        dest.writeString(room);
        dest.writeInt(timeslot);
        dest.writeInt(weekday);
        dest.writeInt(category);
        dest.writeInt(starred);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
    //endregion
}