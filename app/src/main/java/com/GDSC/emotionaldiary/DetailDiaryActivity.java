package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
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
    private TextView title, content, datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        datetime = findViewById(R.id.datetime);
        btn_close = findViewById(R.id.btn_close);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent getIntent = getIntent();
        Long diaryId = getIntent.getLongExtra("diaryId", 0);
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
                Intent intent = new Intent(getApplicationContext(), CreateDiaryActivity.class);
                intent.putExtra("title", title.getText());
                intent.putExtra("content", content.getText());
                startActivity(intent);
                return true;
            case R.id.delete:
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
            String getTitle = new JSONObject(responseStr).getString("title");
            String getContent = new JSONObject(responseStr).getString("content");
            String getCreatedAt = new JSONObject(responseStr).getString("createdAt");
            getCreatedAt = getCreatedAt.replace("T", " ").substring(0, 16);

            title.setText(getTitle);
            content.setText(getContent);
            datetime.setText(getCreatedAt);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}