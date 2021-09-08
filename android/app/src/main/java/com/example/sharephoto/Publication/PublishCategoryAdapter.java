package com.example.sharephoto.Publication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharephoto.Home.HomePhotoAdapter;
import com.example.sharephoto.R;

import java.util.ArrayList;
import java.util.List;

public class PublishCategoryAdapter extends RecyclerView.Adapter<PublishCategoryAdapter.ViewHolder> {
    private Context context;
    private List<PublishSelection> selections = new ArrayList<>();
    private int resourceId;
    private int now = -1;

    public void setNow(int now) {
        if (this.now != -1) {
            selections.get(this.now).setSelected(false);
        }
        this.now = now;
        selections.get(now).setSelected(true);
        this.notifyDataSetChanged();
    }

    public int getNow() {
        return now;
    }

    public PublishCategoryAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }


    public void setSelections(List<PublishSelection> selections) {
        this.selections = selections;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PublishCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull PublishCategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PublishSelection item = selections.get(position);
        holder.selection.setSelected(item.isSelected());
        if (item.isSelected()) {
            holder.selection.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.selection.setTextColor(context.getResources().getColor(R.color.primary));
        }
        holder.selection.setText(item.getTitle());
        holder.itemView.setTag(position);
        holder.selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNow(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selections.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button selection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selection = itemView.findViewById(R.id.publish_category_selection_item);
        }
    }
}
