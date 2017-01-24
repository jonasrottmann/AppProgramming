package de.jonasrottmann.planerapp.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.Course;
import de.jonasrottmann.planerapp.data.CourseContentProvider;
import de.jonasrottmann.planerapp.ui.fragment.DetailFragment;
import de.jonasrottmann.planerapp.ui.fragment.OverviewFragment;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class MainActivity extends AppCompatActivity implements OverviewFragment.Contract, DetailFragment.Contract {
    // Views
    @BindView(R.id.container1)
    FrameLayout container1;
    @Nullable
    @BindView(R.id.container2)
    FrameLayout container2;
    // Fields
    private boolean isInTwoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Setup fragments
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, OverviewFragment.getInstance()).commit();
        if (isInTwoPaneLayout = (container2 != null)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance(null)).commit();
        }
    }

    @Override
    public void onCourseClicked(int id) {
        Cursor cursor = getContentResolver().query(CourseContentProvider.CONTENT_URI, Course.COLUMNS, Course.COLUMN_ID + "= ?", new String[] { String.valueOf(id) }, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Course course = new Course(cursor); // Build data object from cursor
                Fragment fragment = DetailFragment.getInstance(course);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!isInTwoPaneLayout) {
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.container1, fragment);
                } else {
                    transaction.replace(R.id.container2, fragment);
                }
                transaction.commit();
            }
            cursor.close();
        }
    }

    @Override
    public Course toggleStarCourseClicked(@NonNull Course course) {
        // Update database
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_STAR, course.getStarred() ? 0 : 1);
        getContentResolver().update(ContentUris.withAppendedId(CourseContentProvider.CONTENT_URI, course.getId()), values, null, null);

        // Update passed course...
        course.setStarred(!course.getStarred());

        // TODO set notifications...
        //...

        return course;
    }
}