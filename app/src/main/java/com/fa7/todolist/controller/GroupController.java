package com.fa7.todolist.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fa7.todolist.client.GroupClient;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.model.GroupCollaborator;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.util.ArrayList;
import java.util.List;

public class GroupController  {

    Context context;
    static FireBasePersistence fireBasePersistence;
    static AppDatabase appDatabase;
    static GroupClient client;


    public GroupController(Context context) {
        this.context = context;
        fireBasePersistence = new FireBasePersistence(context);
        appDatabase = MainDatabase.getInstance(context);
        client = new GroupClient(context);
    }

    // Chamar esse método para entrar em grupo já existente
    public void JoinExistingGroup(long id, boolean action) {
        fireBasePersistence.MyGroupOnFirebase(new Group(id), action);
    }

    // Chamar esse método para sincronizar os grupos de atividades do Firebase com o SqLite
    public void GetSynchronizeFirebase() {
        fireBasePersistence.GetMyGroupOfFirebase();
    }

    // Método chamado após o retorno do evento onDataChange do método GetMyGroupOfFirebase da classe fireBasePersistence para atualizar os grupos no SqLite
    public static void GetGroups(List<Group> groupList) {
        for (int i = 0; i < groupList.size(); i++)
            fireBasePersistence.GetGroupsOfFirebase(groupList.get(i));
    }

    // Método chamado para receber do onDataChange do método GetGroupOfFirebase as atividades e colaboradores dos grupos e enviar para os objetos de persistencia do SqLite
    public static void UpdateSqLite(Group group) {
        new dbAsyncTask(group).execute();
    }

    public List<Group> GetAllGroup(){
        return appDatabase.getGroup().getAll();
    }

    public List<String> GetAllGroupName(){
        return appDatabase.getGroup().getAllGroupName();
    }

    public boolean AddNewGroup(Group group) {
        try {
            if(group != null) {
                if(group.getNomeGrupo().equals("")) {
                    Log.i("Erro: AddNewGroup", "Informe o nome do grupo.");
                    return false;
                }

                client.insert(group);
                fireBasePersistence.GroupOnFirebase(group, true);
                return true;
            } else {
                Log.i("Erro: AddNewGroup", "Grupo está nulo.");
                return false;
            }

        } catch (Exception ex) {
            Log.i("Erro: AddNewGroup", ex.getMessage());
        }
        return false;
    }

    public boolean UpdateGroupName(Group group) {
        try {
            if(group != null) {
                if(group.getNomeGrupo().equals("")) {
                    Log.i("Erro: UpdateGroupName", "Informe o nome do grupo.");
                    return false;
                }

                client.update(group);
                fireBasePersistence.GroupOnFirebase(group, true);
                return true;
            } else {
                Log.i("Erro: UpdateGroupName", "Grupo nula.");
                return false;
            }

        } catch (Exception ex) {
            Log.i("Erro: UpdateGroupName", ex.getMessage());
        }
        return false;
    }

    public boolean RemoveGroup(Group group) {
        try {
            if(group != null) {
                client.delete(group);
                fireBasePersistence.GroupOnFirebase(group, false);
                return true;
            } else {
                Log.i("Erro: RemoveGroup", "Grupo nulo.");
                return false;
            }

        } catch (Exception ex) {
            Log.i("Erro: AddNewActivity", ex.getMessage());
        }
        return false;
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        Group group;

        public dbAsyncTask(Group group) {
            this.group = group;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                // Verifica se o grupo já existe no SqLite para Atualizar ou Adicionar
                if (appDatabase.getGroup().getGroup(group.getId()) == null)
                    appDatabase.getGroup().insert(group);
                else
                    appDatabase.getGroup().update(group);

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void param) {
            try {
                super.onPostExecute(param);
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }
}
