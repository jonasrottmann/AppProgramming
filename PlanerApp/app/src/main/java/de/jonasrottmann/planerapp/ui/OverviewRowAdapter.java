package de.jonasrottmann.planerapp.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewRowAdapter extends RecyclerView.Adapter<OverviewRowAdapter.ViewHolder> {

    private final Context context;

    public OverviewRowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
            from(parent.getContext()).
            inflate(R.layout.item_course, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText("Title");
        holder.teacher.setText("Teacher");
        holder.room.setText("Room");
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder));
    }

    @Override
    public int getItemCount() {
        return 9;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.teacher)
        TextView teacher;
        @BindView(R.id.room)
        TextView room;
        @BindView(R.id.icon)
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
