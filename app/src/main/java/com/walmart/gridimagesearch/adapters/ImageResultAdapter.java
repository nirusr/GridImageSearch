package com.walmart.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.models.ImageResult;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sgovind on 10/15/15.
 */
public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResult imageInfo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));

        ivImage.setImageResource(0); //clear previous image
        Picasso.with(getContext()).load(imageInfo.getTbUrl()).fit().centerCrop().into(ivImage);

        return convertView;


    }
}
