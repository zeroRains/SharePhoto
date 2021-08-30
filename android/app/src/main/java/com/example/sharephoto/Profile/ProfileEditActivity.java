package com.example.sharephoto.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharephoto.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    private CircleImageView edit_icon;
    private EditText edit_username;
    private Spinner edit_sex;
    private EditText edit_info;
    private Button logout;

    public static final String ICON = "icon";
    public static final String USERNAME = "username";
    public static final String SEX = "sex";
    public static final String INFO = "info";
    public static final int LOGOUT = 886;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary));
        }
        initView();
    }

    private void initView() {
        edit_icon = findViewById(R.id.profile_edit_icon);
        edit_username = findViewById(R.id.profile_edit_username);
        edit_sex = findViewById(R.id.profile_edit_sex);
        edit_info = findViewById(R.id.profile_edit_info);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(LOGOUT, intent);
                ((Activity) v.getContext()).finish();
            }
        });

        edit_sex.setSelection(0, true);
    }

    public void edit_back(View v) {
//        Toast.makeText(ProfileEditActivity.this, edit_username.getText().toString() + edit_sex.getSelectedItem().toString() + edit_info.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(USERNAME, edit_username.getText().toString());
        intent.putExtra(SEX, edit_sex.getSelectedItem().toString());
        intent.putExtra(INFO, edit_info.getText().toString());
        intent.putExtra(ICON, R.drawable.icon);
        setResult(RESULT_OK, intent);
        ((Activity) v.getContext()).finish();
    }
}