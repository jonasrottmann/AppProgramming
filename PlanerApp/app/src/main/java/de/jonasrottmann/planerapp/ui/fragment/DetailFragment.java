package de.jonasrottmann.planerapp.ui.fragment;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    @BindView(R.id.teacher_row)
    RelativeLayout teacherRow;
    @BindView(R.id.teacher_text)
    TextView teacherText;
    @BindView(R.id.teacher_icon)
    ImageView teacherIcon;

    @BindView(R.id.room_row)
    RelativeLayout roomRow;
    @BindView(R.id.room_text)
    TextView roomText;
    @BindView(R.id.room_icon)
    ImageView roomIcon;

    @BindView(R.id.time_row)
    RelativeLayout timeRow;
    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.time_icon)
    ImageView timeIcon;

    @BindView(R.id.cat_row)
    RelativeLayout catRow;
    @BindView(R.id.cat_text)
    TextView catText;
    @BindView(R.id.cat_icon)
    ImageView catIcon;


    @NonNull
    public static DetailFragment getInstance(@Nullable Course course) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_COURSE, course);
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
            backdrop.setImageDrawable(ContextCompat.getDrawable(getActivity(), course.getIcon()));
            if (course.getTeacher() != null) {
                teacherText.setText(course.getTeacher());
            } else {
                teacherRow.setVisibility(View.GONE);
            }
            if (course.getRoom() != null) {
                roomText.setText(course.getRoom());
            } else {
                roomRow.setVisibility(View.GONE);
            }
            timeText.setText(Course.TimeSlot.getTimeSlotForId(course.getTimeslot()));
            catText.setText(Course.Category.getCategoryStringForId(course.getCategory()));

            // Color
            ColorFilter colorFilter = new PorterDuffColorFilter(ContextCompat.getColor(getActivity(), Course.Category.getCategoryColorForId(course.getCategory())), PorterDuff.Mode.SRC_ATOP);
            teacherIcon.setColorFilter(colorFilter);
            roomIcon.setColorFilter(colorFilter);
            timeIcon.setColorFilter(colorFilter);
            catIcon.setColorFilter(colorFilter);
            collapsing.setBackgroundColor(ContextCompat.getColor(getActivity(), Course.Category.getCategoryColorForId(course.getCategory())));
            collapsing.setContentScrimColor(ContextCompat.getColor(getActivity(), Course.Category.getCategoryColorForId(course.getCategory())));
            collapsing.setStatusBarScrimColor(ContextCompat.getColor(getActivity(), Course.Category.getCategoryColorForId(course.getCategory())));

            // Setup toolbar
            collapsing.setTitle(course.getName());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
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

    private static class Row {
        public String text;
        public String label;
        public Drawable drawable;

        public Row(@NonNull String text, @NonNull String label, @NonNull Drawable drawable) {
            this.text = text;
            this.label = label;
            this.drawable = drawable;
        }
    }
}
