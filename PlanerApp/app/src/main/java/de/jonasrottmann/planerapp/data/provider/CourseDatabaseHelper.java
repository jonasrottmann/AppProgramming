package de.jonasrottmann.planerapp.data.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;
import de.jonasrottmann.planerapp.data.provider.DatabaseContract.Course.Columns;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
class CourseDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";

    private final Context context;

    /**
     * @param context A valid {@link Context} (used to access assets).
     */
    CourseDatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /*
     * SQLiteOpenHelper
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        AssetManager am = context.getAssets();
        try {
            InputStream inputStream = am.open("seed.sql");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();

            String[] queries = byteArrayOutputStream.toString().split(";\n");
            for (String query : queries) {
                db.execSQL(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Course.TABLE_NAME);
        this.onCreate(db);
    }

    /*
     * Access Methods
     */
    Cursor getAllCourses(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(DatabaseContract.Course.TABLE_NAME);
        return sqliteQueryBuilder.query(getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    Cursor getCourse(int id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(DatabaseContract.Course.TABLE_NAME);
        sqliteQueryBuilder.appendWhere(Columns.COLUMN_ID + " = " + id);
        if (sortOrder == null) {
            sortOrder = Columns.COLUMN_STAR;
        }
        return sqliteQueryBuilder.query(getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    int updateCourse(int id, ContentValues values) {
        return getWritableDatabase().update(DatabaseContract.Course.TABLE_NAME, values, "_id=?", new String[] { String.valueOf(id) });
    }
}
