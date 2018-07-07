package com.fa7.todolist.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.fa7.todolist.client.CollaboratorClient;
import com.fa7.todolist.client.GroupClient;
import com.fa7.todolist.client.GroupCollaboratorClient;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.model.GroupCollaborator;
import com.fa7.todolist.persistence.File.FileData;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollaboratorController {

    Context context;
    static FireBasePersistence fireBasePersistence;
    static AppDatabase appDatabase;
    static CollaboratorClient collaboratorClient;
    static GroupCollaboratorClient gcClient;

    public CollaboratorController(Context context) {
        this.context = context;
        appDatabase = MainDatabase.getInstance(this.context);
        collaboratorClient = new CollaboratorClient(this.context);
        fireBasePersistence = new FireBasePersistence(this.context);
        gcClient = new GroupCollaboratorClient(this.context);
    }

    public CollaboratorController(Context context, Boolean a) {
        this.context = context;
    }

    public List<Collaborator> GetAllCollaborators(long idGroup){
        List<Collaborator> list = new ArrayList<Collaborator>();

        for (GroupCollaborator gc: gcClient.getAllByGroup(idGroup))
            list.add(collaboratorClient.getCollaborator(gc.getIdColaborador()));

        return list;
    }

    public boolean Remove(Collaborator collaborator) {
        try {
            if(collaborator != null) {
                collaboratorClient.delete(collaborator);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.i("RemoveCollaborator", e.getMessage());
            return false;
        }
    }

    public Collaborator GetUserLocal(){
        Collaborator user = new Collaborator();
        try {
            String[] userCSV = new FileData().getText(context, "TodoListUserLocal").split("\\|");
            user.setId(userCSV[0]);
            user.setNomeColaborador(userCSV[1]);
            user.setEmail(userCSV[2]);
            user.setTypeLogin(userCSV[3]);
            user.setImagePath(userCSV[4]);

            user.setIdFarebase(Long.parseLong(userCSV[5]));
        }
        catch (Exception e){
            Log.e("GetUserLocal", e.getMessage());
        }
        return user;
    }

    // Chamar esse método para recuperar as atividades do Firebase e sincronizar com o SqLite
    public void GetSynchronizeCollaboratorFirebase(){
        List<Group> groupList = new GroupClient(context).getAll();
        for (int i = 0; i < groupList.size(); i++)
            fireBasePersistence.GetCollaborators(groupList.get(i));
    }

    // Método chamado após o retorno do evento onDataChange do método GetActivitys da classe fireBasePersistence
    public static void SetCollaborators(List<Collaborator> collaboratorList, Group group){
        new dbAsyncTask(collaboratorList, group).execute();
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        List<Collaborator> collaboratorList;
        Group group;

        public dbAsyncTask(List<Collaborator> collaboratorList, Group group) {
            this.collaboratorList = collaboratorList;
            this.group = group;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                // Navega na lista de colaboradores do grupo e verifica se já existe no SqLite para Atualizar ou Adicionar
                for (int i = 0; i <collaboratorList.size(); i++) {
                    if (appDatabase.getCollaborator().getCollaborator(collaboratorList.get(i).getId()) == null)
                        appDatabase.getCollaborator().insert(collaboratorList.get(i));
                    else
                        appDatabase.getCollaborator().update(collaboratorList.get(i));
                }

                // Navega na lista de colaboradores do grupo e verifica se já existe no SqLite na tabela de relacionamento GroupCollaborator  para Atualizar ou Adicionar
                for (int i = 0; i < collaboratorList.size(); i++) {
                    GroupCollaborator groupCollaborator = new GroupCollaborator(
                            group.getId(),
                            collaboratorList.get(i).getId()
                    );

                    if (appDatabase.getGroupCollaborator().getCollaboratorInGroup(groupCollaborator.getIdGrupo(), groupCollaborator.getIdColaborador()) == null)
                        appDatabase.getGroupCollaborator().insert(groupCollaborator);
                    else
                        appDatabase.getGroupCollaborator().update(groupCollaborator);
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
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }

}
