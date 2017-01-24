package de.jonasrottmann.planerapp.data;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Jonas Rottmann on 24.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class CourseContentProvider extends android.content.ContentProvider {

    public static final String AUTHORITY = "de.jonasrottmann.planerapp";
    public static final int COURSES = 100;
    public static final int COURSE_ID = 110;
    public static final String COURSES_BASE_PATH = "courses";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + COURSES_BASE_PATH);

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, COURSES_BASE_PATH, COURSES);
        URI_MATCHER.addURI(AUTHORITY, COURSES_BASE_PATH + "/#", COURSE_ID);
    }

    private CourseDatabaseHelper database;

    @Override
    public boolean onCreate() {
        database = new CourseDatabaseHelper(getContext());
        return true;
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
                cursor = database.getAllCourses(projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
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
            int id = Integer.parseInt(uri.getPathSegments().get(1));
            int count = database.updateCourse(id, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        } else {
            throw new IllegalArgumentException("Not allowed to update multiple courses at once.");
        }
    }
}
