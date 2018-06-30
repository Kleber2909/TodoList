package com.fa7.todolist.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public abstract class ControllerBase {

    Context context;
    AppCompatActivity appCompatActivity;

    public ControllerBase(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        this.context = this.appCompatActivity.getApplicationContext();
    }
}
