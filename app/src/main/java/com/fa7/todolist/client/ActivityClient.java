package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.persistence.room.AppDatabase;
import java.util.List;

public class ActivityClient extends ClientBase {

    public ActivityClient(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public Activity getActivity(long id) {
        try {
            return db.getActivity().getActivity(id);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public List<Activity> getAll() {
        try {
            return db.getActivity().getAll();
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public List<Activity> loadAllByIds(int[] activityId) {
        try {
            return db.getActivity().loadAllByIds(activityId);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public Activity findByName(String title) {
        try {
            return db.getActivity().findByName(title);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public List<Activity> getActivityByGroup(long idGroup) {
        try {
            return db.getActivity().getActivityByGroup(idGroup);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public List<Activity> getActivityByStatus(String status) {
        try {
            return db.getActivity().getActivityByStatus(status);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public void insertAll(List<Activity> activities) {
        try {
            db.getActivity().insertAll(activities);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void insert(Activity activity) {
        try {
            db.getActivity().insert(activity);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void delete(Activity activity) {
        try {
            db.getActivity().delete(activity);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void deleteAll() {
        try {
            db.getActivity().deleteAll();
        } catch (Exception e) {
            setMessage(e);
        }
    }

    @Override
    public void setMessage(Exception e) {
        Log.i("ActivityRepository: " + e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}
