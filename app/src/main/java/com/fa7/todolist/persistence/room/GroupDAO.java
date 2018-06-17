package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fa7.todolist.model.Group;

import java.util.List;

@Dao
public interface GroupDAO {
    @Query("SELECT * FROM 'Group'")
    List<Group> getAll();

    @Query("SELECT * FROM 'Group' WHERE id IN (:groupId)")
    List<Group> loadAllByIds(int[] groupId);

    @Query("SELECT * FROM 'Group' WHERE id IN (:id)")
    Group getGroup(int id);

    @Query("SELECT * FROM 'Group' WHERE nomeGrupo LIKE :title LIMIT 1")
    Group findByName(String title);

    @Insert
    void insertAll(Group... group);

    @Delete
    void delete(Group group);

    @Query("DELETE FROM 'Group'")
    void deleteAll();
}
