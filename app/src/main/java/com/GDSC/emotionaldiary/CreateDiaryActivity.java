package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
// Todo 감정 점수 세팅 ..
public class CreateDiaryActivity extends AppCompatActivity {
    private ImageButton emotional_score_btn, lock, close_btn;
    EditText title, content;
    AppCompatButton complete_btn;
    private static final int RESULT_CREATEDIARY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);
        Intent getDetailIntent = getIntent();
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        close_btn = findViewById(R.id.close_btn);
        complete_btn = findViewById(R.id.complete_btn);
        try{
            title.setText(getDetailIntent.getStringExtra("title"));
            content.setText(getDetailIntent.getStringExtra("content"));
        }catch (NullPointerException e){
            title.setText("");
            content.setText("");
        }
        // 감정 점수 다이얼로그 띄우기
        emotional_score_btn = findViewById(R.id.emotional_score_btn);
        emotional_score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmotionalScoreDialog emotionalScoreDialog = new EmotionalScoreDialog();
                emotionalScoreDialog.show(getSupportFragmentManager(), "emotional score");

            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDiaryActivity.this, DetailDiaryActivity.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("content", content.getText().toString());
                Toast.makeText(CreateDiaryActivity.this, "complete_btn Clicked", Toast.LENGTH_SHORT).show();
                Log.e("content", content.getText().toString());
                setResult(RESULT_CREATEDIARY, intent);
                finish();
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

        // 액션바 제거
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
    }
}
