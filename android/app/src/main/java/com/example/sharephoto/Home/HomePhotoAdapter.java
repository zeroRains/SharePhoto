package com.example.sharephoto.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePhotoAdapter extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<HomePhoto> photos;
    private int resourceId;
    private OnItemClickListener onItemClickListener;

    public void setPhotos(List<HomePhoto> photos) {
        this.photos = photos;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

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
        view.setOnClickListener(this);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomePhotoAdapter.ViewHolder holder, int position) {
        HomePhoto item = photos.get(position);
//        holder.home_photo.setImageResource(item.getId());
        Glide.with(context).load(RequestConfig.URL + item.getThumbSnail()).into(holder.home_photo);
        holder.itemView.setTag(position);
//        holder.img_icon.setImageResource(item.getIconId());
        Glide.with(context).load(RequestConfig.URL + item.getIconId()).into(holder.img_icon);
        holder.img_username.setText(item.getUsername());
        holder.img_star_num.setText(item.getStarNum() + "");
        holder.img_status.setSelected(item.isStar().equals("T"));
        holder.img_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.img_status.isSelected()) {
                    // 收藏点击
                    holder.img_status.setSelected(false);
                    int num = Integer.parseInt(holder.img_star_num.getText().toString());
                    num -= 1;
                    holder.img_star_num.setText("" + num);
                } else {
                    holder.img_status.setSelected(true);
                    int num = Integer.parseInt(holder.img_star_num.getText().toString());
                    num += 1;
                    holder.img_star_num.setText("" + num);
                }
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
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
