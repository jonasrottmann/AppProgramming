package de.jonasrottmann.planerapp;

import timber.log.Timber;

import static timber.log.Timber.DebugTree;

/**
 * Created by Jonas Rottmann on 23.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }
}
