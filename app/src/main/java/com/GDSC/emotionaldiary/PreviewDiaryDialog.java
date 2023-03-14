package com.GDSC.emotionaldiary;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class PreviewDiaryDialog extends AppCompatActivity {
    Dialog dialog_enter_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_preview_diary_private);

        dialog_enter_password = new Dialog(PreviewDiaryDialog.this);
        dialog_enter_password.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_enter_password.setContentView(R.layout.dialog_enter_password);

        findViewById(R.id.complete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        dialog_enter_password.show();

        AppCompatButton cancel_btn = dialog_enter_password.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_enter_password.dismiss();
            }
        });
    }
}
