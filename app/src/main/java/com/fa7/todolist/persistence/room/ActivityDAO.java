package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.fa7.todolist.model.Activity;

import java.util.List;

@Dao
public interface ActivityDAO {
    @Query("SELECT * FROM Activity")
    List<Activity> getAll();

    @Query("SELECT * FROM Activity WHERE id IN (:activityId)")
    List<Activity> loadAllByIds(int[] activityId);

    @Query("SELECT * FROM Activity WHERE id IN (:id)")
    Activity getActivity(int id);

    @Query("SELECT * FROM Activity WHERE titulo LIKE :title LIMIT 1")
    Activity findByName(String title);

    @Insert
    void insertAll(Activity... activity);

    @Delete
    void delete(Activity activity);

    @Query("DELETE FROM Activity")
    void deleteAll();
}
