package com.example.sharephoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    String account_text;
    String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText account = findViewById(R.id.register_account);
        EditText password = findViewById(R.id.register_password);
        Button submit = findViewById(R.id.register_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_text = account.getText().toString();
                password_text = password.getText().toString();
                if (check()) {
                    Toast.makeText(RegisterActivity.this, "登录成功." + account_text, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "登录失败，请检查用户名和密码是否正确.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initStatusBar();
    }

    //    状态主题颜色一致
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private boolean check() {
        if (account_text.equals("admin") && password_text.equals("123456"))
            return true;
        return false;
    }
}