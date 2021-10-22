package com.example.sharephoto.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharephoto.R;

public class LoginActivity extends AppCompatActivity {
    String account_text;
    String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText account = findViewById(R.id.login_account);
        EditText password = findViewById(R.id.login_password);
        Button submit = findViewById(R.id.login_button);
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_text = account.getText().toString();
                password_text = password.getText().toString();
//                if (check()) {
//                    Toast.makeText(LoginActivity.this, "登录成功，欢迎您" + account_text, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, "登录失败，请检查好用户名和密码", Toast.LENGTH_SHORT).show();
//                }
                check();
            }

            private void check() {
                Toast.makeText(LoginActivity.this, "进来1", Toast.LENGTH_SHORT).show();
                new LoginAsyncTask(LoginActivity.this, account_text).execute(
                        account_text,
                        password_text);
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

}