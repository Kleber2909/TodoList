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

    @Query("SELECT * FROM 'GroupCollaborator' WHERE id IN (:groupCollaboratorIds)")
    List<GroupCollaborator> loadAllByIds(int[] groupCollaboratorIds);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idColaborador IN (:idCollaborator)")
    List<GroupCollaborator> getAllByCollaborator(int idCollaborator);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idColaborador IN (:idGroup)")
    List<GroupCollaborator> getAllByGroup(int idGroup);

    @Query("DELETE FROM 'GroupCollaborator' WHERE idColaborador in (:idCollaborator)")
    void deleteByCollaborator(int idCollaborator);

    @Query("DELETE FROM 'GroupCollaborator' WHERE idColaborador in (:idGroup)")
    void deleteByGroup(int idGroup);

    @Query("DELETE FROM 'GroupCollaborator'")
    void deleteAll();

    @Insert
    void insertAll(GroupCollaborator... groupCollaborator);

    @Delete
    void delete(GroupCollaborator groupCollaborator);
}
