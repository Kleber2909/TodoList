package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Collaborator {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "idColaborador")
    private String idColaborador;
    @ColumnInfo(name = "nomeColaborador")
    private String nomeColaborador;
    @ColumnInfo(name = "email")
    private String email;

    @Ignore
    public Collaborator(String idColaborador, String nomeColaborador, String email) {
        this.idColaborador = idColaborador;
        this.nomeColaborador = nomeColaborador;
        this.email = email;
    }

    public Collaborator() {
    }

    @Override
    public String toString() {
        return this.nomeColaborador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
