package com.GDSC.emotionaldiary;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerTodoItemActivity extends AppCompatActivity {
    private TodoItem_RecyclerViewAdapter mRecyclerAdapter;
    private ArrayList<TodoItem_Item> mtodoItems = new ArrayList();


    LinearLayout todo_category;
    TextView txt_todo_category;

    AppCompatImageView btn_check_todo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_todo_item);


        RecyclerView TodoRecyclerview = findViewById(R.id.recyclerView_TodoItem);

        mRecyclerAdapter = new TodoItem_RecyclerViewAdapter();
        TodoRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        TodoRecyclerview.setAdapter(mRecyclerAdapter);


        mtodoItems = new ArrayList<>();
        todo_category = (LinearLayout) findViewById(R.id.todo_category);
        txt_todo_category = findViewById(R.id.txt_todo_category);

        btn_check_todo = TodoRecyclerview.findViewById(R.id.btn_check_todo);


        mRecyclerAdapter.setmTodoList(mtodoItems);

    }

}
