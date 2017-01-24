package de.jonasrottmann.planerapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
class OverviewRowAdapter extends CursorRecyclerViewAdapter<OverviewRowAdapter.ViewHolder> {

    private final Context context;
    private final View.OnClickListener listener;

    OverviewRowAdapter(Cursor cursor, @NonNull Context context, View.OnClickListener onClickListener) {
        super(cursor);
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
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, Course.Category.getCategoryColorForId(cursor.getInt(6))));
        holder.title.setText(cursor.getString(1));
        holder.teacher.setText(cursor.getString(2));
        holder.room.setText(cursor.getString(3));
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, cursor.getInt(8)));
        holder.star.setVisibility(cursor.getInt(7) == 0 ? View.GONE : View.VISIBLE);
        ColorFilter colorFilter = new PorterDuffColorFilter(ContextCompat.getColor(context, Course.Category.getCategoryColorForId(cursor.getInt(6))), PorterDuff.Mode.SRC_ATOP);
        holder.star.setColorFilter(colorFilter);
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
        @BindView(R.id.card)
        CardView card;
        @BindView(R.id.star)
        ImageView star;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
