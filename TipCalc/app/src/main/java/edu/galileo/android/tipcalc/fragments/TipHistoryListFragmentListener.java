package edu.galileo.android.tipcalc.fragments;

import edu.galileo.android.tipcalc.models.TipRecord;

/**
 * Created by sanes on 15/07/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
