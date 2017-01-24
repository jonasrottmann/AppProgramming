package de.jonasrottmann.planerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;

import static de.jonasrottmann.planerapp.data.Course.COLUMNS;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_CAT;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_ID;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_NAME;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_ROOM;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_STAR;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_TEACHER;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_TIMESLOT;
import static de.jonasrottmann.planerapp.data.Course.COLUMN_WEEKDAY;
import static de.jonasrottmann.planerapp.data.Course.TABLE_COURSES;

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create schema
        String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS \""
            + TABLE_COURSES
            + "\" (\n"
            + "  \"id\" integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
            + "  \"name\" text NOT NULL,\n"
            + "  \"teacher\" text,\n"
            + "  \"room\" text,\n"
            + "  \"timeslot\" integer NOT NULL,\n"
            + "  \"weekday\" integer NOT NULL,\n"
            + "  \"category\" integer,\n"
            + "  \"starred\" integer NOT NULL DEFAULT(0)\n"
            + ");";
        db.execSQL(CREATE_COURSE_TABLE);

        // Create seed database entries
        createCourse(db, new Course("Asien-Versammlung", null, "Russland", 0, 0, 7));
        createCourse(db, new Course("Chemie III", "Jan", "Mongolei", 1, 0, 6));
        createCourse(db, new Course("Deutsch Werkstatt", "Doreen, Dörte", "Japan", 1, 0, 5));
        createCourse(db, new Course("Garten", "Alex", "Garten", 1, 0, 1));
        createCourse(db, new Course("Impro-Theater", "Julia", "Russland", 1, 0, 1));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 1, 0, 1));
        createCourse(db, new Course("Lesen, schreiben, rechnen", "Hagen, Felix", "USA", 1, 0, 5));
        createCourse(db, new Course("Was interessiert mich", "Sebastian", "Panama", 1, 0, 0));
        createCourse(db, new Course("Chemie III", "Jan", "Mongolei", 2, 0, 6));
        createCourse(db, new Course("Deutsch Werkstatt", "Doreen, Dörte", "Japan", 2, 0, 5));
        createCourse(db, new Course("Garten", "Alex", "Garten", 2, 0, 1));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 2, 0, 1));
        createCourse(db, new Course("Kritisches Denken", "Henrik", "Bhutan", 2, 0, 0));
        createCourse(db, new Course("Mathe Werkstatt", "Auré", "Tibet", 2, 0, 0));
        createCourse(db, new Course("Physik I", "Martin", "Indien", 2, 0, 6));
        createCourse(db, new Course("Schach", "Olaf", "Mexico", 2, 0, 0));
        createCourse(db, new Course("Schmuck", "Grit-Ute", "China", 2, 0, 1));
        createCourse(db, new Course("Sport bis 3. Klasse", "Hagen", null, 2, 0, 4));
        createCourse(db, new Course("Flexizeit", null, null, 3, 0, 7));
        createCourse(db, new Course("Kochen", "Pamela", "Hawaï Küche", 3, 0, 1));
        createCourse(db, new Course("Lernbüro", null, "Tibet", 3, 0, 7));
        createCourse(db, new Course("Schach", "Olaf", "Mexico", 3, 0, 0));
        createCourse(db, new Course("Schmuck", "Grit-Ute", "China", 3, 0, 1));
        createCourse(db, new Course("Sport ab 4. Klasse", "Hagen", null, 3, 0, 4));
        createCourse(db, new Course("VZK", "Sebastian, Felix", "Malaysia", 3, 0, 7));
        createCourse(db, new Course("Chemie II", "Jan", "Mongolei", 5, 0, 6));
        createCourse(db, new Course("Englisch - Grundschule", "Daniela", "Panama", 5, 0, 5));
        createCourse(db, new Course("Grundschul-Teamtreff", null, null, 5, 0, 7));
        createCourse(db, new Course("Japanisch", "Atsuko", "China", 5, 0, 5));
        createCourse(db, new Course("Nähen", "Dörte", "Nähzimmer", 5, 0, 1));
        createCourse(db, new Course("Englisch - Grundschule", "Daniela", "Panama", 6, 0, 5));
        createCourse(db, new Course("Gemeinschaftskunde", "Paula", "Tibet", 6, 0, 3));
        createCourse(db, new Course("Japanisch", "Atsuko", "China", 6, 0, 5));
        createCourse(db, new Course("Gemeinschaftskunde", "Paula", "Tibet", 7, 0, 3));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older course table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        // create fresh course table
        this.onCreate(db);
    }

    private Course createCourse(@NonNull SQLiteDatabase db, @NonNull Course course) {
        // 1. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, course.getName());
        values.put(COLUMN_TEACHER, course.getTeacher());
        values.put(COLUMN_ROOM, course.getRoom());
        values.put(COLUMN_TIMESLOT, course.getTimeslot());
        values.put(COLUMN_WEEKDAY, course.getWeekday());
        values.put(COLUMN_CAT, course.getCategory());
        // 2. insert
        db.insert(TABLE_COURSES, null, values);

        return course;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_COURSES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build course and add it to list
        Course course;
        if (cursor.moveToFirst()) {
            do {
                course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), Integer.parseInt(cursor.getString(5)), cursor.getInt(6),
                    cursor.getInt(7));
                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Log.d("getAllCourses()", courses.toString());
        return courses;
    }

    public List<Course> getCourses(int timeslot, int weekday) {
        List<Course> courses = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_COURSES + " WHERE " + COLUMN_TIMESLOT + "=" + timeslot + " AND " + COLUMN_WEEKDAY + "=" + weekday + ";";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build course and add it to list
        Course course;
        if (cursor.moveToFirst()) {
            do {
                course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), Integer.parseInt(cursor.getString(5)), cursor.getInt(6),
                    cursor.getInt(7));
                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Log.d("getCourses()", courses.toString());
        return courses;
    }

    public Course updateCourse(Course course) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues
        ContentValues values = new ContentValues();
        values.put(COLUMN_STAR, course.getStarred());

        // 3. updating row
        int i = db.update(TABLE_COURSES, //table
            values, // column/value
            COLUMN_ID + " = ?", // selections
            new String[] { String.valueOf(course.getId()) }); //selection args


        // 4. requery
        Cursor cursor = db.query(TABLE_COURSES, // a. table
            COLUMNS, // b. column names
            " id = ?", // c. selections
            new String[] { String.valueOf(course.getId()) }, // d. selections args
            null, // e. group by
            null, // f. having
            null, // g. order by
            null); // h. limit
        if (cursor != null) {
            cursor.moveToFirst();
            course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), Integer.parseInt(cursor.getString(5)), cursor.getInt(6),
                cursor.getInt(7));

            // 4. close
            cursor.close();
            db.close();

            Log.d("updateCourse()", course.toString());

            return course;
        } else {
            db.close();
            return null;
        }
    }
}
