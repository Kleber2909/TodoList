package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Collaborator {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "idFarebase")
    private long idFarebase;
    @ColumnInfo(name = "nomeColaborador")
    private String nomeColaborador;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "typrLogin")
    private String typeLogin;

    @Ignore
    public Collaborator(String id, String nomeColaborador, String email) {
        this.idFarebase = new Date().getTime();
        this.id = id;
        this.nomeColaborador = nomeColaborador;
        this.email = email;
    }

    @Ignore
    public Collaborator(Long idFarebase, String id, String nomeColaborador, String email) {
        this.idFarebase = idFarebase;
        this.id = id;
        this.nomeColaborador = nomeColaborador;
        this.email = email;
    }

    @Ignore
    public Collaborator(String id, String nomeColaborador, String email, String typeLogin) {
        this.id = id;
        this.nomeColaborador = nomeColaborador;
        this.email = email;
        this.typeLogin = typeLogin;
    }

    public Collaborator() {
    }

    @Override
    public String toString() {
        return this.nomeColaborador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(String typeLogin) {
        this.typeLogin = typeLogin;
    }

    public long getIdFarebase() {
        return idFarebase;
    }

    public void setIdFarebase(long idFarebase) {
        this.idFarebase = idFarebase;
    }
}
