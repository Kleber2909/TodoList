package com.fa7.todolist.control;

import android.content.Context;

import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;

import java.util.List;

public class GroupControl {

    Context context;
    static FireBasePersistence fireBasePersistence;

    public GroupControl(Context context){
        this.context = context;
        fireBasePersistence = new FireBasePersistence(context);
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
    public static void UpdateSqLite(Group groupList){
        int a = 1;
    }


}
