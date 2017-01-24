package de.jonasrottmann.planerapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.Course;
import de.jonasrottmann.planerapp.data.SQLiteHelper;
import de.jonasrottmann.planerapp.ui.fragments.DetailFragment;
import de.jonasrottmann.planerapp.ui.fragments.OverviewFragment;
import java.util.List;

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
    private SQLiteHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Setup database
        database = new SQLiteHelper(this);

        // Setup fragments
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, OverviewFragment.getInstance()).commit();
        if (isInTwoPaneLayout = (container2 != null)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance(null, true)).commit();
        }
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    @Override
    public void onCourseClicked(@NonNull Course course) {
        if (isInTwoPaneLayout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance(course, true)).commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container1, DetailFragment.getInstance(course, false)).commit();
        }
    }

    @Override
    public List<Course> getCourses(int timeslot, int weekday) {
        return database.getCourses(timeslot, weekday);
    }

    @Override
    public Course toggleStarCourseClicked(@NonNull Course course) {
        // TODO (un)register notification
        course.setStarred(!course.getStarred());
        return database.updateCourse(course);
    }
}