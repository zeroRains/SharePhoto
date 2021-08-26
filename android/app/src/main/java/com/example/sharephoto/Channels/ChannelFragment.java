package com.example.sharephoto.Channels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.R;

import java.util.ArrayList;
import java.util.List;


public class ChannelFragment extends Fragment {
    List<Channel> channel_list = new ArrayList<>();
    View channel_view;
    public static String ACTIVITY_NAME = "activity_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (channel_view == null) {
            channel_view = inflater.inflate(R.layout.fragment_channel, container, false);
            RecyclerView recyclerView = channel_view.findViewById(R.id.channel_list);
            initData();
            ChannelAdapter adapter = new ChannelAdapter(getContext(), channel_list, R.layout.item_channel_item);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), ChannelDetailActivity.class);
                    TextView temp = view.findViewById(R.id.channel_text);
                    intent.putExtra(ACTIVITY_NAME,temp.getText().toString());
                    startActivity(intent);
                }
            });
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }
        return channel_view;
    }


    private void initData() {
        for (int i = 0; i < 10; i++) {
            Channel item = new Channel();
            if (i % 2 == 0) {
                item.setPhoto(R.drawable.channel_eat);
                item.setTitle("美食");
            } else {
                item.setPhoto(R.drawable.channel_photo);
                item.setTitle("摄影");
            }
            channel_list.add(item);
        }
    }
}