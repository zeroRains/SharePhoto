package com.example.sharephoto.Message;

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

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Chator> list;
    private Context context;
    private int resourceId;

    public MessageAdapter(Context context, List<Chator> list, int resourceId) {
        this.list = list;
        this.context = context;
        this.resourceId = resourceId;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // todo: process data12
        Chator chator = list.get(position);
        if (chator.isReceived()) {
            holder.leftChatBox.setVisibility(View.VISIBLE);
            holder.rightChatBox.setVisibility(View.GONE);
            holder.leftAvatar.setImageResource(chator.getAvatarId());
            holder.leftText.setText(chator.getText());
        } else {
            holder.leftChatBox.setVisibility(View.GONE);
            holder.rightChatBox.setVisibility(View.VISIBLE);
            holder.rightAvatar.setImageResource(chator.getAvatarId());
            holder.rightText.setText(chator.getText());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftAvatar, rightAvatar;
        TextView leftText, rightText;
        ViewGroup leftChatBox, rightChatBox;

        public ViewHolder(View itemView) {
            super(itemView);
            leftChatBox = itemView.findViewById(R.id.zuo);
            leftAvatar = itemView.findViewById(R.id.zuoimg);
            leftText = itemView.findViewById(R.id.zuotext);
            rightChatBox = itemView.findViewById(R.id.you);
            rightAvatar = itemView.findViewById(R.id.youimg);
            rightText = itemView.findViewById(R.id.youtext);
        }
    }
}
