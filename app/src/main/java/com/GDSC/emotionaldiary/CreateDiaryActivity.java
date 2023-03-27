package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CreateDiaryActivity extends AppCompatActivity {
    private ImageButton close_btn, emotional_score_btn, lock;
    private EditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        Intent getDetailIntent = getIntent();
        try{
            // 수정일 때
            title.setText(getDetailIntent.getStringExtra("title"));
            content.setText(getDetailIntent.getStringExtra("content"));
        }catch (NullPointerException e){
            // 작성일 때
            title.setText("");
            content.setText("");
        }

        // 닫기 버튼
        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 감정 점수 다이얼로그 띄우기
        emotional_score_btn = findViewById(R.id.emotional_score_btn);
        emotional_score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmotionalScoreDialog emotionalScoreDialog = new EmotionalScoreDialog();
                emotionalScoreDialog.show(getSupportFragmentManager(), "emotional score");

            }
        });

        // 잠금 아이콘
        lock = findViewById(R.id.lock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock.isSelected()) {
                    lock.setSelected(false);
                }
                else {
                    lock.setSelected(true);
                }
            }
        });
    }
}
