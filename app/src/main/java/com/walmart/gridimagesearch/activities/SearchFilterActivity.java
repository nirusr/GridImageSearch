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
import android.widget.Toast;

import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.models.SearchFilterParcelable;

import java.lang.reflect.Array;

public class SearchFilterActivity extends AppCompatActivity {
    public Spinner spImageSize;
    public Spinner spColorFilter;
    public Spinner spImageType;
    public EditText etSiteFilter;
    public String imageSizeFilter;
    public String imageColorFilter;
    public String imageTypeFilter;
    public String imageSiteFilter;
    public SearchFilterParcelable searchFilterParcelable;
    public ArrayAdapter<String> aImageSize;
    public ArrayAdapter<String> aColor;
    public ArrayAdapter<String> aType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        getReferences();
        setSearchValues();




    }

    public void setSearchValues() {

        //Set the search fields with previous values
        aImageSize = (ArrayAdapter<String>)spImageSize.getAdapter();
        aColor = (ArrayAdapter<String>)spColorFilter.getAdapter();
        ArrayAdapter<String> aType = (ArrayAdapter<String>) spImageType.getAdapter();

        searchFilterParcelable = getIntent().getParcelableExtra(SearchActivity.SEARCH_FILTER);
        if ( searchFilterParcelable != null) {

            int pSize = aImageSize.getPosition(searchFilterParcelable.imageSizeFilter);
            spImageSize.setSelection(pSize);


            int pColor = aColor.getPosition(searchFilterParcelable.imageColorFilter);
            spColorFilter.setSelection(pColor);


            int pType = aType.getPosition(searchFilterParcelable.imageTypeFilter);
            spImageType.setSelection(pType);

            etSiteFilter.setText(searchFilterParcelable.imageSiteFilter);


        }



    }

    public void getReferences() {
        //Get reference on spinner
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);


        ArrayAdapter<CharSequence> aCustSize = ArrayAdapter.createFromResource(this, R.array.imageSize_arrays, android.R.layout.simple_spinner_item);
        aCustSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(aCustSize);


        ArrayAdapter<CharSequence> aCustColor = ArrayAdapter.createFromResource(this, R.array.imageColor_arrays, android.R.layout.simple_spinner_item);
        aCustColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(aCustColor);


        ArrayAdapter<CharSequence> aCustType = ArrayAdapter.createFromResource(this, R.array.imageType_arrays, android.R.layout.simple_spinner_item);
        aCustType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(aCustType);

    }

    public void btnSaveClicked(View v) {

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

    public void btnResetClicked(View v) {
        SearchFilterParcelable searchFilterParcelable = new SearchFilterParcelable();

        spImageSize.setSelection(0);
        spColorFilter.setSelection(0);
        spImageType.setSelection(0);
        etSiteFilter.setText("");
        Toast.makeText(this, "Search reset to empty", Toast.LENGTH_SHORT).show();

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
