package com.fa7.todolist.view;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.fa7.todolist.R;
import com.fa7.todolist.controller.ActivityController;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.utils.SnackbarMessage;

import java.util.List;

public class ActivityView extends AppCompatActivity {

    Spinner cbx_groups, cbx_priority;
    EditText edt_activity_date, edt_description, edt_title;
    RadioGroup rbg_status;
    RadioButton rb_pending, rb_sucess;
    FloatingActionButton btn_save;
    public static LinearLayout lay_activity;
    public static ActivityController activityController;
    private GroupController groupController;
    private String prioridade;
    static Collaborator userLoca;
    List<Group> groups;
    Group grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        InitializeComponents();
        InitializeSpinners();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveActivity();
            }
        });

        //cbx_groups.setSelection();
    }

    private void InitializeComponents() {
        lay_activity = (LinearLayout) findViewById(R.id.lay_activity);
        cbx_groups = (Spinner) findViewById(R.id.cbx_groups);
        cbx_priority = (Spinner) findViewById(R.id.cbx_priority);
        edt_activity_date = (EditText) findViewById(R.id.edt_activity_date);
        edt_description = (EditText) findViewById(R.id.edt_description);
        edt_title = (EditText) findViewById(R.id.edt_title);
        rbg_status = (RadioGroup) findViewById(R.id.rbg_status);
        rb_pending = (RadioButton) findViewById(R.id.rb_pending);
        rb_sucess = (RadioButton) findViewById(R.id.rb_sucess);
        btn_save = (FloatingActionButton) findViewById(R.id.btn_save);
        userLoca = new CollaboratorController(getBaseContext()).GetUserLocal();
        activityController = new ActivityController(this);
        groupController = new GroupController(this);
    }

    private void InitializeSpinners() {

        // groups

        new GetInitializeSpinners(getApplicationContext()).execute();
        // groups
        List<String> priorities = activityController.ListaPrioridades();
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, priorities);
        cbx_priority.setAdapter(priorityAdapter);
        cbx_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                prioridade = parent.getItemAtPosition(posicao).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void SaveActivity(){
        if(grupo.getId() == 0) {
            SnackbarMessage.setProgressMessage(lay_activity, false, ": Selecione o grupo.");
            return;
        }

        if(edt_title.getText().toString().equals("")) {
            SnackbarMessage.setProgressMessage(lay_activity, false, ": Informe um título.");
            return;
        }

        if(edt_activity_date.getText().toString().equals("")) {
            SnackbarMessage.setProgressMessage(lay_activity, false, ": Informe uma data.");
            return;
        }

        if(rbg_status.getCheckedRadioButtonId() == -1) {
            SnackbarMessage.setProgressMessage(lay_activity, false, ": Selecione um status.");
            return;
        }

        String title = edt_title.getText().toString();
        String description = edt_description.getText().toString();
        String data = edt_activity_date.getText().toString();
        String status = ((RadioButton) findViewById(rbg_status.getCheckedRadioButtonId())).getText().toString();

        Activity activity = new Activity(Long.toString(grupo.getId()), title, description, data, prioridade, status);
        new SaveActivity(getApplicationContext(), activity).execute();
    }

    private class SaveActivity extends AsyncTask<Activity, Void, Boolean> {
        Context context;
        Activity activity;
        boolean progress;
        public SaveActivity(Context context, Activity activity){
            this.context = context;
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Activity... params) {
            try {
                progress = activityController.AddNewActivity(activity, userLoca);
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
            SnackbarMessage.setProgressMessage(lay_activity, progress, " ao Adicionar Atividade.");
        }
    }

    private class GetInitializeSpinners extends AsyncTask<Void, Void, Boolean> {
        Context context;
        boolean progress;
        public GetInitializeSpinners(Context context){
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                groups = groupController.GetAllGroup();
                ArrayAdapter<Group> groupAdapter = new ArrayAdapter<Group>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, groups);
                cbx_groups.setAdapter(groupAdapter);
                cbx_groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                        //grupo = parent.getItemAtPosition(posicao).toString();
                        grupo = groups.get(posicao);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
            SnackbarMessage.setProgressMessage(lay_activity, progress, " ao listar Grupos.");
        }
    }
}
