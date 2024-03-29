package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

public class DetailDiaryActivity extends AppCompatActivity {
    private ImageButton btn_close;
    private TextView title, content, date;
    Long diaryId;
    String getTitle, getContent, getDate;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        date = findViewById(R.id.date);
        btn_close = findViewById(R.id.btn_close);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent getIntent = getIntent();
        diaryId = getIntent.getLongExtra("diaryId", 0);
        new Thread(() -> {getDiary(diaryId);}).start();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                finish();
                Intent createDiaryIntent = new Intent(getApplicationContext(), CreateDiaryActivity.class);
                createDiaryIntent.putExtra("diaryId", diaryId);
                createDiaryIntent.putExtra("title", title.getText());
                createDiaryIntent.putExtra("content", content.getText());
                createDiaryIntent.putExtra("date", date.getText());
                createDiaryIntent.putExtra("isUpdate", true);
                startActivity(createDiaryIntent);
                return true;
            case R.id.delete:
                new Thread(() -> {delDiary(diaryId);}).start();
                return true;
            case R.id.cancel:
                return true;
        }
        return false;
    }

    public void getDiary(Long id) {
        String responseString = null;
        try {
            OkHttpClient client = new OkHttpClient();
            String url = "http://34.64.254.35/diary/"+id;
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).get();
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            okhttp3.Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody body = response.body();
                responseString = body.string();
                body.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String responseStr = new JSONObject(responseString).getString("result");
            getTitle = new JSONObject(responseStr).getString("title");
            getContent = new JSONObject(responseStr).getString("content");
            getDate = new JSONObject(responseStr).getString("date");
            getDate = getDate.substring(0, 10);

            setData();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                title.setText(getTitle);
                content.setText(getContent);
                date.setText(getDate);
            }
        });
    }

    public void delDiary(Long id) {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = "http://34.64.254.35/diary/"+id;
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).delete();
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            okhttp3.Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody body = response.body();
                body.close();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}