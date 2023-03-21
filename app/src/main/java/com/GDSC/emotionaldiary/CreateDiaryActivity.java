package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class CreateDiaryActivity extends AppCompatActivity {
    private EditText title, content;
    private ImageButton close_btn, emotional_score_btn, lock;
    private AppCompatButton complete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        // 수정
        Intent intent = getIntent();
        title = findViewById(R.id.title);
        title.setText(intent.getStringExtra("title"));
        content = findViewById(R.id.content);
        content.setText(intent.getStringExtra("content"));

        // 닫기 버튼
        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 완료 버튼
        complete_btn = findViewById(R.id.complete_btn);
        complete_btn.setOnClickListener(new View.OnClickListener() {
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
