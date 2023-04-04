package com.GDSC.emotionaldiary;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
        TodoItem_Item item;
        private boolean isChecked; // todoItem isChecked
        private int itemId;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

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
            isChecked = item.isCheked();
            itemId = item.getId();
            if(item.isCheked()){
                btn_check_todo.setImageResource(R.drawable.ic_check_todo);
            }
            else {
                btn_check_todo.setImageResource(R.drawable.ic_check_not_todo);
            }
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
                    item.setCheked(isChecked = isChecked == false ? true : false); // id 로 보내야겠지
                    new Thread(()->{
                        // TodoItem success 상태 변경
                        try{
                            String urlChangeSuccess = "http://34.64.254.35/todo/success/"+itemId;
                            HttpClient changeSuccess = new HttpClient();
                            String responseChangeSuccess = changeSuccess.put(urlChangeSuccess,"");
                            JSONObject jsonResponse = new JSONObject(responseChangeSuccess);
                            Log.e("jsonResponse",jsonResponse.toString());

                        }catch (JSONException e) {
                            Log.e("RuntimeException :", e.getMessage());
                            throw new RuntimeException(e);
                        }catch (IOException e){
                            Log.e("IOException : ", e.getMessage());
                        }

                    }).start();

                    if(item.isCheked()){
                        btn_check_todo.setImageResource(R.drawable.ic_check_todo);
                    }
                    else {
                        btn_check_todo.setImageResource(R.drawable.ic_check_not_todo);
                    }
                    break;
                case R.id.txt_content:
                    Toast.makeText(v.getContext(), txt_content.getText().toString(),Toast.LENGTH_SHORT).show();
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
                    new Thread(()->{
                        // TodoItem Goal 상태 변경
                        try{
                            String urlChangeGoal = "http://34.64.254.35/todo/goal/"+itemId;
                            HttpClient changeGoal = new HttpClient();
                            JSONObject jsonChangeGoal = new JSONObject();
                            jsonChangeGoal.put("goal",et_content.getText().toString());
                            String responseChangeGoal = changeGoal.put(urlChangeGoal,jsonChangeGoal.toString());
                            JSONObject jsonResponse = new JSONObject(responseChangeGoal);
                            Log.e("jsonResponse",jsonResponse.toString());

                        }catch (JSONException e) {
                            Log.e("RuntimeException :", e.getMessage());
                            throw new RuntimeException(e);
                        }catch (IOException e){
                            Log.e("IOException : ", e.getMessage());
                        }

                    }).start();
                    et_content.setVisibility(View.GONE);
                    txt_content.setVisibility(View.VISIBLE);
                    btn_edit_todo.setVisibility(View.VISIBLE);
                    btn_edit_complete.setVisibility(View.GONE);
                    break;
                case R.id.btn_remove_todo:
                    mTodoItemList.remove(position);
                    new Thread(()->{
                        // TodoItem delete
                        try{
                            String urlDeleteTodo = "http://34.64.254.35/todo/"+itemId;
                            HttpClient deleteTodo = new HttpClient();
                            String responseDeleteTodo = deleteTodo.delete(urlDeleteTodo,"");
                            JSONObject jsonResponse = new JSONObject(responseDeleteTodo);
                            Log.e("jsonResponse",jsonResponse.toString());
                        }catch (JSONException e) {
                            Log.e("RuntimeException :", e.getMessage());
                            throw new RuntimeException(e);
                        }catch (IOException e){
                            Log.e("IOException : ", e.getMessage());
                        }
                    }).start();
                    notifyDataSetChanged();
                    break;
            }
        }

    }
}

