package com.walmart.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sgovind on 10/18/15.
 */
public class SearchFilterParcelable implements Parcelable {

    public String imageSizeFilter;
    public String imageColorFilter;
    public String imageTypeFilter;
    public String imageSiteFilter;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSizeFilter);
        dest.writeString(this.imageColorFilter);
        dest.writeString(this.imageTypeFilter);
        dest.writeString(this.imageSiteFilter);
    }

    public SearchFilterParcelable() {
    }

    protected SearchFilterParcelable(Parcel in) {
        this.imageSizeFilter = in.readString();
        this.imageColorFilter = in.readString();
        this.imageTypeFilter = in.readString();
        this.imageSiteFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchFilterParcelable> CREATOR = new Parcelable.Creator<SearchFilterParcelable>() {
        public SearchFilterParcelable createFromParcel(Parcel source) {
            return new SearchFilterParcelable(source);
        }

        public SearchFilterParcelable[] newArray(int size) {
            return new SearchFilterParcelable[size];
        }
    };
}
