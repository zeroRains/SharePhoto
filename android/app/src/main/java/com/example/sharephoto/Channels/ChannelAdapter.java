package com.example.sharephoto.Channels;

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

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private Context context;
    private List<Channel> channels;
    private int resourceId;

    public ChannelAdapter(Context context, List<Channel> channels, int resourceId) {
        this.context = context;
        this.channels = channels;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdapter.ViewHolder holder, int position) {
        Channel channel = channels.get(position);
        holder.channel_icon.setImageResource(channel.getPhoto());
        holder.channel_title.setText(channel.getTitle());
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView channel_icon;
        TextView channel_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            channel_icon = itemView.findViewById(R.id.channel_icon);
            channel_title = itemView.findViewById(R.id.channel_text);
        }
    }
}
