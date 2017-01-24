package de.jonasrottmann.planerapp.ui;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.Course;
import de.jonasrottmann.planerapp.ui.fragments.OverviewFragment;
import de.jonasrottmann.planerapp.ui.views.HorizontalSpaceItemDecoration;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class OverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_COURSES = 0;
    private static final int VIEWTYPE_TEXT = 1;

    private final OverviewFragment.Contract contract;
    private final Context context;

    private SparseArray<Parcelable> scrollStatePositionsMap = new SparseArray<>();

    public OverviewAdapter(Context context, OverviewFragment.Contract contract) {
        this.contract = contract;
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
                final ViewHolderCoursesRow viewHolder = new ViewHolderCoursesRow(itemView, scrollStatePositionsMap);
                viewHolder.recycler.addItemDecoration(new HorizontalSpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.recycler_horizontal_margin)));
                viewHolder.recycler.hasFixedSize();
                viewHolder.recycler.setNestedScrollingEnabled(false);
                viewHolder.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                viewHolder.recycler.setAdapter(new OverviewRowAdapter(context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.recycler.getChildAdapterPosition(v);
                        OverviewRowAdapter adapter = (OverviewRowAdapter) viewHolder.recycler.getAdapter();
                        Course course = adapter.getData().get(pos);
                        contract.onCourseClicked(course);
                    }
                }));
                return viewHolder;
            case VIEWTYPE_TEXT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
                return new ViewHolderText(itemView);
            default:
                throw new IllegalArgumentException("Unknown ViewType");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((Row) holder).getTimeView().setText(Course.TimeSlot.getTimeSlotForId(holder.getAdapterPosition()));
        switch (holder.getItemViewType()) {
            case VIEWTYPE_COURSES:
                ((OverviewRowAdapter) ((ViewHolderCoursesRow) holder).recycler.getAdapter()).setData(contract.getCourses(holder.getAdapterPosition(), 0));
                // Restore scroll position
                if (scrollStatePositionsMap.get(holder.getAdapterPosition()) != null) {
                    ((ViewHolderCoursesRow) holder).recycler.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ((ViewHolderCoursesRow) holder).recycler.getViewTreeObserver().removeOnPreDrawListener(this);
                            ((ViewHolderCoursesRow) holder).recycler.getLayoutManager().onRestoreInstanceState(scrollStatePositionsMap.get(holder.getAdapterPosition()));
                            return false;
                        }
                    });
                }
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

    static class ViewHolderCoursesRow extends RecyclerView.ViewHolder implements Row {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.recycler)
        RecyclerView recycler;

        ViewHolderCoursesRow(View itemView, final SparseArray<Parcelable> scrollStatePositionsMap) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            // Used to restore scroll position
            recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        scrollStatePositionsMap.put(getAdapterPosition(), recyclerView.getLayoutManager().onSaveInstanceState());
                    }
                }
            });
        }

        @Override
        public TextView getTimeView() {
            return this.time;
        }
    }

    static class ViewHolderText extends RecyclerView.ViewHolder implements Row {
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

    interface Row {
        TextView getTimeView();
    }
}
