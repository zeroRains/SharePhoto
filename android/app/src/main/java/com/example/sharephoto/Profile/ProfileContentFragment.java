package com.example.sharephoto.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharephoto.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileContentFragment extends Fragment {

    private static final String ARG_PARAM1 = "ARG_LABEL";
    private String label;

    private View profileContentView;
    private TextView tv;

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
        // Inflate the layout for this fragment
        profileContentView = inflater.inflate(R.layout.fragment_profile_content, container, false);
        tv = profileContentView.findViewById(R.id.test_tv);
        tv.setText("这个是" + label + "标签页");
        return profileContentView;
    }
}