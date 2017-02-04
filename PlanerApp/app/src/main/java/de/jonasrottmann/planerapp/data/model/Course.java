package de.jonasrottmann.planerapp.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import de.jonasrottmann.planerapp.data.provider.DatabaseContract;
import java.util.Calendar;
import lombok.Getter;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
@Getter
public class Course implements Parcelable {
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

    private final Integer id;
    private final String name;
    private final String teacher;
    private final String room;
    private final int timeslot;
    private final int weekday;
    private final int category;
    private final int icon;
    private int starred;

    /**
     * Build {@link Course} object based on a {@link Cursor}.
     *
     * @param cursor A {@link Cursor}. Make sure it points to a row containing a course.
     */
    public Course(@NonNull Cursor cursor) {
        this.id = cursor.getInt(DatabaseContract.Course.Index.COLUMN_ID);
        this.name = cursor.getString(DatabaseContract.Course.Index.COLUMN_NAME);
        this.teacher = cursor.getString(DatabaseContract.Course.Index.COLUMN_TEACHER);
        this.room = cursor.getString(DatabaseContract.Course.Index.COLUMN_ROOM);
        this.timeslot = cursor.getInt(DatabaseContract.Course.Index.COLUMN_TIMESLOT);
        this.weekday = cursor.getInt(DatabaseContract.Course.Index.COLUMN_WEEKDAY);
        this.category = cursor.getInt(DatabaseContract.Course.Index.COLUMN_CAT);
        this.starred = cursor.getInt(DatabaseContract.Course.Index.COLUMN_STAR);
        this.icon = cursor.getInt(DatabaseContract.Course.Index.COLUMN_ICON);
    }

    private Course(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        teacher = in.readString();
        room = in.readString();
        timeslot = in.readInt();
        weekday = in.readInt();
        category = in.readInt();
        starred = in.readInt();
        icon = in.readInt();
    }

    public boolean getStarred() {
        return starred != 0;
    }

    /**
     * Used to schedule notifications.
     *
     * @return Milliseconds based on weekday and time of day
     */
    public long getTimeslotInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, weekday + 2);
        calendar.set(Calendar.HOUR_OF_DAY, timeslot + 8);
        if (timeslot == 0) {
            calendar.set(Calendar.MINUTE, 30);
        }
        return calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", starred=" + starred +
            '}';
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
        dest.writeInt(icon);
    }
}