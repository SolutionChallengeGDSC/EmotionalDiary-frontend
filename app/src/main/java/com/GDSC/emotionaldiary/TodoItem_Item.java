package com.GDSC.emotionaldiary;

public class TodoItem_Item {
    private String txt_content; // Todo_Item Content
    private boolean isCheked;

    public TodoItem_Item(String txt_todo_content){
        this.txt_content = txt_todo_content;
    }
    public String getTxt_content(){
        return txt_content;
    }

    public boolean isCheked() {
        return isCheked;
    }

    public void setCheked(boolean cheked) {
        isCheked = cheked;
    }
}
