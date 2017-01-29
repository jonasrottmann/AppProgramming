package de.jonasrottmann.planerapp.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.model.Course;
import de.jonasrottmann.planerapp.data.provider.DatabaseContract;
import de.jonasrottmann.planerapp.ui.fragment.DetailFragment;
import de.jonasrottmann.planerapp.ui.fragment.OverviewFragment;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class MainActivity extends AppCompatActivity implements OverviewFragment.Contract {

    private static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";
    // Views
    @BindView(R.id.container1)
    FrameLayout container1;
    @Nullable
    @BindView(R.id.container2)
    FrameLayout container2;
    // Fields
    private boolean isInTwoPaneLayout;

    public static Intent createIntent(@NonNull Context context, @Nullable Integer courseId) {
        Intent intent = new Intent(context, MainActivity.class);
        if (courseId != null) {
            intent.putExtra(EXTRA_COURSE_ID, courseId);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Handle link from notification
        Course course = getCourseFromIntent(getIntent());
        // Setup fragments
        if (isInTwoPaneLayout = container2 != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container1, OverviewFragment.getInstance()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance(course)).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container1, OverviewFragment.getInstance()).commit();
            if (course != null) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, DetailFragment.getInstance(course)).commit();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle link from notification
        Course course = getCourseFromIntent(intent);
        // Setup fragments
        if (isInTwoPaneLayout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance(course)).commit();
        } else {
            if (course != null) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, DetailFragment.getInstance(course)).commit();
            }
        }
    }

    @Nullable
    private Course getCourseFromIntent(Intent intent) {
        Course course = null;
        if (intent.getIntExtra(EXTRA_COURSE_ID, -1) != -1) {
            Cursor cursor =
                getContentResolver().query(ContentUris.withAppendedId(DatabaseContract.Course.CONTENT_URI, intent.getIntExtra(EXTRA_COURSE_ID, -1)), DatabaseContract.Course.COLUMNS, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    course = new Course(cursor);
                    // Dismiss notification
                    NotificationManagerCompat.from(this).cancel(course.getId());
                }
                cursor.close();
            }
        }
        return course;
    }

    @Override
    public void onCourseClicked(int id) {
        Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(DatabaseContract.Course.CONTENT_URI, id), DatabaseContract.Course.COLUMNS, null, null, null);
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
}