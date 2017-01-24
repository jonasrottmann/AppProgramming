package de.jonasrottmann.planerapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.Course;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class DetailFragment extends ContractFragment<DetailFragment.Contract> {

    private static final String EXTRA_COURSE = "EXTRA_COURSE";
    private static final String EXTRA_TWO_PANE = "EXTRA_TWO_PANE";
    private boolean isTwoPain;
    @Nullable
    private Course course;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;

    @NonNull
    public static DetailFragment getInstance(@Nullable Course course, boolean isTwoPane) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_COURSE, course);
        bundle.putBoolean(EXTRA_TWO_PANE, isTwoPane);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void starClicked() {
        if (course != null) {
            this.course = getContract().toggleStarCourseClicked(course);
            fab.setActivated(course.getStarred());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.course = getArguments().getParcelable(EXTRA_COURSE);
        this.isTwoPain = getArguments().getBoolean(EXTRA_TWO_PANE);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        if (course != null) {
            // Setup views
            fab.setActivated(course.getStarred());
            // TODO

            // Setup toolbar
            collapsing.setTitle(course.getName());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (!this.isTwoPain) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface Contract {
        Course toggleStarCourseClicked(@NonNull Course course);
    }
}
