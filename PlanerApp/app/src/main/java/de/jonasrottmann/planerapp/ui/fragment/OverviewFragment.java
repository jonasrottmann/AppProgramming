package de.jonasrottmann.planerapp.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import de.jonasrottmann.planerapp.data.provider.DatabaseContract;
import de.jonasrottmann.planerapp.ui.adapter.CursorRecyclerViewAdapter;
import de.jonasrottmann.planerapp.ui.adapter.OverviewAdapter;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewFragment extends ContractFragment<OverviewFragment.Contract> implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @Nullable
    private Cursor cursor = null;

    @NonNull
    public static OverviewFragment getInstance() {
        return new OverviewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(R.id.loader, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, view);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Setup views
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(new OverviewAdapter(getActivity(), getContract(), this.cursor));

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), DatabaseContract.Course.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.cursor = data;
        ((CursorRecyclerViewAdapter) this.recycler.getAdapter()).changeCursor(this.cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.cursor = null;
        ((CursorRecyclerViewAdapter) this.recycler.getAdapter()).changeCursor(this.cursor);
    }

    public interface Contract {
        void onCourseClicked(int id);
    }
}
