package com.fa7.todolist.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fa7.todolist.client.CollaboratorClient;
import com.fa7.todolist.client.GroupClient;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.persistence.File.FileData;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.util.List;

public class CollaboratorController {

    Context context;
    //static FireBasePersistence fireBasePersistence;
    static AppDatabase appDatabase;
    static CollaboratorClient collaboratorClient;

    public CollaboratorController(Context context) {
        this.context = context;
        //fireBasePersistence = new FireBasePersistence(context);
        appDatabase = MainDatabase.getInstance(context);
        collaboratorClient = new CollaboratorClient(context);
    }

    public List<Collaborator> GetAllGroup(){
        return appDatabase.getCollaborator().getAll();
    }

    public boolean Remove(Collaborator collaborator) {
        try {
            if(collaborator != null) {
                collaboratorClient.delete(collaborator);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.i("RemoveCollaborator", e.getMessage());
            return false;
        }
    }

    public Collaborator GetUserLocal(){
        Collaborator user = new Collaborator();
        try {
            String[] userCSV = new FileData().getText(context, "TodoListUserLocal").split("\\|");
            user.setId(userCSV[0]);
            user.setNomeColaborador(userCSV[1]);
            user.setEmail(userCSV[2]);
            user.setTypeLogin(userCSV[3]);
        }
        catch (Exception e){
            Log.e("GetUserLocal", e.getMessage());
        }
        return user;
    }
}
