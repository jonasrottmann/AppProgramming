package de.jonasrottmann.planerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;
import de.jonasrottmann.planerapp.R;

import static de.jonasrottmann.planerapp.data.Course.COLUMN_CAT;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_ICON;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_NAME;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_ROOM;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_STAR;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_TEACHER;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_TIMESLOT;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_WEEKDAY;
import static de.jonasrottmann.planerapp.data.Course.TABLE_NAME;

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class CourseDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";


    public CourseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create schema
        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS \""
            + TABLE_NAME
            + "\" (\n"
            + "  \"_id\" integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "  \"name\" text NOT NULL,\n"
            + "  \"teacher\" text,\n"
            + "  \"room\" text,\n"
            + "  \"timeslot\" integer NOT NULL,\n"
            + "  \"weekday\" integer NOT NULL,\n"
            + "  \"category\" integer,\n"
            + "  \"starred\" integer NOT NULL DEFAULT(0),\n"
            + "  \"icon\" integer NOT NULL\n"
            + ");";
        db.execSQL(CREATE_COURSE_TABLE);

        // Create seed database entries
        createCourse(db, new Course("Asien-Versammlung", null, "Russland", 0, 0, 7, R.drawable.placeholder));
        createCourse(db, new Course("Chemie III", "Jan", "Mongolei", 1, 0, 6, R.drawable.placeholder));
        createCourse(db, new Course("Deutsch Werkstatt", "Doreen, Dörte", "Japan", 1, 0, 5, R.drawable.german_workshop));
        createCourse(db, new Course("Garten", "Alex", "Garten", 1, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Impro-Theater", "Julia", "Russland", 1, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 1, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Lesen, schreiben, rechnen", "Hagen, Felix", "USA", 1, 0, 5, R.drawable.read_write_calc));
        createCourse(db, new Course("Was interessiert mich", "Sebastian", "Panama", 1, 0, 0, R.drawable.placeholder));
        createCourse(db, new Course("Chemie III", "Jan", "Mongolei", 2, 0, 6, R.drawable.placeholder));
        createCourse(db, new Course("Deutsch Werkstatt", "Doreen, Dörte", "Japan", 2, 0, 5, R.drawable.german_workshop));
        createCourse(db, new Course("Garten", "Alex", "Garten", 2, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 2, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Kritisches Denken", "Henrik", "Bhutan", 2, 0, 0, R.drawable.placeholder));
        createCourse(db, new Course("Mathe Werkstatt", "Auré", "Tibet", 2, 0, 0, R.drawable.math));
        createCourse(db, new Course("Physik I", "Martin", "Indien", 2, 0, 6, R.drawable.placeholder));
        createCourse(db, new Course("Schach", "Olaf", "Mexico", 2, 0, 0, R.drawable.placeholder));
        createCourse(db, new Course("Schmuck", "Grit-Ute", "China", 2, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Sport bis 3. Klasse", "Hagen", null, 2, 0, 4, R.drawable.placeholder));
        createCourse(db, new Course("Flexizeit", null, null, 3, 0, 7, R.drawable.placeholder));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 3, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Lernbüro", null, "Tibet", 3, 0, 7, R.drawable.placeholder));
        createCourse(db, new Course("Schach", "Olaf", "Mexico", 3, 0, 0, R.drawable.placeholder));
        createCourse(db, new Course("Schmuck", "Grit-Ute", "China", 3, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Sport ab 4. Klasse", "Hagen", null, 3, 0, 4, R.drawable.placeholder));
        createCourse(db, new Course("VZK", "Sebastian, Felix", "Malaysia", 3, 0, 7, R.drawable.placeholder));
        createCourse(db, new Course("Chemie II", "Jan", "Mongolei", 5, 0, 6, R.drawable.placeholder));
        createCourse(db, new Course("Englisch - Grundschule", "Daniela", "Panama", 5, 0, 5, R.drawable.placeholder));
        createCourse(db, new Course("Grundschul-Teamtreff", null, null, 5, 0, 7, R.drawable.placeholder));
        createCourse(db, new Course("Japanisch", "Atsuko", "China", 5, 0, 5, R.drawable.placeholder));
        createCourse(db, new Course("Nähen", "Dörte", "Nähzimmer", 5, 0, 1, R.drawable.placeholder));
        createCourse(db, new Course("Englisch - Grundschule", "Daniela", "Panama", 6, 0, 5, R.drawable.placeholder));
        createCourse(db, new Course("Gemeinschaftskunde", "Paula", "Tibet", 6, 0, 3, R.drawable.placeholder));
        createCourse(db, new Course("Japanisch", "Atsuko", "China", 6, 0, 5, R.drawable.placeholder));
        createCourse(db, new Course("Gemeinschaftskunde", "Paula", "Tibet", 7, 0, 3, R.drawable.placeholder));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    private void createCourse(@NonNull SQLiteDatabase db, @NonNull Course course) {
        // 1. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, course.getName());
        values.put(COLUMN_TEACHER, course.getTeacher());
        values.put(COLUMN_ROOM, course.getRoom());
        values.put(COLUMN_TIMESLOT, course.getTimeslot());
        values.put(COLUMN_WEEKDAY, course.getWeekday());
        values.put(COLUMN_CAT, course.getCategory());
        values.put(COLUMN_ICON, course.getIcon());
        // 2. insert
        db.insert(TABLE_NAME, null, values);
    }

    Cursor getAllCourses(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(Course.TABLE_NAME);
        return sqliteQueryBuilder.query(getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    Cursor getCourse(int id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(Course.TABLE_NAME);
        sqliteQueryBuilder.appendWhere(Course.COLUMN_ID + " = " + id);
        if (sortOrder == null) {
            sortOrder = COLUMN_STAR;
        }
        return sqliteQueryBuilder.query(getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    public int updateCourse(int id, ContentValues values) {
        return getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[] { String.valueOf(id) });
    }
}
