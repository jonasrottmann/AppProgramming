package de.jonasrottmann.planerapp.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.ui.views.HorizontalSpaceItemDecoration;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private final Context context;
    private final HorizontalSpaceItemDecoration decoration;

    public OverviewAdapter(Context context) {
        this.context = context;
        this.decoration = new HorizontalSpaceItemDecoration(10);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
            from(parent.getContext()).
            inflate(R.layout.item_courses_row, parent, false);

        ViewHolder holder = new ViewHolder(itemView);
        holder.recycler.addItemDecoration(decoration);
        holder.recycler.hasFixedSize();
        holder.recycler.setNestedScrollingEnabled(false);
        holder.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler.setAdapter(new OverviewRowAdapter(context));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText("Uhrzeit");
        //TODO: holder.recycler.getAdapter().setData()
    }

    @Override
    public int getItemCount() {
        return 9;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.recycler)
        RecyclerView recycler;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
