package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class CreateDiaryActivity extends AppCompatActivity {
    private ImageButton close_btn, emotional_score_btn, lock;
    private AppCompatButton complete_btn;
    private EditText title, content;
    boolean isUpdate;
    Long diaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        Intent getIntent = getIntent();
        isUpdate = getIntent.getBooleanExtra("isUpdate", false);
        diaryId = getIntent.getLongExtra("diaryId", 0);
        title.setText(getIntent.getStringExtra("title"));
        content.setText(getIntent.getStringExtra("content"));

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
                if(isUpdate) {  // 수정일 때
                    new Thread(() -> {putDiary(diaryId);}).start();
                }
                else {  // 작성일 때
                    new Thread(() -> {postDiary();}).start();
                }
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

    public void putDiary(Long id) {
        try {
            String userEmail = "test1@naver.com";  // 임시
            OkHttpClient client = new OkHttpClient();
            String url = "http://34.64.254.35/diary/"+id;
            String strBody = String.format("{\"title\" : \"%s\", \"content\" : \"%s\", \"userEmail\" : \"%s\"}", title.getText(), content.getText(), userEmail);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).put(requestBody);
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            okhttp3.Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                response.body().close();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postDiary() {
        try {
            String userEmail = "test1@naver.com";  // 임시
            OkHttpClient client = new OkHttpClient();
            String url = "http://34.64.254.35/diary";
            String strBody = String.format("{\"title\" : \"%s\", \"content\" : \"%s\", \"userEmail\" : \"%s\"}", title.getText(), content.getText(), userEmail);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).post(requestBody);
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            okhttp3.Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                response.body().close();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
