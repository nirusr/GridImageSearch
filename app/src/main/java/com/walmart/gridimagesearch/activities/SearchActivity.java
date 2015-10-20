package com.walmart.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.walmart.gridimagesearch.adapters.EndlessScrollListener;
import com.walmart.gridimagesearch.adapters.ImageResultAdapter;
import com.walmart.gridimagesearch.models.ImageResult;
import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.models.SearchFilterParcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {


    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResults ;
    private String query;

    public static final int REQUEST_CODE = 29;
    public static final String SEARCH_FILTER = "SEARCH_FILTER";


    public String imageSizeFilter;
    public String imageColorFilter;
    public String imageTypeFilter;
    public String imageSiteFilter;

    //Search Arguments
    public static final String Qimgcolor = "imgcolor=";
    public static final String Qas_sitesearch = "as_sitesearch=";
    public static final String Qas_filetype = "as_filetype=";
    public static final String Qimgsz = "imgsz=";
    public StringBuffer urlSearchQuery ;
    public String Url;

    public SearchFilterParcelable searchFilterParcelable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getViewReference();
        urlSearchQuery = null;

        if (savedInstanceState == null) {
           imageResults = new ArrayList<ImageResult>();
        } else {
            imageResults = (ArrayList) savedInstanceState.getSerializable("imageResults");
        }
        aImageResults = new ImageResultAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);


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

        //Scroll Listner
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromSite(totalItemsCount);

                return false;
            }
        });

        searchFilterParcelable = null;
    }


    //Get reference of the views
    public void getViewReference() {

        gvResults = (GridView) findViewById(R.id.gvResults);
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
        outState.putSerializable("imageResults", imageResults);
        super.onSaveInstanceState(outState);
    }


    private void customLoadMoreDataFromSite(int totalItemsCount) {

       // String Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + totalItemsCount;

        if ( urlSearchQuery == null ) {
            Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + totalItemsCount;
        }  else {
            Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + totalItemsCount+ urlSearchQuery.toString();
        }


        Log.i("url:", Url);

        if (isNetworkAvailable()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(Url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray imageResultsJson = null;
                    try {
                        imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchImages(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);



    }

    public void fetchImages(String qString) {
        query = qString;
        if ( query.length() == 0 ) {
            query="Android";
        }

        if ( urlSearchQuery == null ) {
            Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        }  else {
            Url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + urlSearchQuery.toString();
        }
        Log.i("Url:", Url);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.actionSearchFilter: {
                Intent intent = new Intent(this, SearchFilterActivity.class);

                //Send the previous search info to show
                if ( searchFilterParcelable != null ) {
                    Log.i("Searchable", "YES");
                    intent.putExtra(SEARCH_FILTER, searchFilterParcelable);
                } else {
                    Log.i("Searchable", "NO");
                }


                startActivityForResult(intent, REQUEST_CODE);
            }
            default:return super.onOptionsItemSelected(item);


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            searchFilterParcelable= (SearchFilterParcelable) data.getParcelableExtra(SEARCH_FILTER);
            //Log.i("Size=>", searchFilterParcelable.imageSizeFilter);

            buildSearchQuery(searchFilterParcelable);

            fetchImages(query);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void buildSearchQuery(SearchFilterParcelable searchFilterParcelable) {
        urlSearchQuery = new StringBuffer();

        if (  searchFilterParcelable.imageSizeFilter.length() != 0) {
            urlSearchQuery.append("&"+ Qimgsz);
            urlSearchQuery.append(searchFilterParcelable.imageSizeFilter);

        }
        if ( searchFilterParcelable.imageTypeFilter.length() != 0) {
            urlSearchQuery.append("&"+Qas_filetype);
            urlSearchQuery.append(searchFilterParcelable.imageTypeFilter);
        }
        if (searchFilterParcelable.imageColorFilter.length() != 0) {
            urlSearchQuery.append("&"+Qimgcolor);
            urlSearchQuery.append(searchFilterParcelable.imageColorFilter);
        }

        if (searchFilterParcelable.imageSiteFilter.length() != 0 ) {
            urlSearchQuery.append("&"+Qas_sitesearch);
            urlSearchQuery.append(searchFilterParcelable.imageSiteFilter);
        }


    }
}
