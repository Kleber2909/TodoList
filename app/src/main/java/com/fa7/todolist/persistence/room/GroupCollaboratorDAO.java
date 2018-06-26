package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.fa7.todolist.model.GroupCollaborator;

import java.util.List;

@Dao
public interface GroupCollaboratorDAO {

    @Query("SELECT * FROM 'GroupCollaborator'")
    List<GroupCollaborator> getAll();

    @Query("SELECT * FROM 'GroupCollaborator' WHERE id IN (:groupCollaboratorIds)")
    List<GroupCollaborator> loadAllByIds(int[] groupCollaboratorIds);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idColaborador IN (:idCollaborator)")
    List<GroupCollaborator> getAllByCollaborator(String idCollaborator);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idGrupo IN (:idGroup)")
    List<GroupCollaborator> getAllByGroup(long idGroup);

    @Query("SELECT * FROM 'GroupCollaborator' WHERE idGrupo IN (:groupId)")
    GroupCollaborator getCollaboratorInGroup(long groupId);

    @Query("DELETE FROM 'GroupCollaborator' WHERE idColaborador in (:idCollaborator)")
    void deleteByCollaborator(int idCollaborator);

    @Query("DELETE FROM 'GroupCollaborator'")
    void deleteAll();

    @Insert
    void insert(GroupCollaborator groupCollaborator);

    @Insert
    void insertAll(GroupCollaborator... groupCollaborator);

    @Delete
    void delete(GroupCollaborator groupCollaborator);

    @Query("DELETE FROM 'GroupCollaborator' WHERE idGrupo IN (:groupId)")
    void deleteByGroup(long groupId);

    @Update
    void update(GroupCollaborator groupCollaborator);
}
