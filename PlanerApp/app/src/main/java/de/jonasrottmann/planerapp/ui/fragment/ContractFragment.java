package de.jonasrottmann.planerapp.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Jonas Rottmann on 24.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public abstract class ContractFragment<T> extends Fragment {
    private T contract;
    private Context context;

    @Override
    public void onAttach(Context context) {
        try {
            //noinspection unchecked
            contract = (T) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException(context.getClass().getSimpleName() + " does not implement " + getClass().getSimpleName() + "'s contract interface.", e);
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        contract = null;
    }

    public final T getContract() {
        return contract;
    }
}