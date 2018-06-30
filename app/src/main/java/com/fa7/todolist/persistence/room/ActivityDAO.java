package com.fa7.todolist.persistence.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.Update;

import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Group;

import java.util.List;

@Dao
public interface ActivityDAO {
    @Query("SELECT * FROM Activity")
    List<Activity> getAll();

    @Query("SELECT * FROM Activity WHERE id IN (:activityId)")
    List<Activity> loadAllByIds(int[] activityId);

    @Query("SELECT A.*, B.nomeGrupo FROM Activity A INNER JOIN `GROUP` B ON A.idGrupo = B.id WHERE A.data = :date AND (A.status = :status OR :status = 'Todos')")
    List<ActivityAndGroup> loadAllByDate(String date, String status);

    @Query("SELECT * FROM Activity WHERE id IN (:id)")
    Activity getActivity(long id);

    @Query("SELECT * FROM Activity WHERE titulo LIKE :title LIMIT 1")
    Activity findByName(String title);

    @Query("SELECT * FROM Activity WHERE idGrupo = :idGroup")
    List<Activity> getActivityByGroup(long idGroup);

    @Query("SELECT * FROM Activity WHERE status = :status")

    List<Activity> getActivityByStatus(String status);

    @Insert
    void insertAll(Activity... activity);

    @Insert
    void insert(Activity activity);

    @Delete
    void delete(Activity activity);

    @Update
    void update(Activity activity);

    @Query("UPDATE Activity SET status = :status WHERE id = :id")
    void updateStatus(long id, String status);

    @Query("DELETE FROM Activity WHERE id = :id")
    void deleteById(long id);

    @Query("DELETE FROM Activity")
    void deleteAll();

    @Entity
    public class ActivityAndGroup {

        @Embedded
        public Activity oActivity;

        @ColumnInfo(name="nomeGrupo")
        public String nomeGrupo;

    }


}
