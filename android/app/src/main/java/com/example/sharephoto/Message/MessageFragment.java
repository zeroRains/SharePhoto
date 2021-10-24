package com.example.sharephoto.Message;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.R;
import com.example.sharephoto.Utils.ChatTools;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    private String mParam1;
    private String chatContent;
    private List<Chator> chatorList;
    private static final String ARG_PARAM1 = "message";

    private View messageView;
    private TextView chatText;
    private Button sendBtn;
    private RecyclerView recyclerView;

    private Chator chator;
    private ChatTools chatTools;
    private MessageAdapter adapter;
    private LinearLayoutManager layoutManager;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(requireContext().getApplicationContext(), "连接服务器成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    recyclerView.scrollToPosition(chatorList.size() - 1);
                    break;
            }

        }
    };


    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance(String param1) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        messageView = inflater.inflate(R.layout.fragment_message, container, false);
        chatText = messageView.findViewById(R.id.chat_text);
        sendBtn = messageView.findViewById(R.id.send_button);
        recyclerView = messageView.findViewById(R.id.chat_content);

        chatorList = new ArrayList<>();
        chatTools = new ChatTools(this, "pan.kexie.space");
        adapter = new MessageAdapter(getContext(), chatorList, R.layout.item_chat_box);
        layoutManager = new LinearLayoutManager(requireContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatContent = chatText.getText().toString();
                if (chatContent.length() == 0) {
                    Toast.makeText(requireContext().getApplicationContext(), "发送消息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    chatorList.add(new Chator(chatContent, R.drawable.duifang, false));

                    chatTools.sendMessage(chatContent);
                    recyclerView.setAdapter(adapter);
                    chatText.setText(null);
                    recyclerView.scrollToPosition(chatorList.size() - 1);
                }
            }
        });

        return messageView;
    }

    public List<Chator> getChatorList() {
        return this.chatorList;
    }

    public Handler getHandler() {
        return handler;
    }
}