package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;


import java.time.LocalDate;
import java.util.Date;

import java.time.Instant;
import java.time.ZoneId;


public class MainActivity extends AppCompatActivity {
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
    boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btn_check_todo = findViewById(R.id.btn_check_todo); // todo_check button
        isChecked = false;


        btn_check_todo.setOnClickListener(new View.OnClickListener() { // todo_check Button
            @Override
            public void onClick(final View view) {
                view.setActivated(!view.isActivated());
                isChecked = isChecked == false ? true : false;
            }
        });



        /*---------------------------------------음악, 영화 추천 및 새로고침----------------------------------------------*/
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


        /* -------------------------------------CalendarView 속성------------------------------------- */

        calendar_todo.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months))); //
        calendar_todo.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays))); // 요일

        calendar_diary.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months))); //
        calendar_diary.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays))); // 요일


        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendar_todo.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        calendar_diary.setHeaderTextAppearance(R.style.CalendarWidgetHeader_Diary);


        // 일자 선택 시 정의한 드로어블이 적용
        calendar_todo.addDecorators(new DayDecorator(this));
        calendar_diary.addDecorators(new DayDecorator_Diary(this));


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
        /* -------------------------------------CalendarView 속성------------------------------------- */





    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 (Calendar_Todo) */
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


}
