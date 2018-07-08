package com.fa7.todolist.controller;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.fa7.todolist.client.ActivityClient;
import com.fa7.todolist.client.GroupClient;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;

import java.util.ArrayList;
import java.util.List;

public class ActivityController extends ControllerBase {

    static FireBasePersistence fireBasePersistence;
    static ActivityClient client;
    Activity activity;

    public ActivityController(AppCompatActivity appCompatActivity){
        super(appCompatActivity);
        fireBasePersistence = new FireBasePersistence(context);
        client = new ActivityClient(context);
    }

    // Chamar esse método para adicionar uma atividade
    public boolean AddNewActivity(Activity activity, Collaborator collaborator, Boolean add){
        try {
            if(activity != null) {
                if(activity.getIdGrupo().equals("")) {
                    Log.i("Erro: AddNewActivity", "Selecione o grupo.");
                    return false;
                }

                if (activity.getTitulo().equals("")) {
                    Log.i("Erro: AddNewActivity", "Selecione o grupo.");
                    return false;
                }

                if(activity.getData().equals("")){
                    Log.i("Erro: AddNewActivity", "Selecione o grupo.");
                    return false;
                }

                if(add)
                    client.insert(activity, collaborator);
                else
                    client.update(activity, collaborator);
                return true;
            } else {
                Log.i("Erro: AddNewActivity", "Atividade nula.");
                return false;
            }

        } catch (Exception ex) {
            Log.i("Erro: AddNewActivity", ex.getMessage());
        }
        return false;
    }

    public void RemoveActivity(long activityId){
        try {
            activity = client.getActivity(activityId);
            client.delete(activity);
        } catch (Exception ex) {
            Log.i("Erro: AddNewActivity", ex.getMessage());
        }
    }

    public List<String> ListaPrioridades() {
        List<String> priorits = new ArrayList<String>();
        priorits.add("Alta");
        priorits.add("Média");
        priorits.add("Baixa");
        return priorits;
    }

    // Chamar esse método para recuperar as atividades do Firebase e sincronizar com o SqLite
    public void GetSynchronizeActivitysFirebase(){
        List<Group> groupList = new GroupClient(context).getAll();
        for (int i = 0; i < groupList.size(); i++)
            fireBasePersistence.GetActivitys(groupList.get(i));
    }

    // Método chamado após o retorno do evento onDataChange do método GetActivitys da classe fireBasePersistence
    public static void SetMyActivitys(List<Activity> activityList){
        new dbAsyncTask(activityList).execute();
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        List<Activity> activityList;

        public dbAsyncTask(List<Activity> activityList) {
            this.activityList = activityList;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Navega na lista de ativiades do grupo e verifica se já existe no SqLite para Atualizar ou Adicionar
                for (int i = 0; i < activityList.size(); i++) {
                    if (client.getActivity(activityList.get(i).getId()) == null)
                        client.insertSqLite(activityList.get(i));
                    else
                        client.updateSqLite(activityList.get(i));
                }
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void param) {
            try {
                super.onPostExecute(param);
                // SincFirebase();
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }
}
