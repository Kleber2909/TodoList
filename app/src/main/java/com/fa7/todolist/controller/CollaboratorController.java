package com.fa7.todolist.controller;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.fa7.todolist.client.CollaboratorClient;
import com.fa7.todolist.client.GroupCollaboratorClient;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.GroupCollaborator;
import com.fa7.todolist.persistence.File.FileData;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollaboratorController {

    Context context;
    static FireBasePersistence fireBasePersistence;
    static AppDatabase appDatabase;
    static CollaboratorClient collaboratorClient;
    static GroupCollaboratorClient gcClient;

    public CollaboratorController(Context context) {
        this.context = context;
        appDatabase = MainDatabase.getInstance(this.context);
        collaboratorClient = new CollaboratorClient(this.context);
        fireBasePersistence = new FireBasePersistence(this.context);
        gcClient = new GroupCollaboratorClient(this.context);
    }

    public List<Collaborator> GetAllCollaborators(long idGroup){
        List<Collaborator> list = new ArrayList<Collaborator>();

        for (GroupCollaborator gc: gcClient.getAllByGroup(idGroup))
            list.add(collaboratorClient.getCollaborator(gc.getIdColaborador()));

        return list;
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
            user.setImagePath(userCSV[4]);
        }
        catch (Exception e){
            Log.e("GetUserLocal", e.getMessage());
        }
        return user;
    }
}
