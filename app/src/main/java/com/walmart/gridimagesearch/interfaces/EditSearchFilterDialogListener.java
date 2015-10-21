package com.walmart.gridimagesearch.interfaces;

import android.os.Parcelable;

import com.walmart.gridimagesearch.models.SearchFilterParcelable;

/**
 * Created by sgovind on 10/20/15.
 */
public interface EditSearchFilterDialogListener {
    public void onEditSearchFilterDialogDone(String tag, boolean cancelled, SearchFilterParcelable searchFilterParacelable);
}
