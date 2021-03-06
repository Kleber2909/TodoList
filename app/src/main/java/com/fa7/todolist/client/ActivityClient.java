package com.fa7.todolist.client;

import android.content.Context;
import android.util.Log;

import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.ActivityDAO;
import com.fa7.todolist.persistence.room.AppDatabase;
import java.util.List;

public class ActivityClient extends ClientBase {

    static FireBasePersistence fireBasePersistence;
    Context context;

    public ActivityClient(Context context) {
        this.context = context;
        db = AppDatabase.getInstance(context);
        fireBasePersistence = new FireBasePersistence(context);
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

    public List<ActivityDAO.ActivityAndGroup> loadAllByDate(String data, String status) {
        try {
            return db.getActivity().loadAllByDate(data, status);
        } catch (Exception e) {
            setMessage(e);
        }
        return null;
    }

    public void insertAll(Activity... activities) {
        try {
            db.getActivity().insertAll(activities);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void insert(Activity activity, Collaborator collaborator) {
        try {
            db.getActivity().insert(activity);
            fireBasePersistence.ActivityOnFirebase(activity, collaborator, true);

        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void insertSqLite(Activity activity) {
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

    public void deleteById(Activity activity, Collaborator collaborator) {
        try {
            db.getActivity().deleteById(activity.getId());
            fireBasePersistence.ActivityOnFirebase(activity, collaborator, false);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void update(Activity activity, Collaborator collaborator) {
        try {
            db.getActivity().update(activity);
            fireBasePersistence.ActivityOnFirebase(activity, collaborator, true);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void updateSqLite(Activity activity) {
        try {
            db.getActivity().update(activity);
        } catch (Exception e) {
            setMessage(e);
        }
    }

    public void updateStatus(long id, String status) {
        try {
            db.getActivity().updateStatus(id, status);
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
