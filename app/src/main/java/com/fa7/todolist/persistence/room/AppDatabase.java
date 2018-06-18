package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.model.GroupCollaborator;

@Database(entities = {Activity.class, Group.class, Collaborator.class, GroupCollaborator.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "todo-list-db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).build();
    }

    public abstract ActivityDAO getActivity();
    public abstract GroupDAO getGroup();
    public abstract CollaboratorDAO getCollaborator();
    public abstract GroupCollaboratorDAO getGroupCollaborator();

}
