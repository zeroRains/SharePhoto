package com.example.sharephoto.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePhotoAdapter extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> {
    private Context context;
    private List<HomePhoto> photos;
    private int resourceId;

    public HomePhotoAdapter(Context context, List<HomePhoto> photos, int resourceId) {
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
    public void onBindViewHolder(@NonNull HomePhotoAdapter.ViewHolder holder, int position) {
        HomePhoto item = photos.get(position);
        holder.home_photo.setImageResource(item.getPhoto_id());
        holder.img_tag.setText(item.getTag());
        holder.img_icon.setImageResource(item.getIcon_id());
        holder.img_username.setText(item.getUsername());
        holder.img_star_num.setText(item.getStart_num()+"");
        holder.img_status.setSelected(item.isStart());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView home_photo;
        TextView img_tag;
        CircleImageView img_icon;
        TextView img_username;
        ImageView img_status;
        TextView img_star_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_photo = itemView.findViewById(R.id.home_photo);
            img_tag = itemView.findViewById(R.id.img_tag);
            img_icon = itemView.findViewById(R.id.img_icon);
            img_username = itemView.findViewById(R.id.img_username);
            img_status = itemView.findViewById(R.id.img_status);
            img_star_num = itemView.findViewById(R.id.img_star_num);
        }
    }
}
