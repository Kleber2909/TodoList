package com.fa7.todolist.control;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.util.List;

public class GroupControl {

    Context context;
    static FireBasePersistence fireBasePersistence;
    static AppDatabase appDatabase;

    public GroupControl(Context context){
        this.context = context;
        fireBasePersistence = new FireBasePersistence(context);
        appDatabase = MainDatabase.getInstance(context);
    }

    // Chamar esse método para entrar em grupo já existente
    public void JoinExistingGroup(long id){
        fireBasePersistence.MyGroupOnFirebase(new Group(id), true);
    }

    // Chamar esse método para sincronizar os grupos de atividades do Firebase com o SqLite
    public void GetSynchronizeFirebase(){
        fireBasePersistence.GetGroupOfFirebase();
    }

    // Método chamado após o retorno do evento onDataChange do método GetGroupOfFirebase da classe fireBasePersistence
    public static void GetMyGroups(List<Group> groupList){
        for(int i = 0; i < groupList.size(); i++)
            fireBasePersistence.GetActivitysOfFirebase(groupList.get(i));
    }

    // Método chamado para receber do onDataChange do método GetActivitysOfFirebase as atividades e colaboradores dos grupos e enviar para os objetos de persistencia do SqLite
    public static void UpdateSqLite(Group group){
        new dbAsyncTask(group).execute();
    }

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        Group group;

        public dbAsyncTask(Group group){
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
                    if (appDatabase.getCollaborator().getCollaborator(group.getCollaboratorList().get(i).getId())== null)
                        appDatabase.getCollaborator().insert(group.getCollaboratorList().get(i));
                    else
                        appDatabase.getCollaborator().update(group.getCollaboratorList().get(i));
                }

                List<Group> a = appDatabase.getGroup().getAll();
                List<Activity> x = appDatabase.getActivity().getAll();
                List<Collaborator> y = appDatabase.getCollaborator().getAll();
                int abbb = 1;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }
            return null;
        }
    }


    /*
    if(appDatabase.userDAO().getMovie(movie.getId()) == null)
    {
        appDatabase = MainDatabase.getInstance(context);
        appDatabase.userDAO().insertAll(movie);
    }
    */
}
