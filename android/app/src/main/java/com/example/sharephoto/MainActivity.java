package com.example.sharephoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sharephoto.Home.HomeFragment;
import com.example.sharephoto.Profile.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout tab_home, tab_channel, tab_message, tab_profile;
    private ImageView iv_home, iv_channel, iv_message, iv_profile;
    private ImageView current_position;
    private FloatingActionButton add_img;
    private FragmentManager fragmentManager;
    private Fragment home, channel, message, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
    }

    private void initPager() {
        fragmentManager = getSupportFragmentManager();

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

        home = new HomeFragment();
        channel = new ChannelFragment();
        message = new MessageFragment();
        profile = new ProfileFragment();

        current_position = iv_home;
        current_position.setSelected(true);
        changePage(home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
    }

    private void changePage(Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content, f);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        current_position.setSelected(false);
        switch (id) {
            case R.id.tab_home:
                iv_home.setSelected(true);
                current_position = iv_home;
                changePage(home);
                break;
            case R.id.tab_channel:
                iv_channel.setSelected(true);
                current_position = iv_channel;
                changePage(channel);
                break;
            case R.id.tab_message:
                iv_message.setSelected(true);
                current_position = iv_message;
                changePage(message);
                break;
            case R.id.tab_profile:
                iv_profile.setSelected(true);
                current_position = iv_profile;
                changePage(profile);
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                startActivity(intent);
                break;
            case R.id.addImage:
                current_position.setSelected(true);
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
                break;
        }
    }
}