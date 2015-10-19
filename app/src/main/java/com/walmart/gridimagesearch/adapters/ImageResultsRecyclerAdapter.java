package com.walmart.gridimagesearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walmart.gridimagesearch.R;
import com.walmart.gridimagesearch.models.ImageResult;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sgovind on 10/19/15.
 */
public class ImageResultsRecyclerAdapter extends RecyclerView.Adapter<ImageResultsRecyclerAdapter.ViewHolder> {

    /*ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
    TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);*/
    private List<ImageResult> mImageResults;
    public Context context;
    public ImageResultsRecyclerAdapter(List<ImageResult> imageResults) {
        this.mImageResults = imageResults;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvTitle;

        public ViewHolder(View itemView) {

            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }


    @Override
    public ImageResultsRecyclerAdapter.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View imageResultView = inflater.inflate(R.layout.item_image_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(imageResultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageResultsRecyclerAdapter.ViewHolder holder, int position) {
        ImageResult imageResult = mImageResults.get(position);

        TextView tvTitle = holder.tvTitle;
        tvTitle.setText(imageResult.getTitle());

        ImageView ivImage = holder.ivImage;
        Picasso.with(context).load(imageResult.getTbUrl()).fit().centerCrop().into(ivImage);


    }

    @Override
    public int getItemCount() {
        return mImageResults.size();
    }
}
