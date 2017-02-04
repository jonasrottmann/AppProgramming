package de.jonasrottmann.planerapp.data.model;

import android.util.SparseArray;
import android.util.SparseIntArray;
import de.jonasrottmann.planerapp.R;

/**
 * Created by Jonas Rottmann on 04.02.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Category {

    private static final SparseArray<String> categoryStrings = new SparseArray<>(8);
    private static final SparseIntArray categoryColor = new SparseIntArray(8);

    static {
        categoryStrings.append(0, "Denken");
        categoryStrings.append(1, "Handwerk/Kunst");
        categoryStrings.append(2, "Musik");
        categoryStrings.append(3, "Sozialwissenschaften");
        categoryStrings.append(4, "Sport/Tanz");
        categoryStrings.append(5, "Deutsch und Fremdsprachen");
        categoryStrings.append(6, "Wissenschaften");
        categoryStrings.append(7, "Sonstiges");

        categoryColor.append(0, R.color.cat0);
        categoryColor.append(1, R.color.cat1);
        categoryColor.append(2, R.color.cat2);
        categoryColor.append(3, R.color.cat3);
        categoryColor.append(4, R.color.cat4);
        categoryColor.append(5, R.color.cat5);
        categoryColor.append(6, R.color.cat6);
        categoryColor.append(7, R.color.cat7);
    }

    private Category() {
    }

    public static String getCategoryStringForId(int id) {
        return categoryStrings.get(id);
    }

    public static int getCategoryColorForId(int id) {
        return categoryColor.get(id);
    }
}
