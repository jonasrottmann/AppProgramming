package de.jonasrottmann.planerapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public static final String EXTRA_COURSE = "EXTRA_COURSE";
    @Nullable
    private Course course;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @NonNull
    public static DetailFragment getInstance(@NonNull Course course) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_COURSE, course);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.fab)
    public void starClicked(View view) {
        if (course != null) {
            this.course = getContract().toggleStarCourseClicked(course);
            fab.setActivated(course.getStarred());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.course = getArguments().getParcelable(EXTRA_COURSE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (course != null) {
            fab.setActivated(course.getStarred());
        }
        return view;
    }

    public interface Contract {
        Course toggleStarCourseClicked(@NonNull Course course);
    }
}
