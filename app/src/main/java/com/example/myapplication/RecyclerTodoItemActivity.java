package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import androidx.appcompat.widget.AppCompatImageView;

public class RecyclerTodoItemActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TodoItem_RecyclerViewAdapter mRecyclerAdapter;
    private ArrayList<TodoItem_Item> mtodoItems = new ArrayList();
    private ArrayList<ArrayList<TodoItem_Item>> mtodoList;
    private AppCompatActivity btn_edit;

    LinearLayout todo_category;
    TextView txt_todo_category;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_todo_item);


        RecyclerView view = findViewById(R.id.recyclerView_TodoItem);

        mRecyclerAdapter = new TodoItem_RecyclerViewAdapter();
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(mRecyclerAdapter);


        mtodoItems = new ArrayList<>();
        todo_category = (LinearLayout) findViewById(R.id.todo_category);
        txt_todo_category = findViewById(R.id.txt_todo_category);


        todo_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_dialog(v);
            }
        });

        for(int i = 1; i < 10; i++){
            mtodoItems.add(new TodoItem_Item("gun" + i));
        }
        mRecyclerAdapter.setmTodoList(mtodoItems);

    }


    public void custom_dialog(View v) {
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


}
