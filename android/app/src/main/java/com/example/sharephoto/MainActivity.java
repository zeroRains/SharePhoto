package com.example.sharephoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout tab_home, tab_channel, tab_message, tab_profile;
    private ImageView iv_home, iv_channel, iv_message, iv_profile;
    private ImageView current_position;
    private FloatingActionButton add_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
    }

    private void initPager() {
        add_img = findViewById(R.id.addImage);
        tab_home = findViewById(R.id.tab_home);
        tab_channel = findViewById(R.id.tab_channel);
        tab_message = findViewById(R.id.tab_message);
        tab_profile = findViewById(R.id.tab_profile);

        iv_home = findViewById(R.id.tab_iv_home);
        iv_channel = findViewById(R.id.tab_iv_channel);
        iv_message = findViewById(R.id.tab_iv_message);
        iv_profile = findViewById(R.id.tab_iv_profile);

        add_img.setOnClickListener(this);
        tab_home.setOnClickListener(this);
        tab_message.setOnClickListener(this);
        tab_profile.setOnClickListener(this);
        tab_channel.setOnClickListener(this);

        current_position = iv_home;
        current_position.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        current_position.setSelected(false);
        switch (id){
            case R.id.tab_home:
                iv_home.setSelected(true);
                current_position = iv_home;
                break;
            case R.id.tab_channel:
                iv_channel.setSelected(true);
                current_position = iv_channel;
                break;
            case R.id.tab_message:
                iv_message.setSelected(true);
                current_position = iv_message;
                break;
            case R.id.tab_profile:
                iv_profile.setSelected(true);
                current_position = iv_profile;
                break;
            case R.id.addImage:
                current_position.setSelected(true);
                Toast.makeText(MainActivity.this,"click the add image",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}