package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class TodoItem_RecyclerViewAdapter extends RecyclerView.Adapter<TodoItem_RecyclerViewAdapter.ViewHolder> {
    private ArrayList<TodoItem_Item> mTodoItemList;

    @NonNull
    @Override
    public TodoItem_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_todo_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItem_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.onBind(mTodoItemList.get(position));
    }

    public void setmTodoList(ArrayList<TodoItem_Item> list){
        this.mTodoItemList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mTodoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_content;
        EditText et_content;
        AppCompatImageView btn_check_todo;
        AppCompatImageView btn_edit_todo;
        AppCompatImageView btn_remove_todo;
        AppCompatImageView btn_edit_complete;
        boolean isChecked;
        TodoItem_Item item;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            isChecked = false;
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            btn_check_todo = (AppCompatImageView) itemView.findViewById(R.id.btn_check_todo);
            btn_edit_todo = (AppCompatImageView) itemView.findViewById(R.id.btn_edit_todo);
            btn_remove_todo = (AppCompatImageView) itemView.findViewById(R.id.btn_remove_todo);
            et_content = (EditText) itemView.findViewById(R.id.et_content);
            btn_edit_complete = (AppCompatImageView) itemView.findViewById(R.id.btn_edit_complete);
        }

        void onBind(TodoItem_Item item) {
            this.item = item;
            txt_content.setText(item.getTxt_content());

            btn_check_todo.setOnClickListener(this);
            txt_content.setOnClickListener(this);
            btn_edit_todo.setOnClickListener(this);
            btn_remove_todo.setOnClickListener(this);
            et_content.setOnClickListener(this);
            btn_edit_complete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            switch (v.getId()){
                case R.id.btn_check_todo:
                    v.setActivated(!v.isActivated());
                    isChecked = isChecked == false ? true : false;
                    Toast.makeText(v.getContext(), "btn_check_todo",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.txt_content:
                    Toast.makeText(v.getContext(), "txt_content",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_edit_todo: // todo_Edit 버튼
                    txt_content.setVisibility(View.GONE);
                    et_content.setVisibility(View.VISIBLE);
                    btn_edit_todo.setVisibility(View.GONE);
                    btn_edit_complete.setVisibility(View.VISIBLE);
                    et_content.setText(txt_content.getText().toString());
                    break;
                case R.id.btn_edit_complete: //todo_Edit 완료 버튼
                    if(et_content.getText().toString().length() != 0){
                        txt_content.setText(et_content.getText().toString());
                    }
                    et_content.setVisibility(View.GONE);
                    txt_content.setVisibility(View.VISIBLE);
                    btn_edit_todo.setVisibility(View.VISIBLE);
                    btn_edit_complete.setVisibility(View.GONE);
                    break;
                case R.id.btn_remove_todo:
                    mTodoItemList.remove(position);
                    notifyDataSetChanged();
                    break;
            }
        }

    }
}
