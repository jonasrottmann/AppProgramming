package de.jonasrottmann.planerapp.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.ui.adapter.OverviewAdapter;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewFragment extends ContractFragment<OverviewFragment.Contract> {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private Cursor cursor = null;

    @NonNull
    public static OverviewFragment getInstance() {
        return new OverviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, view);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Setup Views
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(new OverviewAdapter(getActivity(), getContract(), getContract().getCursor()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.cursor = getContract().getCursor();
        ((OverviewAdapter) recycler.getAdapter()).swapCursor(this.cursor);
    }

    public void updateCursor(Cursor cursor) {
        this.cursor = cursor;
        ((OverviewAdapter) recycler.getAdapter()).swapCursor(this.cursor);
    }

    public interface Contract {
        void onCourseClicked(int id);

        Cursor getCursor();
    }
}
