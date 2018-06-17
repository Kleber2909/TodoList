package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MainDatabase {
    private static AppDatabase appDatabase;
    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, "todo-list-db").build();
        }

        return appDatabase;
    }
}
