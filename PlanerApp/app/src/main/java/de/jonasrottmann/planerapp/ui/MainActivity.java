package de.jonasrottmann.planerapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import de.jonasrottmann.planerapp.ui.fragments.OverviewFragment;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, OverviewFragment.getInstance()).commit();
    }
}