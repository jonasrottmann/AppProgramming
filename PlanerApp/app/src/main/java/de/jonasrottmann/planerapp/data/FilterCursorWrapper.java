package de.jonasrottmann.planerapp.data;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Jonas Rottmann on 24.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class FilterCursorWrapper extends CursorWrapper {
    private final String filter;
    private final int column;
    private final int[] index;
    private int count = 0;
    private int pos = 0;

    public FilterCursorWrapper(Cursor cursor, String filter, int column) {
        super(cursor);
        this.filter = filter.toLowerCase();
        this.column = column;
        if (!this.filter.isEmpty()) {
            this.count = super.getCount();
            this.index = new int[this.count];
            for (int i = 0; i < this.count; i++) {
                super.moveToPosition(i);
                if (this.getString(this.column).toLowerCase().contains(this.filter)) {
                    this.index[this.pos++] = i;
                }
            }
            this.count = this.pos;
            this.pos = 0;
            super.moveToFirst();
        } else {
            this.count = super.getCount();
            this.index = new int[this.count];
            for (int i = 0; i < this.count; i++) {
                this.index[i] = i;
            }
        }
    }

    @Override
    public boolean move(int offset) {
        return this.moveToPosition(this.pos + offset);
    }

    @Override
    public boolean moveToNext() {
        return this.moveToPosition(this.pos + 1);
    }

    @Override
    public boolean moveToPrevious() {
        return this.moveToPosition(this.pos - 1);
    }

    @Override
    public boolean moveToFirst() {
        return this.moveToPosition(0);
    }

    @Override
    public boolean moveToLast() {
        return this.moveToPosition(this.count - 1);
    }

    @Override
    public boolean moveToPosition(int position) {
        if (position >= this.count || position < 0) {
            return false;
        }
        this.pos = position;
        return super.moveToPosition(this.index[position]);
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public int getPosition() {
        return this.pos;
    }
}