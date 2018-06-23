package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.fa7.todolist.model.Activity;
import java.util.List;

@Dao
public interface ActivityDAO {
    @Query("SELECT * FROM Activity")
    List<Activity> getAll();

    @Query("SELECT * FROM Activity WHERE id IN (:activityId)")
    List<Activity> loadAllByIds(int[] activityId);

    @Query("SELECT * FROM Activity WHERE id IN (:id)")
    Activity getActivity(long id);

    @Query("SELECT * FROM Activity WHERE titulo LIKE :title LIMIT 1")
    Activity findByName(String title);

    @Query("SELECT * FROM Activity WHERE idGrupo = :idGroup")
    List<Activity> getActivityByGroup(long idGroup);

    @Query("SELECT * FROM Activity WHERE status = :status")

    List<Activity> getActivityByStatus(String status);

    @Insert
    void insertAll(List<Activity> activity);

    @Insert
    void insert(Activity activity);

    @Delete
    void delete(Activity activity);

    @Update
    void update(Activity activity);

    @Query("DELETE FROM Activity")
    void deleteAll();
}
