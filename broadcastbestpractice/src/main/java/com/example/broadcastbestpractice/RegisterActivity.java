package com.example.broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/5/28.
 */

public class RegisterActivity extends BaseActivity {
    EditText reg_name;
    EditText reg_psw;
    EditText reg_psw2;
    Button register;
    Button but_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_psw = (EditText) findViewById(R.id.reg_psw);
        reg_psw2 = (EditText) findViewById(R.id.reg_psw2);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new registerButtonListener());
        but_login = (Button) findViewById(R.id.but_login);
        but_login.setOnClickListener(new butLoginListener());
    }

    private class registerButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class butLoginListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }


}
