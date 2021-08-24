package com.example.sharephoto.Profile;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileContentFragment extends Fragment {

    private String label;
    private Profile item;
    private List<Profile> contentList;
    private static final String ARG_PARAM1 = "ARG_LABEL";

    private RecyclerView recyclerView;
    private View profileContentView;

    private ProfileContentAdapter contentAdapter;

    public ProfileContentFragment() {
        // Required empty public constructor
    }

    public static ProfileContentFragment newInstance(String label) {
        ProfileContentFragment fragment = new ProfileContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, label);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            label = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        // Inflate the layout for this fragment
        profileContentView = inflater.inflate(R.layout.fragment_profile_content, container, false);
        recyclerView = profileContentView.findViewById(R.id.content_list);

        contentAdapter = new ProfileContentAdapter(getContext(), contentList, R.layout.item_profile_content);
        contentAdapter.setOnItemClickListener(new ProfileContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(contentAdapter);

        return profileContentView;
    }

    private void initData() {
        contentList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            item = new Profile();
            if (i % 2 == 0) {
                item.setImageid(R.drawable.nmsl);
            } else {
                item.setImageid(R.drawable.icon);
            }
            contentList.add(item);
        }
    }
}