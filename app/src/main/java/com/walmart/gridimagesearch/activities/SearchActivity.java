package com.walmart.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.walmart.gridimagesearch.adapters.ImageResultAdapter;
import com.walmart.gridimagesearch.models.ImageResult;
import com.walmart.gridimagesearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResults ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getViewReference();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults );


        //Click Listener
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the clicked item
                ImageResult clickedImage = aImageResults.getItem(position);
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                intent.putExtra(ImageDisplayActivity.IMAGE_URL, clickedImage.getUrl());
                startActivity(intent);
            }
        });
    }

    //Get reference of the views
    public void getViewReference() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
    }

    //Fired when sesarch button is clicked
    //View v is the "button view"
    public void onImageSearch (View v) {
        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Cat&rsz=8
        String query = etQuery.getText().toString();
        if ( query.length() == 0 ) {
            query="Android";
        }

        String Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";

        if (isNetworkAvailable()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray imageResultsJson = null;
                    try {
                        imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                        //// TODO: 10/15/15  pagination
                        imageResults.clear();
                        //imageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                        aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                        Log.i("data:", imageResults.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                     super.onFailure(statusCode, headers, responseString, throwable);
                }
            });

        } else {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_SHORT).show();
        }

   }
    //Check Network Connectivity
    public Boolean isNetworkAvailable(){
        Boolean networkConn = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            return networkConn.TRUE;
        } else {
            return networkConn.FALSE;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putStringArrayList("imageResults", imageResults);
        super.onSaveInstanceState(outState);
    }
}
