package com.example.sharephoto.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharephoto.R;


public class HomePhotoFragment extends Fragment {
    private String text;

    public HomePhotoFragment(String text) {
        // Required empty public constructor
        this.text = text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_photo, container, false);
        TextView textView = view.findViewById(R.id.name);
        textView.setText(text);
        return view;
    }
}