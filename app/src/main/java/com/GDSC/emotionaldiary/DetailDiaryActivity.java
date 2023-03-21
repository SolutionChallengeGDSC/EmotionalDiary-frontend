package com.GDSC.emotionaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailDiaryActivity extends AppCompatActivity {
    private TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
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
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        switch (item.getItemId()) {
            case R.id.modify:
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
}