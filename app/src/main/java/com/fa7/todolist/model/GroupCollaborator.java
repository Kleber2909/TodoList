package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GroupCollaborator {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "idGrupo")
    private String idGrupo;
    @ColumnInfo(name = "idColaborador")
    private String idColaborador;

    @Ignore
    public GroupCollaborator(String idGrupo, String idColaborador) {
        this.idGrupo = idGrupo;
        this.idColaborador = idColaborador;
    }

    public GroupCollaborator() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }
}
