package com.walmart.gridimagesearch.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.models.SearchFilterParcelable;

public class SearchFilterActivity extends AppCompatActivity {
    public Spinner spImageSize;
    public Spinner spColorFilter;
    public Spinner spImageType;
    public EditText etSiteFilter;
    public String imageSizeFilter;
    public String imageColorFilter;
    public String imageTypeFilter;
    public String imageSiteFilter;
    SearchFilterParcelable searchFilterParcelable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        getReferences();


        //Set the search fields with previous values
        searchFilterParcelable = getIntent().getParcelableExtra(SearchActivity.SEARCH_FILTER);
        if ( searchFilterParcelable != null) {

            ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>)spImageSize.getAdapter();
            int p = arrayAdapter.getPosition(searchFilterParcelable.imageSizeFilter);
            spImageSize.setSelection(p);
            //TODO OTHER FIELDS

        }

    }
    public void getReferences() {
        //Get reference on spinner
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

    }

    public void btnSave(View v) {

        //Get the selected values from the spinner
        imageSizeFilter = spImageSize.getSelectedItem().toString();
        imageColorFilter = spColorFilter.getSelectedItem().toString();
        imageTypeFilter = spImageType.getSelectedItem().toString();
        imageSiteFilter = etSiteFilter.getText().toString();

        searchFilterParcelable = new SearchFilterParcelable();
        searchFilterParcelable.imageColorFilter = imageColorFilter;
        searchFilterParcelable.imageSiteFilter = imageSiteFilter;
        searchFilterParcelable.imageSizeFilter = imageSizeFilter;
        searchFilterParcelable.imageTypeFilter = imageTypeFilter;

        Intent intent = new Intent();
        intent.putExtra(SearchActivity.SEARCH_FILTER, searchFilterParcelable);
        setResult(RESULT_OK, intent);
        finish();

        //return to search activity

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("iSize", imageSizeFilter);
        outState.putString("iColor", imageColorFilter);
        outState.putString("iType", imageTypeFilter);
        outState.putSerializable("iSite", imageSiteFilter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("Restore:" , "YES");
        super.onRestoreInstanceState(savedInstanceState);
        imageSizeFilter = savedInstanceState.getString("iSize");
        imageColorFilter = savedInstanceState.getString("iColor");
        imageSiteFilter = savedInstanceState.getString("iSite");
        imageTypeFilter = savedInstanceState.getString("iType");

    }
}
