package com.GDSC.emotionaldiary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

public class EmotionalScoreDialog extends BottomSheetDialogFragment {
    private View view;
    private TextView score;
    private Slider slider;
    private AppCompatButton complete_btn;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_emotional_score, container, false);

        // slider 리스너
        Slider.OnSliderTouchListener sliderTouchListener = new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(Slider slider) {
                // 바가 시작하면 동작하는 부분
            }

            @Override
            public void onStopTrackingTouch(Slider slider) {
                // 유저가 바에서 손을 뗐을 때 동작하는 함수
                String val = Integer.toString((int) slider.getValue());
                score.setText(val);
            }
        };
        score = view.findViewById(R.id.score);
        slider = view.findViewById(R.id.slider);
        slider.addOnSliderTouchListener(sliderTouchListener);

        // 완료 버튼 (감정 점수 다이얼로그 없애기)
        complete_btn = view.findViewById(R.id.complete_btn);
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}