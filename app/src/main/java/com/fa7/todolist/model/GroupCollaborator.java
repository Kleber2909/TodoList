package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GroupCollaborator {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "idGrupo")
    private long idGrupo;
    @ColumnInfo(name = "idColaborador")
    private String idColaborador;

    @Ignore
    public GroupCollaborator(long idGrupo, String idColaborador) {
        this.idGrupo = idGrupo;
        this.idColaborador = idColaborador;
    }

    public GroupCollaborator() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }
}
