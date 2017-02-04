package de.jonasrottmann.planerapp.data.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.jonasrottmann.planerapp.data.model.Course;

/**
 * Created by Jonas Rottmann on 24.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class CourseContentProvider extends ContentProvider {
    // URI-Matcher codes
    public static final int COURSES = 100;
    public static final int COURSE_ID = 110;
    // MIME types
    public static final String COURSE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + DatabaseContract.AUTHORITY + ".courses";
    public static final String COURSE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + DatabaseContract.AUTHORITY + ".courses";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Course.TABLE_NAME, COURSES);
        URI_MATCHER.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Course.TABLE_NAME + "/#", COURSE_ID);
    }

    private CourseDatabaseHelper database;

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            database = new CourseDatabaseHelper(getContext());
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case COURSE_ID:
                cursor = database.getCourse(Integer.parseInt(uri.getPathSegments().get(1)), projection, selection, selectionArgs, sortOrder);
                break;
            case COURSES:
                sortOrder = DatabaseContract.Course.Columns.COLUMN_STAR + " DESC"; // return starred coursed first
                cursor = database.getAllCourses(projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        // Notify the content resolver
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case COURSE_ID:
                return COURSE_CONTENT_ITEM_TYPE;
            case COURSES:
                return COURSE_CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new IllegalArgumentException("Not allowed to insert courses.");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new IllegalArgumentException("Not allowed to delete courses.");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == COURSE_ID) {
            Course course;
            Cursor courseCursor = database.getCourse(Integer.parseInt(uri.getPathSegments().get(1)), DatabaseContract.Course.ALL_COLUMNS, null, null, null);
            if (courseCursor != null && courseCursor.moveToFirst()) {
                course = new Course(courseCursor);
            } else {
                throw new IllegalArgumentException("No course with this id.");
            }
            if (values.getAsInteger(DatabaseContract.Course.Columns.COLUMN_STAR) == 1) {
                // Course should be starred
                // Get stared course for this timeslot
                Cursor starredCourses = database.getAllCourses(DatabaseContract.Course.ALL_COLUMNS,
                    DatabaseContract.Course.Columns.COLUMN_WEEKDAY + " = ? AND " + DatabaseContract.Course.Columns.COLUMN_TIMESLOT + " = ? AND " + DatabaseContract.Course.Columns.COLUMN_STAR + " = ?",
                    new String[] {
                        String.valueOf(course.getWeekday()), String.valueOf(course.getTimeslot()), String.valueOf(1)
                    }, null);
                if (starredCourses == null || starredCourses.getCount() != 0) {
                    throw new IllegalStateException("Only one starred course per timeslot allowed.");
                } else {
                    // Set star
                    getContext().getContentResolver().notifyChange(uri, null);
                    return database.updateCourse(course.getId(), values);
                }
            } else {
                // Remove star
                getContext().getContentResolver().notifyChange(uri, null);
                return database.updateCourse(course.getId(), values);
            }
        } else {
            throw new IllegalArgumentException("Not allowed to update multiple courses at once.");
        }
    }
}
