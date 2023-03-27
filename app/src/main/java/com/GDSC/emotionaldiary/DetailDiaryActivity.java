package com.GDSC.emotionaldiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
// Todo 날짜 값을 키 값으로 title, content 값 세팅 : 백엔드 연결
public class DetailDiaryActivity extends AppCompatActivity {
    private TextView title, content;
    String selectedDate;
    private static final int RESULT_DETAILDIARY = 0;
    private static final int RESULT_CREATEDIARY = 1;

    ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_back = findViewById(R.id.btn_back);


        Intent getDetailDiary = getIntent();
        selectedDate = getDetailDiary.getStringExtra("selectedDate"); // 날짜

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    //첫번째 버튼 행동
                    case R.id.btn_back:
                        Intent intent = new Intent();
                        String resultToMain = "gun";
                        intent.putExtra("resultToMain",resultToMain);
                        setResult(RESULT_DETAILDIARY, intent);
                        finish();
                        break;
                    case R.id.modify:
                        Toast.makeText(DetailDiaryActivity.this, "yup", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btn_back.setOnClickListener(onClickListener);


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
                Intent intent = new Intent(DetailDiaryActivity.this, CreateDiaryActivity.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("content", content.getText().toString());
                launcher.launch(intent);
                return true;
            case R.id.delete:
                return true;
            case R.id.cancel:
                return true;
        }
        return false;
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult> ()
            {
                @Override
                public void onActivityResult(ActivityResult data)
                {

                    Log.e("TAG", "data : " + data);
                    if (data.getResultCode() == RESULT_CREATEDIARY) // RESULT_CREATEDIARY = 1
                    {
                        Intent intent = data.getData();
                        String getTitle = intent.getStringExtra ("title");
                        String getContent = intent.getStringExtra("content");
                        title.setText(getTitle);
                        content.setText(getContent);
                    }
                }

            });

}