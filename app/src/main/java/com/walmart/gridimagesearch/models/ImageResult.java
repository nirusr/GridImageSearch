package com.walmart.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sgovind on 10/15/15.
 */
public class ImageResult {

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
}

