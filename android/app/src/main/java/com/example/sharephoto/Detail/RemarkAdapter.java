package com.example.sharephoto.Detail;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.Request;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {

    private Context context;
    private List<Remark> remarks = new ArrayList<>();
    private int resourceId;

    public RemarkAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
//        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RemarkAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Remark remark = remarks.get(position);
//        holder.remark_icon.setImageResource(remark.getIcon());
        Glide.with(context).load(RequestConfig.URL + remark.getIcon()).into(holder.remark_icon);
        holder.remark_username.setText(remark.getUsername());
        holder.remark_content.setText(remark.getContent());
        holder.remark_date.setText(remark.getDate());
        holder.remark_status.setSelected(remark.isStatus().equals("T"));
        holder.remark_zan_num.setText("" + remark.getNum());
        holder.remark_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state;
                if (holder.remark_status.isSelected()) {
                    holder.remark_status.setSelected(false);
                    int num = Integer.parseInt(holder.remark_zan_num.getText().toString());
                    num -= 1;
                    holder.remark_zan_num.setText("" + num);
                    state = "false";
                } else {
                    holder.remark_status.setSelected(true);
                    int num = Integer.parseInt(holder.remark_zan_num.getText().toString());
                    num += 1;
                    holder.remark_zan_num.setText("" + num);
                    state = "true";
                }
                String username = context.getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", "");
                if (!username.equals("")) {
                    new RemarkThumbsupAsyncTask(context).execute(remarks.get(position).getCommentId() + "", username, state);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return remarks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView remark_icon;
        TextView remark_username;
        TextView remark_date;
        ImageView remark_status;
        TextView remark_content;
        TextView remark_zan_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remark_icon = itemView.findViewById(R.id.remark_icon);
            remark_username = itemView.findViewById(R.id.remark_username);
            remark_date = itemView.findViewById(R.id.remark_date);
            remark_status = itemView.findViewById(R.id.remark_status);
            remark_content = itemView.findViewById(R.id.remark_content);
            remark_zan_num = itemView.findViewById(R.id.remark_zan_num);
        }
    }
}
