package com.fa7.todolist.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GroupController extends ControllerBase {

    public GroupController(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
    }

    public List<String> getAll() {
        List<String> s = new ArrayList<String>();
        s.add("Item 1");
        s.add("Item 2");
        s.add("Item 3");
        return s;
    }
}
