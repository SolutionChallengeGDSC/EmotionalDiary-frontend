package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView txt_login;
    AppCompatImageButton btn_google;
    boolean isLogin;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isLogin = false;
        txt_login = findViewById(R.id.txt_login);
        btn_google = findViewById(R.id.btn_google);

        txt_login.setTypeface(txt_login.getTypeface(), Typeface.ITALIC);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = true;
                Intent intentLogin = new Intent(getApplication(), MainActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_google:
                break;

        }
    }

}
