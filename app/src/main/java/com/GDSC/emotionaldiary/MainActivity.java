package com.GDSC.emotionaldiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GDSC.emotionaldiary.LoginActivity;
import com.GDSC.emotionaldiary.TodoItem_Item;
import com.GDSC.emotionaldiary.TodoItem_RecyclerViewAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
// Todo : 일기 비밀번호 설정 dialog

public class MainActivity extends AppCompatActivity {
    /*------------------------------------------Side Menu, User Info------------------------------------------ */
    private DrawerLayout mainDrawerLayout;
    private AppCompatImageView openMenuButton;
    TextView main_navigation_txt_logout;
    TextView main_navigation_txt_setting_pw;
    String diaryPw="password"; // 일기 비밀번호
    String diaryPwHint="passwordhint"; // 일기 비밀번호 힌트
    CircleImageView img_side_menu_userImg;
    TextView txt_side_menu_userId;
    Uri userProfileImg;
    int userId = 0; // 임시 값, 추후 수정

    /*------------------------------------------Side Menu, User Info------------------------------------------ */

    /*------------------------------------------Main------------------------------------------ */
    TextView textDiary;
    TextView textTodo;
    TextView title_recommended_music;
    TextView info_recommended_music;
    TextView title_recommended_movie;
    TextView info_recommended_movie;
    SwitchCompat switchChangeMode;

    MaterialCalendarView calendar_todo;
    MaterialCalendarView calendar_diary;
    LinearLayout main_mode_todo;
    LinearLayout main_mode_diary;
    ImageView img_recommended_music;
    ImageView img_recommended_movie;
    AppCompatImageView btn_check_todo;
    AppCompatImageView refresh_recommended_music;
    AppCompatImageView refresh_recommended_movie;
    LinearLayout recommended_music;
    LinearLayout recommended_movie;

    private TodoItem_RecyclerViewAdapter mRecyclerAdapter;
    private ArrayList<TodoItem_Item> mtodoItems = new ArrayList();


    LinearLayout todo_category;
    TextView txt_todo_category;

    boolean isChecked;
    /*------------------------------------------Main------------------------------------------ */


    /*------------------------------------------ onCreate ------------------------------------------ */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_include_drawer);



        textDiary = findViewById(R.id.txt_mode_Diary); // text_mode_diary
        textTodo = findViewById(R.id.txt_mode_Todo); // text_mode_todo

        switchChangeMode = (SwitchCompat) findViewById(R.id.switch_change_mode);

        calendar_todo = (MaterialCalendarView) findViewById(R.id.calendarview_todo); // calender_todo
        calendar_diary = (MaterialCalendarView) findViewById(R.id.calendarview_diary); // calender_diary

        main_mode_todo = (LinearLayout) findViewById(R.id.main_mode_todo); // main_todo
        main_mode_diary = (LinearLayout) findViewById(R.id.main_mode_diary); // main_diary

        recommended_music = findViewById(R.id.recommended_music); // recommended_music Layout
        img_recommended_music = findViewById(R.id.img_recommended_music); // recommended_music Image
        title_recommended_music = findViewById(R.id.title_recommended_music); // recommended_music Title
        info_recommended_music = findViewById(R.id.info_recommended_music); // recommended_music Info
        refresh_recommended_music = findViewById(R.id.refresh_recommended_music); // recommended_music refresh Button

        recommended_movie = findViewById(R.id.recommended_movie); // recommended_movie Layout
        img_recommended_movie = findViewById(R.id.img_recommended_movie); // recommended_movie Image
        title_recommended_movie = findViewById(R.id.title_recommended_movie); // recommended_movie Title
        info_recommended_movie = findViewById(R.id.info_recommended_movie); // recommended_movie Director
        refresh_recommended_movie = findViewById(R.id.refresh_recommended_movie); // recommended_movie refresh Button

        isChecked = false;




        /*-------------------------------------------Main------------------------------------------*/
        Object[][] musicList = {{R.drawable.main_img_music1,"Stay this way","fromis_9"},
                {R.drawable.main_img_music2,"Ditto","NewJeans"},
                {R.drawable.main_img_music3,"DM","fromis_9"},
                {R.drawable.main_img_music4,"Feel Good","fromis_9"},
                {R.drawable.main_img_music5,"We go","fromis_9"},
                {R.drawable.main_img_music6,"Love Bomb","fromis_9"},
                {R.drawable.main_img_music7,"Talk & Talk","fromis_9"}
        };
        Object[][] movieList = {{R.drawable.main_img_movie1,"대외비","이원태"},
                {R.drawable.main_img_movie2,"더 퍼스트 슬램덩크","이노우에 다케히코"},
                {R.drawable.main_img_movie3,"앤트맨과 와스프: 퀀텀매니아","페이튼 리드"},
                {R.drawable.main_img_movie4,"서치 2","니콜라스 D. 존슨"},
                {R.drawable.main_img_movie5,"귀멸의 칼날: 상현집결, 그리고 도공 마을로","소토자키 하루오"},
                {R.drawable.main_img_movie6,"아임 히어로 더 파이널","오윤동"},
                {R.drawable.main_img_movie7,"카운트","권혁재"}
        };



        int randomNum_music = (int) (Math.random() * musicList.length);
        img_recommended_music.setImageResource((Integer) musicList[randomNum_music][0]);
        title_recommended_music.setText((CharSequence) musicList[randomNum_music][1]);
        info_recommended_music.setText((CharSequence) musicList[randomNum_music][2]);

        int randomNum_movie = (int) (Math.random() * movieList.length);
        img_recommended_movie.setImageResource((Integer) movieList[randomNum_movie][0]);
        title_recommended_movie.setText((CharSequence) movieList[randomNum_movie][1]);
        info_recommended_movie.setText((CharSequence) movieList[randomNum_movie][2]);

        recommended_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String musicTitle = "https://www.google.com/search?q="+ info_recommended_music.getText().toString() + " " +title_recommended_music.getText().toString();
                Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(musicTitle));
                startActivity(uriIntent);
            }
        });

        refresh_recommended_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNum = (int) (Math.random() * musicList.length);
                img_recommended_music.setImageResource((Integer) musicList[randomNum][0]);
                title_recommended_music.setText((CharSequence) musicList[randomNum][1]);
                info_recommended_music.setText((CharSequence) musicList[randomNum][2]);
            }
        });


        recommended_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = "https://www.google.com/search?q=movie+"+ title_recommended_movie.getText().toString();
                Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieTitle));
                startActivity(uriIntent);
            }
        });
        refresh_recommended_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNum = (int) (Math.random() * movieList.length);
                img_recommended_movie.setImageResource((Integer) movieList[randomNum][0]);
                title_recommended_movie.setText((CharSequence) movieList[randomNum][1]);
                info_recommended_movie.setText((CharSequence) movieList[randomNum][2]);
            }
        });




        // Mode_Change Button (Diary <-> Todo)
        class visibilitySwitchListener implements CompoundButton.OnCheckedChangeListener{
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ // mode_Todo
                    textDiary.setVisibility(View.INVISIBLE);
                    textTodo.setVisibility(View.VISIBLE);
                    main_mode_todo.setVisibility(View.VISIBLE);
                    main_mode_diary.setVisibility(View.INVISIBLE);
                }
                else{ // mode_Diary
                    textDiary.setVisibility(View.VISIBLE);
                    textTodo.setVisibility(View.INVISIBLE);
                    main_mode_todo.setVisibility(View.INVISIBLE);
                    main_mode_diary.setVisibility(View.VISIBLE);

                }
            }
        }
        switchChangeMode.setOnCheckedChangeListener(new visibilitySwitchListener()); // mode switch button 활성


        /* ------------------------------------- feature : CalendarView ------------------------------------- */

        calendar_todo.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months))); //
        calendar_todo.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays))); // 요일

        calendar_diary.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months))); //
        calendar_diary.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays))); // 요일


        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendar_todo.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        calendar_diary.setHeaderTextAppearance(R.style.CalendarWidgetHeader_Diary);


        // 일자 선택 시 정의한 드로어블이 적용
        calendar_todo.addDecorators(new MainActivity.DayDecorator(this));
        calendar_diary.addDecorators(new MainActivity.DayDecorator_Diary(this));


        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        calendar_todo.setTitleFormatter(new TitleFormatter() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public CharSequence format(CalendarDay day) {

                Date inputText = day.getDate();
                Instant instant = inputText.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                String[] calendarHeaderElements = localDate.toString().split("-");

                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0]) // calendarHeaderElements[0] : 2023, calendarHeaderElements[1] : 03
                        .append(" - ")
                        .append(calendarHeaderElements[1]);

                return calendarHeaderBuilder.toString();
            }
        });

        calendar_diary.setTitleFormatter(new TitleFormatter() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public CharSequence format(CalendarDay day) {
                Date inputText = day.getDate();
                Instant instant = inputText.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                String[] calendarHeaderElements = localDate.toString().split("-");

                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0]) // calendarHeaderElements[0] : 2023, calendarHeaderElements[1] : 03
                        .append(" - ")
                        .append(calendarHeaderElements[1]);

                return calendarHeaderBuilder.toString();
            }
        });
        /* ------------------------------------- feature : CalendarView ------------------------------------- */



        RecyclerView TodoRecyclerview = findViewById(R.id.recyclerView_TodoItem);

        mRecyclerAdapter = new TodoItem_RecyclerViewAdapter();
        TodoRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        TodoRecyclerview.setAdapter(mRecyclerAdapter);


        mtodoItems = new ArrayList<>();
        todo_category = (LinearLayout) findViewById(R.id.todo_category);
        txt_todo_category = findViewById(R.id.txt_todo_category);


        todo_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoDialog(v);
            }
        });

        for(int i = 1; i <= 5; i++){
            mtodoItems.add(new TodoItem_Item("gun" + i));
        }
        mRecyclerAdapter.setmTodoList(mtodoItems);


        /*-------------------------------------------Main------------------------------------------*/


        /*------------------------------------------ feature : Side Menu ------------------------------------------ */

        Intent logInIntent = getIntent();


        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        openMenuButton = findViewById(R.id.ic_menu);

        main_navigation_txt_logout = findViewById(R.id.main_navigation_txt_logout);
        img_side_menu_userImg = findViewById(R.id.img_side_menu_userImg);
        txt_side_menu_userId = findViewById(R.id.txt_side_menu_userId);
        main_navigation_txt_setting_pw = findViewById(R.id.main_navigation_txt_setting_pw);

        userProfileImg = logInIntent.getParcelableExtra("profileImg");
        String userProfileName = logInIntent.getStringExtra("name");
        userId = logInIntent.getIntExtra("userId",0);

        if(!TextUtils.isEmpty(userProfileName)){
            txt_side_menu_userId.setText(userProfileName);
            Picasso.get().load(userProfileImg).into(img_side_menu_userImg);
        }

        main_navigation_txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog(v);
            }
        });
        main_navigation_txt_setting_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordDialog(v);
            }
        });
        openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        /*------------------------------------------ feature : Side Menu ------------------------------------------ */
    }
    /*------------------------------------------ onCreate ------------------------------------------ */

    /*------------------------------------------ feature : Back Button ------------------------------------------ */
    private final long finishtimeed = 1000;
    private long presstime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - presstime;
        if(mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            if (0 <= intervalTime && finishtimeed >= intervalTime)
            {
                finish();
            }
            else
            {
                presstime = tempTime;
                Toast.makeText(getApplicationContext(), "한번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*------------------------------------------ feature : Back Button ------------------------------------------ */

    private static class DayDecorator implements DayViewDecorator {
        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
            view.addSpan(new ForegroundColorSpan(0xFF92BEA9));   // 달력 안의 모든 숫자
        }
    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 (Calendar_Diary) */
    private static class DayDecorator_Diary implements DayViewDecorator{
        private final Drawable drawable_diary;
        private final Drawable drawable_all;


        public DayDecorator_Diary(Context context){
            drawable_diary = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
            drawable_all = ContextCompat.getDrawable(context, R.drawable.calendar_day_default);
        }
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable_diary);
            view.setBackgroundDrawable(drawable_all);
            // 달력 안의 모든 숫자
        }

    }
    /* ------------------------------------- feature : Add_Todo_dialog ------------------------------------- */
    public void addTodoDialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_todo, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView txt_category = dialogView.findViewById(R.id.txt_category); // dialog category
        txt_category.setText(txt_todo_category.getText().toString());
        EditText et_category = dialogView.findViewById(R.id.et_category);

        EditText writeContents = dialogView.findViewById(R.id.writeContents);
        AppCompatImageView btn_edit_category = dialogView.findViewById(R.id.btn_edit_category);
        AppCompatImageView btn_edit_complete = dialogView.findViewById(R.id.btn_edit_complete);

        btn_edit_category.setOnClickListener(new View.OnClickListener() { // category 편집 버튼
            @Override
            public void onClick(View v) {
                et_category.setVisibility(View.VISIBLE);
                txt_category.setVisibility(View.GONE);
                btn_edit_category.setVisibility(View.GONE);
                btn_edit_complete.setVisibility(View.VISIBLE);
                et_category.setText(txt_category.getText().toString());
            }
        });
        btn_edit_complete.setOnClickListener(new View.OnClickListener() { // category 편집 완료 버튼
            @Override
            public void onClick(View v) {
                txt_category.setText(et_category.getText().toString());
                et_category.setVisibility(View.GONE);
                txt_category.setVisibility(View.VISIBLE);
                btn_edit_category.setVisibility(View.VISIBLE);
                btn_edit_complete.setVisibility(View.GONE);
            }
        });


        Button ok_btn = dialogView.findViewById(R.id.saveBtn); // ok button
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(writeContents.getText().toString().length() != 0){
                    mtodoItems.add(new TodoItem_Item(writeContents.getText().toString()));
                    mRecyclerAdapter.setmTodoList(mtodoItems);
                }
                if(txt_category.getText().toString().length() != 0){
                    txt_todo_category.setText(txt_category.getText().toString());
                }
                alertDialog.dismiss();
            }
        });

        Button cancle_btn = dialogView.findViewById(R.id.cancelBtn);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    /* ------------------------------------- feature : Add_Todo_dialog ------------------------------------- */

    /* ------------------------------------- feature : change_password_dailog  ------------------------------------- */
    public void changePasswordDialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_diary_pw, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView txt_password_hint = dialogView.findViewById(R.id.txt_password_hint);
        AppCompatImageView btn_edit_password_hint = dialogView.findViewById(R.id.btn_edit_password_hint);
        EditText et_password_hint = dialogView.findViewById(R.id.et_password_hint);
        AppCompatImageView btn_edit_password_hint_complete = dialogView.findViewById(R.id.btn_edit_password_hint_complete);

        TextView txt_password = dialogView.findViewById(R.id.txt_password);
        AppCompatImageView btn_edit_password = dialogView.findViewById(R.id.btn_edit_password);
        EditText et_password = dialogView.findViewById(R.id.et_password);
        AppCompatImageView btn_edit_password_complete = dialogView.findViewById(R.id.btn_edit_password_complete);

        Button cancelBtn_diary_pw = dialogView.findViewById(R.id.cancelBtn_diary_pw);
        Button saveBtn_diary_pw = dialogView.findViewById(R.id.saveBtn_diary_pw);

        txt_password_hint.setText(diaryPwHint);
        String pw_show = "";
        for(int i = 0; i<diaryPw.length(); i++){
            pw_show += "*";
        }
        txt_password.setText(pw_show);

        btn_edit_password_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_password_hint.setVisibility(View.GONE);
                btn_edit_password_hint.setVisibility(View.GONE);
                et_password_hint.setVisibility(View.VISIBLE);
                btn_edit_password_hint_complete.setVisibility(View.VISIBLE);
                et_password_hint.setText(txt_password_hint.getText().toString());
            }
        });
        btn_edit_password_hint_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_password_hint.setVisibility(View.VISIBLE);
                btn_edit_password_hint.setVisibility(View.VISIBLE);
                et_password_hint.setVisibility(View.GONE);
                btn_edit_password_hint_complete.setVisibility(View.GONE);
                txt_password_hint.setText(et_password_hint.getText().toString());
            }
        });

        btn_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_password.setVisibility(View.GONE);
                btn_edit_password.setVisibility(View.GONE);
                et_password.setVisibility(View.VISIBLE);
                btn_edit_password_complete.setVisibility(View.VISIBLE);
                et_password.setText(diaryPw);
            }
        });
        btn_edit_password_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_password.setVisibility(View.VISIBLE);
                btn_edit_password.setVisibility(View.VISIBLE);
                et_password.setVisibility(View.GONE);
                btn_edit_password_complete.setVisibility(View.GONE);
                diaryPw = et_password.getText().toString();
                String tempPassword = "";
                for(int i = 0; i < diaryPw.length(); i++){
                    tempPassword += "*";
                }
                txt_password.setText(tempPassword);
            }
        });

        saveBtn_diary_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_password.getText().toString().length() == 0 || txt_password_hint.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this, "비밀번호나 힌트를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    new Thread(()->{

                        try{
                            String urlSetPw = "http://34.64.254.35/user/" + userId + "/pass-hint";
                            // request setPw
                            HttpPost postSetPw = new HttpPost();
                            JSONObject jsonSetPw = new JSONObject();
                            jsonSetPw.put("password",diaryPw);
                            jsonSetPw.put("hint",diaryPwHint);

                            // response setPw
                            String responseSetPw = postSetPw.post(urlSetPw,jsonSetPw.toString());
                            JSONObject jsonResponse = new JSONObject(responseSetPw);
                            int status = jsonResponse.getInt("status");
                            Log.e("JSONObject",jsonSetPw.toString());
                            Log.e("jsonResponse",jsonResponse.toString());
                            Log.e("jsonResponse",jsonResponse.toString());
                            if(status == 200 ){
                                alertDialog.dismiss();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "재시도", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e) {
                            Log.e("RuntimeException :", e.getMessage());
                            throw new RuntimeException(e);
                        }catch (IOException e){
                            Log.e("IOException : ", e.getMessage());
                        }


                    }).start();





                }
            }
        });
        cancelBtn_diary_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    /* ------------------------------------- feature : change_password_dailog ------------------------------------- */


    /* ------------------------------------- feature : log_out_dialog  ------------------------------------- */

    public void logOutDialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button cancelBtn_logout = dialogView.findViewById(R.id.cancelBtn_logout);
        Button saveBtn_logout = dialogView.findViewById(R.id.saveBtn_logout);


        cancelBtn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        saveBtn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOutIntent = new Intent(MainActivity.this, LoginActivity.class);
                alertDialog.dismiss();
                startActivity(logOutIntent);
                finish();
            }
        });

    }
    /* ------------------------------------- feature : log_out_dialog  ------------------------------------- */

}

