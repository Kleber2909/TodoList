package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;

import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.persistence.room.AppDatabase;

import java.util.List;

public class CollaboratorClient extends ClientBase {

    public CollaboratorClient(Context context) {
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


    public List<Collaborator> loadAllByIds(int[] collaboratorIds) {
        try {
            return db.getCollaborator().loadAllByIds(collaboratorIds);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }


    public Collaborator getCollaborator(int id) {
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


    public void insertAll(Collaborator... collaborators) {
        try {
            db.getCollaborator().insertAll(collaborators);
        } catch (Exception e) {
            setMessage(e);
        }
    }


    public void insert(Collaborator collaborator) {
        try {
            db.getCollaborator().insert(collaborator);
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

    @Override
    public void setMessage(Exception e) {
        Log.i(e.getStackTrace()[0].getClassName() + " - " + e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}
