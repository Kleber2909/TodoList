package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fa7.todolist.model.GroupCollaborator;

import java.util.List;

@Dao
public interface GroupCollaboratorDAO {

    @Query("SELECT * FROM 'GroupCollaborator'")
    List<GroupCollaborator> getAll();

    @Query("SELECT * FROM 'GroupCollaborator' WHERE id IN (:groupCollaboratorId)")
    List<GroupCollaborator> loadAllByIds(int[] groupCollaboratorId);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE id IN (:id)")
    GroupCollaborator getCollaborator(int id);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idColaborador LIKE :title LIMIT 1")
    GroupCollaborator findByName(String title);

    @Insert
    void insertAll(GroupCollaborator... groupCollaborator);

    @Delete
    void delete(GroupCollaborator groupCollaborator);

    @Query("DELETE FROM 'GroupCollaborator'")
    void deleteAll();
}
