package com.fa7.todolist.view;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.fa7.todolist.R;
import com.fa7.todolist.control.GroupControl;
import com.fa7.todolist.controller.ActivityController;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;

import java.util.List;

public class ActivityView extends AppCompatActivity {

    Spinner cbx_groups, cbx_priority;
    EditText edt_activity_date, edt_description;
    RadioGroup rbg_status;
    RadioButton rb_pending, rb_sucess;
    private ActivityController activityController;
    private GroupController groupController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        InitializeComponents();

        List<String> list = groupController.getAll();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cbx_groups.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        cbx_groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                String grupo = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                Toast.makeText(getApplicationContext(), "Nome Selecionado: " + grupo, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void InitializeComponents() {
        cbx_groups = (Spinner) findViewById(R.id.cbx_groups);
        cbx_priority = (Spinner) findViewById(R.id.cbx_priority);
        edt_activity_date = (EditText) findViewById(R.id.edt_activity_date);
        edt_description = (EditText) findViewById(R.id.edt_description);
        rbg_status = (RadioGroup) findViewById(R.id.rbg_status);
        rb_pending = (RadioButton) findViewById(R.id.rb_pending);
        rb_sucess = (RadioButton) findViewById(R.id.rb_sucess);

        activityController = new ActivityController(this);
        groupController = new GroupController(this);
    }
}
