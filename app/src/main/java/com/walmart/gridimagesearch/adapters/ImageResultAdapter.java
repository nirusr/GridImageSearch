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

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
    }

    public ImageResultAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_image_result, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        Picasso.with(getContext()).load(imageInfo.getTbUrl()).fit().centerCrop().into(viewHolder.ivImage);


        return convertView;


    }
}
