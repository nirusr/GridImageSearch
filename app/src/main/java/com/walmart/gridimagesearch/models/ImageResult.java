package com.walmart.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sgovind on 10/15/15.
 */
public class ImageResult implements Parcelable {

    private String url;
    private String tbUrl;
    private String title;
    private String tbHeight;
    private String tbWidth;

    public ImageResult(JSONObject json) {
        try {
            this.url = json.getString("url");
            this.tbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.tbHeight = json.getString("tbHeight");
            this.tbWidth = json.getString("tbWidth");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Factory method to return array of ImageResults

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for ( int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
       return results;
    }


    //Gettter and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTbHeight() {
        return tbHeight;
    }

    public void setTbHeight(String tbHeight) {
        this.tbHeight = tbHeight;
    }

    public String getTbWidth() {
        return tbWidth;
    }

    public void setTbWidth(String tbWidth) {
        this.tbWidth = tbWidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.tbUrl);
        dest.writeString(this.title);
        dest.writeString(this.tbHeight);
        dest.writeString(this.tbWidth);
    }

    protected ImageResult(Parcel in) {
        this.url = in.readString();
        this.tbUrl = in.readString();
        this.title = in.readString();
        this.tbHeight = in.readString();
        this.tbWidth = in.readString();
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}

