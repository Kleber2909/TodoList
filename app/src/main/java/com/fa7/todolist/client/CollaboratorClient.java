package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;

import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.persistence.File.FileData;
import com.fa7.todolist.persistence.room.AppDatabase;

import java.util.List;

public class CollaboratorClient extends ClientBase {

    Context context;
    public CollaboratorClient(Context context) {
        this.context = context;
        db = AppDatabase.getInstance(context);

    }

    public List<Collaborator> getAll() {
        try {
            return db.getCollaborator().getAll();
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }


    public List<Collaborator> loadAllByIds(String[] collaboratorIds) {
        try {
            return db.getCollaborator().loadAllByIds(collaboratorIds);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }


    public Collaborator getCollaborator(String id) {
        try {
            return db.getCollaborator().getCollaborator(id);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }


    public Collaborator findByName(String name) {
        try {
            return db.getCollaborator().findByName(name);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }

    public void insert(Collaborator collaborator) {
        try {
            db.getCollaborator().insert(collaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void update(Collaborator collaborator) {
        try {
            db.getCollaborator().update(collaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void delete(Collaborator collaborator) {
        try {
            db.getCollaborator().delete(collaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void deleteAll() {
        try {
            db.getCollaborator().deleteAll();
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void AddLocalUser(Collaborator collaborator){
        if(getCollaborator(collaborator.getId()) == null)
            insert(collaborator);
        else
            update(collaborator);

        new FileData().saveText(context,
                "TodoListUserLocal",
                collaborator.getId()+"|"+collaborator.getNomeColaborador()+"|"+collaborator.getEmail()+"|G|"+collaborator.getImagePath()+"|"+collaborator.getIdFarebase());
    }

    public void DelLocalUser(){
        new FileData().delText(context);
    }

    @Override
    public void setMessage(Exception e) {
        Log.i(e.getStackTrace()[0].getClassName() + " - " + e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}
