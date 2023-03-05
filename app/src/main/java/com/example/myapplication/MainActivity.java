package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.time.Instant;
import java.time.ZoneId;


public class MainActivity extends AppCompatActivity {
    TextView textDiary;
    TextView textTodo;
    TextView title_recommended_music;
    TextView title_recommended_movie;
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
        refresh_recommended_music = findViewById(R.id.refresh_recommended_music); // recommended_music refresh Button

        recommended_movie = findViewById(R.id.recommended_movie); // recommended_music Layout
        img_recommended_movie = findViewById(R.id.img_recommended_movie); // recommended_music Image
        title_recommended_movie = findViewById(R.id.title_recommended_movie); // recommended_music Title
        refresh_recommended_movie = findViewById(R.id.refresh_recommended_movie); // recommended_music refresh Button

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
        recommended_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String musicTitle = "https://www.google.com/search?q=music+"+title_recommended_music.getText().toString();
                Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(musicTitle));
                startActivity(uriIntent);
            }
        });
        refresh_recommended_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"새로고침(music)",Toast.LENGTH_SHORT).show();
            }
        });


        recommended_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = "https://www.google.com/search?q=movie+"+title_recommended_movie.getText().toString();
                Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieTitle));
                startActivity(uriIntent);
            }
        });
        refresh_recommended_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"새로고침(movie)",Toast.LENGTH_SHORT).show();
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
