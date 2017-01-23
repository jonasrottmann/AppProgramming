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

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";
    private static final String TABLE_COURSES = "course";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TEACHER = "teacher";
    private static final String COLUMN_ROOM = "room";
    private static final String COLUMN_TIMESLOT = "timeslot";
    private static final String COLUMN_WEEKDAY = "weekday";
    private static final String COLUMN_CAT = "category";
    private static final String COLUMN_STAR = "starred";
    private static final String[] COLUMNS = { COLUMN_ID, COLUMN_NAME, COLUMN_TEACHER, COLUMN_ROOM, COLUMN_TIMESLOT, COLUMN_WEEKDAY, COLUMN_CAT, COLUMN_STAR };

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
