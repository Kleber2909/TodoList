package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
public class Group {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "nomeGrupo")
    private String nomeGrupo;
    @Ignore
    private List<Collaborator> collaboratorList;
    @Ignore
    private List<Activity> activityList;
    @Ignore
    private Collaborator collaborator;
    @Ignore
    private Activity activity;

    public Group() {
    }

    @Ignore
    public Group(String nomeGrupo) {
        this.id = new Date().getTime();
        this.nomeGrupo = nomeGrupo;
    }

    @Ignore
    public Group(long id, String nomeGrupo) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
    }

    @Ignore
    public Group(long id) {
        this.id = id;
    }

    @Ignore
    public Group(String nomeGrupo, List<Collaborator> collaboratorList, List<Activity> activityList) {
        this.id = new Date().getTime();
        this.nomeGrupo = nomeGrupo;
        this.collaboratorList = collaboratorList;
        this.activityList = activityList;
    }

    @Ignore
    public Group(long id, String nomeGrupo, List<Collaborator> collaboratorList, List<Activity> activityList) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.collaboratorList = collaboratorList;
        this.activityList = activityList;
    }

    @Ignore
    public Group(long id, String nomeGrupo, Collaborator collaborator, Activity activity) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.collaborator = collaborator;
        this.activity = activity;
    }



    @Override
    public String toString() {
        return this.nomeGrupo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public List<Collaborator> getCollaboratorList() {
        return collaboratorList;
    }

    public void setCollaboratorList(List<Collaborator> collaboratorList) {
        this.collaboratorList = collaboratorList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
