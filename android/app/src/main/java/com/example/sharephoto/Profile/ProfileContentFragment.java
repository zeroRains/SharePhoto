package com.example.sharephoto.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.Detail.DetailActivity;
import com.example.sharephoto.R;
import com.example.sharephoto.RequestConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileContentFragment extends Fragment {

    public String getLabel() {
        return label;
    }

    private String label;
    private Profile item;
    private List<Profile> contentList = new ArrayList<>();
    private static final String ARG_PARAM1 = "ARG_LABEL";

    private RecyclerView recyclerView;
    private View profileContentView;

    private ProfileContentAdapter contentAdapter;

    public ProfileContentFragment(String label) {
        // Required empty public constructor
        this.label = label;
    }

    public static ProfileContentFragment newInstance(String label) {
        ProfileContentFragment fragment = new ProfileContentFragment(label);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, label);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            label = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (profileContentView == null) {
            profileContentView = inflater.inflate(R.layout.fragment_profile_content, container, false);
        }
        View view = profileContentView;
        recyclerView = profileContentView.findViewById(R.id.content_list);

        contentAdapter = new ProfileContentAdapter(getContext(), contentList, R.layout.item_profile_content);
        contentAdapter.setOnItemClickListener(new ProfileContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("shuoshuoId", contentList.get(position).getId());
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(contentAdapter);
        initData();
        return profileContentView;
    }

    private void initData() {
        String URL = "";
        switch (label) {
            case "动态":
                URL = RequestConfig.SELF_PUBLISH;
                break;
            case "收藏":
                URL = RequestConfig.STARTED_SHUOSHUO;
                break;
            case "赞过":
                URL = RequestConfig.THUMBSUP_SHUOSHUO;
                break;
            default:
                break;
        }
        String id = getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getString("username", "");
//        Log.d(TAG, "initData: ");
        if (!URL.equals("") && !id.equals("")) {
            Log.d("xxxxx", "initData: " + URL + " " + label);
            new GetPhotoAsyncTask(URL, contentList, contentAdapter).execute(id);
        }
//        for (int i = 0; i < 20; i++) {
//            item = new Profile();
//            if (i % 2 == 0) {
//                item.setImageid(R.drawable.nmsl);
//            } else {
//                item.setImageid(R.drawable.icon);
//            }
//            contentList.add(item);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("xxxxx", "resume: " + " " + label);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("xxxxx", "attach : " + " " + label);
    }
}