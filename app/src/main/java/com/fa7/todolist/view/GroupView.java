package com.fa7.todolist.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.fa7.todolist.R;

public class GroupView extends AppCompatActivity {

    public static LinearLayout lay_group;
    private RecyclerView rv_group;
    private FloatingActionButton btn_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups);
        InitializeComponents();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveActivity();
            }
        });
    }

    private void InitializeComponents() {
        lay_group = (LinearLayout) findViewById(R.id.lay_group);
        rv_group = (RecyclerView) findViewById(R.id.rv_group);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
    }

    private void SaveActivity() {

    }
}
