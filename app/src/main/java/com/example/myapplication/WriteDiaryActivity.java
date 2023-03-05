package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;


public class WriteDiaryActivity extends AppCompatActivity{

    AppCompatImageButton btn_X; // 뒤로가기 버튼
    ImageView imgbtn_lock_post; // 게시글 잠금 버튼
    ImageButton imgbtn_score; // 감정 점수 버튼

    boolean isLocked;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);


        isLocked = false;
        btn_X = (AppCompatImageButton) findViewById(R.id.btn_x);
        imgbtn_lock_post = findViewById(R.id.imgbtn_lock_post);
        imgbtn_score = findViewById(R.id.imgbtn_score);






        imgbtn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickHandler(view);
            }
        });


        imgbtn_lock_post.setOnClickListener(new View.OnClickListener() { // 게시글 잠금 버튼
            @Override
            public void onClick(final View view) {
                view.setActivated(!view.isActivated());
                isLocked = isLocked == false ? true : false;
            }
        });

        imgbtn_lock_post.setActivated(isLocked);

    }

    private void OnClickHandler(View view){ // dialog (감정 점수)
        View dialogView = getLayoutInflater().inflate(R.layout.dailog_sample, null);
        final EditText nameEditText = (EditText)dialogView.findViewById(R.id.name);
        final EditText NicknameEditText = (EditText)dialogView.findViewById(R.id.nickname);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int pos)
            {
                String name = "이름은 : " + nameEditText.getText().toString();
                String nickname = "별명은 : " + NicknameEditText.getText().toString();

                Toast.makeText(getApplicationContext(),name + "\n" + nickname, Toast.LENGTH_LONG).show();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM); // dialog align bottom
        alertDialog.show();


        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        int height = size.y;
        WindowManager.LayoutParams params=alertDialog.getWindow().getAttributes();
        params.width=width;
        params.height=height;
        alertDialog.getWindow().setAttributes(params);
    }

}
