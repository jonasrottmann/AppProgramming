package de.jonasrottmann.planerapp.data.model;

import android.util.SparseIntArray;
import de.jonasrottmann.planerapp.R;

/**
 * Created by Jonas Rottmann on 04.02.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Icon {

    private static final SparseIntArray icons = new SparseIntArray();

    static {
        icons.append(0, R.drawable.placeholder);
        icons.append(1, R.drawable.german_workshop);
        icons.append(2, R.drawable.read_write_calc);
    }

    private Icon() {
    }

    public static Integer getIconResId(int id) {
        return icons.get(id);
    }
}
