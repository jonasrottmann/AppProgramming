package de.jonasrottmann.planerapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.ui.fragments.DetailFragment;
import de.jonasrottmann.planerapp.ui.fragments.OverviewFragment;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

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

        isInTwoPaneLayout = (container2 != null);

        getSupportFragmentManager().beginTransaction().replace(R.id.container1, OverviewFragment.getInstance()).commit();
        if (isInTwoPaneLayout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, DetailFragment.getInstance()).commit();
        }
    }
}