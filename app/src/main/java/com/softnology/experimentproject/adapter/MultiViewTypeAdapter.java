package com.softnology.experimentproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.softnology.experimentproject.R;
import com.softnology.experimentproject.model.MultiViewModel;

import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {
    private ArrayList<MultiViewModel> dataSet;
    Context mContext;
    int total_types;

    public MultiViewTypeAdapter(ArrayList<MultiViewModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return MultiViewModel.TYPE_BANNER;
            case 1:
                return MultiViewModel.TYPE_IMAGE_WITH_TEXT;

        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MultiViewModel.TYPE_BANNER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image, parent, false);
                final ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams sglp =
                            (StaggeredGridLayoutManager.LayoutParams) lp;
                    sglp.setFullSpan(true);
                }

                return new BannerTypeViewHolder(view);

            case MultiViewModel.TYPE_IMAGE_WITH_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_with_title, parent, false);
                return new ImageTypeViewHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        MultiViewModel object = dataSet.get(position);

        if (object != null) {
            switch (object.type) {
                case MultiViewModel.TYPE_BANNER:
                    ((BannerTypeViewHolder) holder).bannerImg.setBackgroundResource(R.drawable.banner);;

                    break;
                case MultiViewModel.TYPE_IMAGE_WITH_TEXT:
                    ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                    ((ImageTypeViewHolder) holder).image.setImageResource(object.data);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class BannerTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerImg;

        public BannerTypeViewHolder(View itemView) {
            super(itemView);
            this.bannerImg = itemView.findViewById(R.id.imgBanner);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.txtTitle);
            this.image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
