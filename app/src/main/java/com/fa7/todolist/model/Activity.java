package com.fa7.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Activity {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "idGrupo")
    private String idGrupo;
    @ColumnInfo(name = "titulo")
    private String titulo;
    @ColumnInfo(name = "descricao")
    private String descricao;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "prioridade")
    private String prioridade;
    @ColumnInfo(name = "status")
    private String status;

    @Ignore
    public Activity(String idGrupo, String titulo, String descricao, String data, String prioridade, String status){
        this.id = new Date().getTime();
        this.idGrupo = idGrupo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.prioridade = prioridade;
        this.status = status;
    }

    public  Activity(){

    }

    @Override
    public String toString(){
        return this.descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
