package com.walmart.gridimagesearch.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.walmart.gridimagesearch.R;


public class ImageDisplayActivity extends AppCompatActivity {
    public static final String IMAGE_URL = "IMAGE_URL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        //Get the data from intent
        String imageUrl = getIntent().getStringExtra(IMAGE_URL);
        //Get ImageView reference
        ImageView ivFullImage = (ImageView) findViewById(R.id.ivFullImage);

        Picasso.with(this).load(imageUrl).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(ivFullImage);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
