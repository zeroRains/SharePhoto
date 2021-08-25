package com.example.sharephoto.Publication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.R;

import java.util.List;

public class PublishPhotoAdapter extends RecyclerView.Adapter<PublishPhotoAdapter.ViewHolder> {
    private Context context;
    private List<PublishPhoto> photos;
    private int resourceId;

    public PublishPhotoAdapter(Context context, List<PublishPhoto> photos, int resourceId) {
        this.context = context;
        this.photos = photos;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublishPhotoAdapter.ViewHolder holder, int position) {
        PublishPhoto item = photos.get(position);
        holder.publish_photo_item.setImageResource(item.getId());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView publish_photo_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            publish_photo_item = itemView.findViewById(R.id.publish_photo_item);
        }
    }
}
