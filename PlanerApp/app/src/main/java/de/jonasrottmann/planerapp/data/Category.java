package de.jonasrottmann.planerapp.data;

import android.util.SparseArray;

/**
 * Created by Jonas Rottmann on 19.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class Category {

    private Category() {
    }

    private static SparseArray<String> categories = new SparseArray<>();

    static {
        categories.append(0, "Denken");
        categories.append(1, "Handwerk/Kunst");
        categories.append(2, "Musik");
        categories.append(3, "Sozialwissenschaften");
        categories.append(4, "Sport/Tanz");
        categories.append(5, "Deutsch und Fremdsprachen");
        categories.append(6, "Wissenschaften");
        categories.append(7, "Sonstiges");
    }

    public static String getCategoryForId(int id) {
        return categories.get(id);
    }
}