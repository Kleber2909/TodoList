package com.fa7.todolist.view;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.fa7.todolist.R;
import com.fa7.todolist.client.ActivityClient;
import com.fa7.todolist.controller.ActivityController;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.utils.SnackbarMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityView extends AppCompatActivity {

    Spinner cbx_groups, cbx_priority;
    EditText edt_activity_date, edt_description, edt_title;
    RadioGroup rbg_status;
    RadioButton rb_pending, rb_sucess;
    FloatingActionButton btn_save;
    public static LinearLayout lay_activity;
    public static ActivityController activityController;
    ActivityClient activityClient;
    private GroupController groupController;
    private String prioridade;
    static Collaborator userLoca;
    List<Group> groups;
    Group grupo;
    Activity activity;
    Long key;
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

        edt_activity_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ActivityView.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        edt_activity_date.setText(sdf.format(myCalendar.getTime()));

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecionar Data");
                mDatePicker.show();  }
        });

        Long b = getIntent().getExtras().getLong("key");
        if(b != 0)
        {
            this.setTitle("Atualizar Atividade");
        }else{
            this.setTitle("Adicionar Atividade");
        }

        if(getIntent().getExtras().containsKey("Data")){
            edt_activity_date.setText(getIntent().getExtras().getString("Data"));
        }

        Intent intent = getIntent();
        if(intent.getStringExtra("key") != null) {
            key = Long.parseLong(getIntent().getExtras().getString("key"));
            new GetActivitData(this, intent.getStringExtra("key")).execute();
        }
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
        activityClient = new ActivityClient(this);
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
        try {
            if (grupo.getId() == 0) {
                SnackbarMessage.setProgressMessage(lay_activity, false, ": Selecione o grupo.");
                return;
            }

            if (edt_title.getText().toString().equals("")) {
                SnackbarMessage.setProgressMessage(lay_activity, false, ": Informe um título.");
                return;
            }

            if (edt_activity_date.getText().toString().equals("")) {
                SnackbarMessage.setProgressMessage(lay_activity, false, ": Informe uma data.");
                return;
            }

            if (rbg_status.getCheckedRadioButtonId() == -1) {
                SnackbarMessage.setProgressMessage(lay_activity, false, ": Selecione um status.");
                return;
            }

            String title = edt_title.getText().toString();
            String description = edt_description.getText().toString();
            String data = edt_activity_date.getText().toString();
            String status = ((RadioButton) findViewById(rbg_status.getCheckedRadioButtonId())).getText().toString();
            Activity activity;
            if(key == null)
                activity = new Activity(Long.toString(grupo.getId()), title, description, data, prioridade, status);
            else
                activity = new Activity(key, Long.toString(grupo.getId()), title, description, data, prioridade, status);

            new SaveActivity(getApplicationContext(), activity).execute();
        }
        catch (Exception e){
            Log.e("Erro", e.getMessage());
        }
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
                if(key != null)
                {
                    progress = activityController.AddNewActivity(activity, userLoca, false);
                }else{
                    progress = activityController.AddNewActivity(activity, userLoca, true);
                }
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
            if(key == null)
                SnackbarMessage.setProgressMessage(lay_activity, progress, " ao Adicionar Atividade.");
            else
                SnackbarMessage.setProgressMessage(lay_activity, progress, " ao Atualizar Atividade.");
            onBackPressed();
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
        }
    }

    private class GetActivitData extends AsyncTask<Void, Void, Boolean> {
        Context context;
        String key;

        public GetActivitData(Context context, String key){
            this.context = context;
            this.key = key;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                activity = activityClient.getActivity(Long.valueOf(key));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < groups.size(); i++) {
                                if (groups.get(i).getId() == (Long.parseLong(activity.getIdGrupo())))
                                    cbx_groups.setSelection(i);
                            }
                            switch (activity.getPrioridade()) {
                                case "Alta":
                                    cbx_priority.setSelection(0);
                                    break;
                                case "Média":
                                    cbx_priority.setSelection(1);
                                    break;
                                case "Baixa":
                                    cbx_priority.setSelection(2);
                                    break;
                            }
                            edt_activity_date.setText(activity.getData());
                            edt_description.setText(activity.getDescricao());
                            edt_title.setText(activity.getTitulo());
                            if (activity.getStatus().equals("Pendente"))
                                rb_pending.setChecked(true);
                            else
                                rb_sucess.setChecked(true);
                        } catch (Exception e) {
                            Log.e("Erro", e.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
        }
    }

}
