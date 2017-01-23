package de.jonasrottmann.planerapp;

import de.jonasrottmann.planerapp.data.Course;
import de.jonasrottmann.planerapp.data.SQLiteHelper;

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteHelper helper = new SQLiteHelper(this);
        Course course = helper.getAllCourses().get(0);
        course.setStarred(true);
        course = helper.updateCourse(course);
        course.setStarred(false);
        course = helper.updateCourse(course);
    }
}
