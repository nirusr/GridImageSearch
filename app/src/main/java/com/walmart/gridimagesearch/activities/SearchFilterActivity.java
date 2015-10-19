package com.walmart.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
    }

    public void btnSave(View v) {

        /*private String imageSizeFilter;
        private String imageColorFilter;
        private String imageTypeFilter;
        private String imageSiteFilter;
        */
        //Get reference on spinner
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

        //Get the selected values from the spinner
        imageSizeFilter = spImageSize.getSelectedItem().toString();
        imageColorFilter = spColorFilter.getSelectedItem().toString();
        imageTypeFilter = spImageType.getSelectedItem().toString();
        imageSiteFilter = etSiteFilter.getText().toString();

        SearchFilterParcelable searchFilterParcelable = new SearchFilterParcelable();
        searchFilterParcelable.imageColorFilter = imageColorFilter;
        searchFilterParcelable.imageSiteFilter = imageSiteFilter;
        searchFilterParcelable.imageSizeFilter = imageSizeFilter;
        searchFilterParcelable.imageTypeFilter = imageTypeFilter;

        Intent intent = new Intent();
        intent.putExtra(SearchActivity.SEARCH_FILTER,searchFilterParcelable );
        setResult(RESULT_OK, intent);
        finish();

        //return to search activity

    }
}
