package com.fa7.todolist.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.fa7.todolist.R;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.persistence.File.FileData;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText id;
    static EditText grupo;
    static FireBasePersistence fireBasePersistence;
    static GroupController groupControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id = findViewById(R.id.editText);
        //grupo = findViewById(R.id.editText2);
        fireBasePersistence = new FireBasePersistence(getApplicationContext());
        groupControl = new GroupController(this);

    }

    public void gravar(View view){
        new dbAsyncTask(getApplicationContext()).execute();
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        Context context;
        public dbAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                //AppDatabase appDatabase = MainDatabase.getInstance(context);


                //groupControl.JoinExistingGroup(Long.valueOf("1529460213972"));
                //groupControl.JoinExistingGroup(Long.valueOf("1529460213000"));
//                groupControl.GetSynchronizeFirebase();
                //groupControl.SincFirebase();
                //new CopiaBanco().CopiaBancoParaSD(context);

                //new FileData().saveText(context, "TodoListUserLocal", "gOm0i5RDgXQgXiLOAhj273VTdAc2|Kleber Cavalcante|kleber@teste.com.br");

                //Activity activity = new Activity("1529460213908", "Reunião projeto", "Realizar alinhamento das atividades de implantação", "20/06/2018", "Normal", "Pendente");

               // Collaborator collaborator = new CollaboratorController(context).GetUserLocal();
                //fireBasePersistence.ActivityOnFirebase(activity, collaborator, true);
                //fireBasePersistence.GetGroupOfFirebase();
                /*
                List<Group> groupList = appDatabase.groupDAO().getAll();

                Group grup = new Group(Long.valueOf("1529460213908"));
                appDatabase.groupDAO().insertAll(grup);
                fireBasePersistence.DataMyGroupOnFirebase(grup, true);

                List<Collaborator> collaboratorList = new ArrayList<Collaborator>();
                collaboratorList.add(new Collaborator("08WfY6ESUvO2ALxrjXCV0pWdi4O2", "Colaborador 1", "a@a.com.br"));
                collaboratorList.add(new Collaborator("WE76MfeGPMRXo4dcT36FZmlN1VE2", "Colaborador 2", "b@b.com.br"));

                List<Activity> activityList = new ArrayList<Activity>();
                activityList.add(new Activity( String.valueOf(grup.getId()), "Reunião projeto", "Realizar alinhamento das atividades de implantação", "20/06/2018", "Normal", "Pendente"));
                activityList.add(new Activity( String.valueOf(grup.getId()), "Trocar HD", "Realizar troca do HD do Notebook", "19/06/2018", "Normal", "Pendente"));
                activityList.add(new Activity( String.valueOf(grup.getId()), "Planilha de combustível", "Montar planilha de abastecimento conforme relatórios de visita", "18/06/2018", "Baixa", "Pendente"));

                grup.setActivityList(activityList);
                grup.setCollaboratorList(collaboratorList);

                fireBasePersistence.DataGroupOnFirebase(grup, true);

                collaboratorList = new ArrayList<Collaborator>();
                activityList = new ArrayList<Activity>();

                collaboratorList.add(new Collaborator("gOm0i5RDgXQgXiLOAhj273VTdAc2", "Colaborador 3", "c@c.com.br"));

                grup = new Group("Grupo 10");
                appDatabase.groupDAO().insertAll(grup);
                fireBasePersistence.DataMyGroupOnFirebase(grup, true);

                activityList.add(new Activity(String.valueOf(grup.getId()), "Pedal", "Pedal com equipe Pé de Pano", "20/06/2018", "Alta", "Pendente"));
                activityList.add(new Activity(String.valueOf(grup.getId()), "Lavar Bike", "Lavar Bike depois da trilha, lembrar de usar o limpa correte", "20/06/2018", "Normal", "Pendente"));

                grup.setActivityList(activityList);
                grup.setCollaboratorList(collaboratorList);

                fireBasePersistence.DataGroupOnFirebase(grup, true);

*/
                int a =0;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }
            return null;
        }
    }

    public void remover(View view){
        //new FireBasePersistence(getApplicationContext())
         //       .DataOnFirebase(new Group(id.getText().toString(), grupo.getText().toString()), "groups", id.getText().toString(), false);
    }
}
