package com.example.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/27.
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    EditText accentedit;
    EditText pswedit;
    CheckBox remeb;
    Boolean isRemember = false;
    Button but_rgister;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.login_layout);
        accentedit = (EditText) findViewById(R.id.editname);
        pswedit = (EditText) findViewById(R.id.editpsw);
        Button login = (Button) findViewById(R.id.login);
        but_rgister = (Button) findViewById(R.id.but_register);
        but_rgister.setOnClickListener(new butRegisterListener());
        remeb = (CheckBox) findViewById(R.id.remember);
        isRemember = preferences.getBoolean("isRemember", false);
        if (isRemember) {
            accentedit.setText(preferences.getString("name", ""));
            pswedit.setText(preferences.getString("psw", ""));
            remeb.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://10.0.21.227:8088/json/user.json";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(url).build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            parseJSONWithJSONobject(responseData);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();



//                HttpUtil.sendOkHttpRequest(url, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                    }
//                });
//                judgeUser();
            }
        });
    }

    public void judgeUser() {
        String name = accentedit.getText().toString();
        String psw = pswedit.getText().toString();
        if (name.equals("admin") && psw.equals("123456")) {
            editor = preferences.edit();
            if (remeb.isChecked()) {
                editor.putString("name", name);
                editor.putString("psw", psw);
                editor.putBoolean("isRemember", true);
            } else {
                editor.clear();
            }
            editor.apply();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

        }
    }

    private class butRegisterListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        }
    }

    private void parseJSONWithJSONobject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            int i = 0;

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("name");
                String psw = jsonObject.getString("psw");
                Log.d(TAG, "parseJSONWithJSONobject: " + id);
                Log.d(TAG, "parseJSONWithJSONobject: " + psw);
            }
//            while (i < jsonArray.length()) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String name = accentedit.getText().toString();
//                String psw = pswedit.getText().toString();
//                if (jsonObject.getString("name").equals(name)) {
//                    if (jsonObject.getString("psw").equals(psw)) {
//                        editor = preferences.edit();
//                        if (remeb.isChecked()) {
//                            editor.putString("name", name);
//                            editor.putString("psw", psw);
//                            editor.putBoolean("isRemember", true);
//                        } else {
//                            editor.clear();
//                        }
//                        editor.apply();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                i++;
//            }
//            else {
//                Toast.makeText(LoginActivity.this, "不存在此用户", Toast.LENGTH_SHORT).show();
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
