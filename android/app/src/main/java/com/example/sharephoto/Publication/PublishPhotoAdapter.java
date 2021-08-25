package com.example.sharephoto.Publication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.R;

import java.util.ArrayList;
import java.util.List;

public class PublishPhotoAdapter extends RecyclerView.Adapter<PublishPhotoAdapter.ViewHolder> {
    //    private static List<PublishPhoto> photos;
    private Context context;
    private List<PublishPhoto> photos = new ArrayList<>();
    private int resourceId;

    public PublishPhotoAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }

    public void setPhotos(List<PublishPhoto> photos) {
        this.photos = photos;
        this.notifyDataSetChanged();
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
        holder.publish_photo_item.setImageBitmap(displayImage(item.getPhoto_uri()));
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

    private Bitmap displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            return bitmap;
        } else {
            Toast.makeText(context, "读取失败", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
