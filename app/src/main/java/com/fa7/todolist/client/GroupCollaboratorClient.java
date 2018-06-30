package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;
import com.fa7.todolist.model.GroupCollaborator;
import com.fa7.todolist.persistence.room.AppDatabase;
import java.util.List;

public class GroupCollaboratorClient extends ClientBase {

    public GroupCollaboratorClient(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<GroupCollaborator> getAll(){
        try {
            return db.getGroupCollaborator().getAll();
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }

    public List<GroupCollaborator> loadAllByIds(int[] groupCollaboratorIds) {
        try {
            return db.getGroupCollaborator().loadAllByIds(groupCollaboratorIds);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }

    public List<GroupCollaborator> getAllByCollaborator(String idCollaborator) {
        try {
            return db.getGroupCollaborator().getAllByCollaborator(idCollaborator);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }

    public List<GroupCollaborator> getAllByGroup(long idGroup) {
        try {
            return db.getGroupCollaborator().getAllByGroup(idGroup);
        } catch (Exception e) {
            setMessage(e);
        }

        return null;
    }

    public void deleteByCollaborator(int idCollaborator) {
        try {
            db.getGroupCollaborator().deleteByCollaborator(idCollaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void deleteByGroup(int idGroup) {
        try {
            db.getGroupCollaborator().deleteByGroup(idGroup);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void deleteAll() {
        try {
            db.getGroupCollaborator().deleteAll();
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void insertAll(GroupCollaborator... groupCollaborator) {
        try {
            db.getGroupCollaborator().insertAll(groupCollaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void delete(GroupCollaborator groupCollaborator) {
        try {
            db.getGroupCollaborator().delete(groupCollaborator);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    @Override
    public void setMessage(Exception e) {
        Log.i(e.getStackTrace()[0].getClassName() + " - " + e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}
