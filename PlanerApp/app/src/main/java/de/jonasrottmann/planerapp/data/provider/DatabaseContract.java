package de.jonasrottmann.planerapp.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import java.lang.annotation.Retention;

import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_CAT;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_ICON;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_ID;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_NAME;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_ROOM;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_STAR;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_TEACHER;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_TIMESLOT;
import static de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns.COLUMN_WEEKDAY;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Jonas Rottmann on 28.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class DatabaseContract {
    public static final String AUTHORITY = "de.jonasrottmann.planerapp";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class Course {
        public static final String TABLE_NAME = "course";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

        public static final String[] COLUMNS = { COLUMN_ID, COLUMN_NAME, COLUMN_TEACHER, COLUMN_ROOM, COLUMN_TIMESLOT, COLUMN_WEEKDAY, COLUMN_CAT, COLUMN_STAR, COLUMN_ICON };

        @Retention(SOURCE)
        @StringDef({
            Columns.COLUMN_ID, Columns.COLUMN_NAME, Columns.COLUMN_TEACHER, Columns.COLUMN_ROOM, Columns.COLUMN_TIMESLOT, Columns.COLUMN_WEEKDAY, Columns.COLUMN_CAT, Columns.COLUMN_STAR,
            Columns.COLUMN_ICON
        })
        public @interface Columns {
            String COLUMN_ID = BaseColumns._ID;
            String COLUMN_NAME = "name";
            String COLUMN_TEACHER = "teacher";
            String COLUMN_ROOM = "room";
            String COLUMN_TIMESLOT = "timeslot";
            String COLUMN_WEEKDAY = "weekday";
            String COLUMN_CAT = "category";
            String COLUMN_STAR = "starred";
            String COLUMN_ICON = "icon";
        }


        @Retention(SOURCE)
        @IntDef({
            Index.COLUMN_ID, Index.COLUMN_NAME, Index.COLUMN_TEACHER, Index.COLUMN_ROOM, Index.COLUMN_TIMESLOT, Index.COLUMN_WEEKDAY, Index.COLUMN_CAT, Index.COLUMN_STAR, Index.COLUMN_ICON
        })
        public @interface Index {
            int COLUMN_ID = 0;
            int COLUMN_NAME = 1;
            int COLUMN_TEACHER = 2;
            int COLUMN_ROOM = 3;
            int COLUMN_TIMESLOT = 4;
            int COLUMN_WEEKDAY = 5;
            int COLUMN_CAT = 6;
            int COLUMN_STAR = 7;
            int COLUMN_ICON = 8;
        }
    }
}
