package com.example.nationalparks.adaptor;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationalparks.R;
import com.example.nationalparks.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdaptor extends RecyclerView.Adapter<ViewPagerAdaptor.ImageSlider> {
    private List<Images> imagesList;

    public ViewPagerAdaptor(List<Images> imagesListl) {
        this.imagesList = imagesListl;
    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pager_row,parent,false);
        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {

        Picasso.get()
                .load(imagesList.get(position).getUrl())
                .fit()
                .placeholder(android.R.drawable.stat_notify_error)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.view_pager_imageview);
        }
    }
}
