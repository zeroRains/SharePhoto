package com.example.sharephoto.Detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharephoto.Home.HomePhoto;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;

import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.ViewHolder> {
    private Context context;


    private String[] photos;
    private int resourceId;
    ;

    public DetailImageAdapter(Context context, String[] photos, int resouseId) {
        this.context = context;
        this.photos = photos;
        this.resourceId = resouseId;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
        this.notifyDataSetChanged();
    }

    public String[] getPhotos() {
        return photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img = photos[position];
        Glide.with(context).load(RequestConfig.URL + img).into(holder.detail_photo);
    }

    @Override
    public int getItemCount() {
        return photos.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView detail_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail_photo = itemView.findViewById(R.id.detail_photo);
        }
    }
}
