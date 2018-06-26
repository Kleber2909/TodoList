package com.fa7.todolist.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.persistence.File.FileData;

public class CollaboratorController {

//    public CollaboratorController(AppCompatActivity appCompatActivity) {
//        super(appCompatActivity);
//        context = appCompatActivity.getBaseContext();
//    }
    Context context;
    public CollaboratorController(Context context) {
        this.context = context;
    }

    public Collaborator GetUserLocal(){
        Collaborator user = new Collaborator();
        try {
            String[] userCSV = new FileData().getText(context, "TodoListUserLocal").split("\\|");
            user.setId(userCSV[0]);
            user.setNomeColaborador(userCSV[1]);
            user.setEmail(userCSV[2]);
        }
        catch (Exception e){
            Log.e("GetUserLocal", e.getMessage());
        }
        return user;
    }
}
