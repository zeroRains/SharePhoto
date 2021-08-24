package com.example.sharephoto.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.Home.HomePhoto;
import com.example.sharephoto.Home.HomePhotoAdapter;

import java.util.List;
import java.util.PrimitiveIterator;

class ProfileContentAdapter extends RecyclerView.Adapter<ProfileContentAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private int resourceId;
    private List<Profile> contentList;

    private View view;
    private ViewHolder vh;

    private ProfileContentAdapter.OnItemClickListener onItemClickListener;

    public ProfileContentAdapter(Context context, List<Profile> contentList, int resourceId) {
        this.context = context;
        this.contentList = contentList;
        this.resourceId = resourceId;
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

    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(ProfileContentAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
