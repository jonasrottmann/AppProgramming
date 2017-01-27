package de.jonasrottmann.planerapp.ui.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import de.jonasrottmann.planerapp.data.CourseContentProvider;
import timber.log.Timber;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class DetailFragment extends Fragment {

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
            if (toggleStarCourseClicked()) {
                requeryCourse();
                fab.setActivated(course.getStarred());
            }
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

    public boolean toggleStarCourseClicked() {
        String selection = Course.COLUMN_WEEKDAY + " = ? AND " + Course.COLUMN_TIMESLOT + " = ? AND " + Course.COLUMN_STAR + " = ? AND " + Course.COLUMN_ID + " != ?";
        Cursor cursor = getActivity().getContentResolver().query(CourseContentProvider.CONTENT_URI, Course.COLUMNS, selection, new String[] {
            String.valueOf(course.getWeekday()), String.valueOf(course.getTimeslot()), String.valueOf(1), String.valueOf(course.getId())
        }, null, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            final Course collisionCourse = new Course(cursor);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(String.format("Dieser Slot ist schon von \"%s\" belegt. Möchsten Sie wechseln?", collisionCourse.getName()));
            builder.setTitle("Achtung");
            builder.setPositiveButton("Wechseln", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (toggleStar(collisionCourse)) {
                        toggleStar(course);
                    }
                    dialog.dismiss();
                    requeryCourse();
                    fab.setActivated(course.getStarred());
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            cursor.close();
            return false;
        } else {
            // Just do it.
            return toggleStar(course);
        }
    }

    private boolean toggleStar(@NonNull Course course) {
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_STAR, course.getStarred() ? 0 : 1);
        try {
            getActivity().getContentResolver().update(ContentUris.withAppendedId(CourseContentProvider.CONTENT_URI, course.getId()), values, null, null);
            return true;
        } catch (IllegalStateException e) {
            Timber.e(e);
            return false;
        }
    }

    private void requeryCourse() {
        Cursor cursor = getActivity().getContentResolver().query(ContentUris.withAppendedId(CourseContentProvider.CONTENT_URI, course.getId()), Course.COLUMNS, null, null, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            course = new Course(cursor);
        } else {
            course = null;
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
