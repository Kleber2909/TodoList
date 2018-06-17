package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fa7.todolist.model.Collaborator;

import java.util.List;

@Dao
public interface CollaboratorDAO {

    @Query("SELECT * FROM 'Collaborator'")
    List<Collaborator> getAll();

    @Query("SELECT * FROM 'Collaborator' WHERE id IN (:collaboratorId)")
    List<Collaborator> loadAllByIds(int[] collaboratorId);

    @Query("SELECT * FROM 'Collaborator' WHERE id IN (:id)")
    Collaborator getCollaborator(int id);

    @Query("SELECT * FROM 'Collaborator' WHERE nomeColaborador LIKE :title LIMIT 1")
    Collaborator findByName(String title);

    @Insert
    void insertAll(Collaborator... collaborator);

    @Delete
    void delete(Collaborator collaborator);

    @Query("DELETE FROM 'Collaborator'")
    void deleteAll();

}
