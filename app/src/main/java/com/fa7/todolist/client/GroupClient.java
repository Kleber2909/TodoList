package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.room.AppDatabase;
import java.util.List;

public class GroupClient extends ClientBase {

    public GroupClient(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<Group> getAll() {
        try {
            return db.getGroup().getAll();
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public List<Group> loadAllByIds(int[] groupId) {
        try {
            db.getGroup().loadAllByIds(groupId);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public Group getGroup(long id) {
        try {
            db.getGroup().getGroup(id);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public Group findByName(String title) {
        try {
            db.getGroup().findByName(title);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public void insertAll(Group... groups) {
        try {
            db.getGroup().insertAll(groups);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void insert(Group group) {
        try {
            db.getGroup().insert(group);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void update(Group group) {
        try {
            db.getGroup().update(group);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void delete(Group group) {
        try {
            db.getGroup().delete(group);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void deleteAll() {
        try {
            db.getGroup().deleteAll();
        } catch (Exception e) {
            setMessage(e);
        }
    }

    @Override
    public void setMessage(Exception e) {
        Log.i(e.getStackTrace()[0].getClassName() + " - " + e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}