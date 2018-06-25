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
        fireBasePersistence.GetGroupOfFirebase();
    }

    // Método chamado após o retorno do evento onDataChange do método GetGroupOfFirebase da classe fireBasePersistence
    public static void GetMyGroups(List<Group> groupList) {
        for (int i = 0; i < groupList.size(); i++)
            fireBasePersistence.GetActivitysOfFirebase(groupList.get(i));
    }

    // Método chamado para receber do onDataChange do método GetActivitysOfFirebase as atividades e colaboradores dos grupos e enviar para os objetos de persistencia do SqLite
    public static void UpdateSqLite(Group group) {
        new dbAsyncTask(group).execute();
    }

    // Método chamado sincrodizar os dados do SqLite com o Firebase
    public void SincFirebase() {
        new firebaseAsyncTask().execute();
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
                JoinExistingGroup(Long.parseLong("1529891054362"), false);
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

    private static class firebaseAsyncTask extends AsyncTask<Void, Void, Void> {
        Group group;

        public firebaseAsyncTask() {
            this.group = group;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                List<Group> myListGroup = appDatabase.getGroup().getAll();

                for (int i = 0; i < myListGroup.size(); i++) {

                    List<Group> a = appDatabase.getGroup().getAll();
                List<Activity> x = appDatabase.getActivity().getAll();
                List<Collaborator> y = appDatabase.getCollaborator().getAll();
                List<GroupCollaborator> f = appDatabase.getGroupCollaborator().getAll();
                int abbb = 1;

                    List<GroupCollaborator> groupCollaboratorList = appDatabase
                            .getGroupCollaborator()
                            .getAllByGroup(myListGroup.get(i).getId());

                    List<Activity> activityList = appDatabase
                            .getActivity()
                            .getActivityByGroup(myListGroup.get(i).getId());

                    List<Collaborator> collaboratorList = new ArrayList<Collaborator>();
                    for (int j = 0; j < groupCollaboratorList.size(); j++) {
                        collaboratorList.add(appDatabase
                                .getCollaborator()
                                .getCollaborator(groupCollaboratorList.get(j).getIdColaborador()));
                    }

                    myListGroup.get(i).setCollaboratorList(collaboratorList);
                    myListGroup.get(i).setActivityList(activityList);

                    fireBasePersistence.GroupOnFirebase(myListGroup.get(i), true);

                }


                int abbb = 1;
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }
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

                // Navega na lista de ativiades do grupo e verifica se já existe no SqLite para Atualizar ou Adicionar
                for (int i = 0; i < group.getActivityList().size(); i++) {
                    if (appDatabase.getActivity().getActivity(group.getActivityList().get(i).getId()) == null)
                        appDatabase.getActivity().insert(group.getActivityList().get(i));
                    else
                        appDatabase.getActivity().update(group.getActivityList().get(i));
                }

                // Navega na lista de colaboradores do grupo e verifica se já existe no SqLite para Atualizar ou Adicionar
                for (int i = 0; i < group.getCollaboratorList().size(); i++) {
                    if (appDatabase.getCollaborator().getCollaborator(group.getCollaboratorList().get(i).getId()) == null)
                        appDatabase.getCollaborator().insert(group.getCollaboratorList().get(i));
                    else
                        appDatabase.getCollaborator().update(group.getCollaboratorList().get(i));
                }

                // Navega na lista de colaboradores do grupo e verifica se já existe no SqLite na tabela de relacionamento GroupCollaborator  para Atualizar ou Adicionar
                for (int i = 0; i < group.getCollaboratorList().size(); i++) {
                    GroupCollaborator groupCollaborator = new GroupCollaborator(
                            group.getId(),
                            group.getCollaboratorList().get(i).getId()
                    );

                    if (appDatabase.getGroupCollaborator().getCollaboratorInGroup(groupCollaborator.getIdGrupo()) == null)
                        appDatabase.getGroupCollaborator().insert(groupCollaborator);
                    else
                        appDatabase.getGroupCollaborator().update(groupCollaborator);
                }

//                List<Group> a = appDatabase.getGroup().getAll();
//                List<Activity> x = appDatabase.getActivity().getAll();
//                List<Collaborator> y = appDatabase.getCollaborator().getAll();
//                List<GroupCollaborator> f = appDatabase.getGroupCollaborator().getAll();
//                int abbb = 1;
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
