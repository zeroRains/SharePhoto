package com.example.sharephoto.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.Home.HomePhoto;
import com.example.sharephoto.Home.HomePhotoAdapter;
import com.example.sharephoto.R;

import java.util.List;
import java.util.PrimitiveIterator;

class ProfileContentAdapter extends RecyclerView.Adapter<ProfileContentAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private int resourceId;
    private List<Profile> contentList;
    private Profile item;

    private View view;
    private ViewHolder vh;

    private ProfileContentAdapter.OnItemClickListener onItemClickListener;

    public ProfileContentAdapter(Context context, List<Profile> contentList, int resourceId) {
        this.context = context;
        this.contentList = contentList;
        this.resourceId = resourceId;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ProfileContentAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item = contentList.get(position);
        holder.profilePhoto.setTag(position);
        holder.profilePhoto.setImageResource(item.getImageid());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return contentList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profile_photo);
        }
    }
}
