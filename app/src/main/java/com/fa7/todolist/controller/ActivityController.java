package com.fa7.todolist.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fa7.todolist.client.ActivityClient;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;

import java.util.ArrayList;
import java.util.List;

public class ActivityController extends ControllerBase {

    ActivityClient client;
    Activity activity;

    public ActivityController(AppCompatActivity appCompatActivity){
        super(appCompatActivity);



        client = new ActivityClient(context);
    }

    // Chamar esse método para entrar em grupo já existente
    public boolean AddNewActivity(Activity activity){
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

                client.insert(activity);
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



    // Chamar esse método para sincronizar os grupos de atividades do Firebase com o SqLite
    public void GetSynchronizeFirebase(){

    }

    // Método chamado após o retorno do evento onDataChange do método GetGroupOfFirebase da classe fireBasePersistence
    public static void GetMyGroups(List<Group> groupList){

    }

    // Método chamado para receber do onDataChange do método GetActivitysOfFirebase as atividades e colaboradores dos grupos e enviar para os objetos de persistencia do SqLite
    public static void UpdateSqLite(Group groupList){
        int a = 1;
    }
}
