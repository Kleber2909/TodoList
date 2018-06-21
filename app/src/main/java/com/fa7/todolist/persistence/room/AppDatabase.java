package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.model.GroupCollaborator;

@Database(entities = {Activity.class, Group.class, Collaborator.class, GroupCollaborator.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroupDAO groupDAO();
    public abstract ActivityDAO activityDAO();
    public abstract CollaboratorDAO collaboratorDAO();
    public abstract GroupCollaboratorDAO groupCollaboratorDAO();
}
