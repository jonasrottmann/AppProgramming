package de.jonasrottmann.planerapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
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
import de.jonasrottmann.planerapp.data.Course;
import java.util.List;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewRowAdapter extends RecyclerView.Adapter<OverviewRowAdapter.ViewHolder> {

    private final Context context;
    private List<Course> courses;
    private View.OnClickListener listener;

    public OverviewRowAdapter(@NonNull Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.listener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
            from(parent.getContext()).
            inflate(R.layout.item_course, parent, false);
        itemView.setOnClickListener(this.listener);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(courses.get(position).getName());
        holder.teacher.setText(courses.get(position).getTeacher());
        holder.room.setText(courses.get(position).getRoom());
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder));
    }

    public void setData(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public List<Course> getData() {
        return courses;
    }

    @Override
    public int getItemCount() {
        return this.courses == null ? 0 : this.courses.size();
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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
