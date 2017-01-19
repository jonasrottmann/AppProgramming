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
import de.jonasrottmann.planerapp.data.TimeSlot;
import de.jonasrottmann.planerapp.ui.views.HorizontalSpaceItemDecoration;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_COURSES = 0;
    private static final int VIEWTYPE_TEXT = 1;

    private final Context context;

    public OverviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 4 ? VIEWTYPE_TEXT : VIEWTYPE_COURSES;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEWTYPE_COURSES:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courses_row, parent, false);
                ViewHolderRecycler holder = new ViewHolderRecycler(itemView);
                holder.recycler.addItemDecoration(new HorizontalSpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.recycler_horizontal_margin)));
                holder.recycler.hasFixedSize();
                holder.recycler.setNestedScrollingEnabled(false);
                holder.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                holder.recycler.setAdapter(new OverviewRowAdapter(context));
                return holder;
            case VIEWTYPE_TEXT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
                return new ViewHolderText(itemView);
            default:
                throw new IllegalArgumentException("Unknown ViewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RowViewHolder) holder).getTimeView().setText(TimeSlot.getTimeSlotForId(position));
        switch (holder.getItemViewType()) {
            case VIEWTYPE_COURSES:
                //TODO: holder.recycler.getAdapter().setData()
                break;
            case VIEWTYPE_TEXT:
                ((ViewHolderText) holder).text.setText("Mittagspause");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ViewHolderRecycler extends RecyclerView.ViewHolder implements RowViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.recycler)
        RecyclerView recycler;

        ViewHolderRecycler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public TextView getTimeView() {
            return this.time;
        }
    }

    static class ViewHolderText extends RecyclerView.ViewHolder implements RowViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.text)
        TextView text;

        ViewHolderText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public TextView getTimeView() {
            return this.time;
        }
    }

    private interface RowViewHolder {
        TextView getTimeView();
    }
}
