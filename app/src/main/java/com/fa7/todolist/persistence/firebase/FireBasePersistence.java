package com.fa7.todolist.persistence.firebase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fa7.todolist.model.Group;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBasePersistence extends AppCompatActivity {

    Context context;
    DatabaseReference databaseReference;
    private String uID;

    public FireBasePersistence(Context context){
        this.context = context;
        GetDataBaseReference();
    }

    private void GetDataBaseReference() {
        try {
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword("a@a.com.br", "123456")
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference();
                                }
                            });
        }
        catch (Exception e){
            Log.e("GetDataBaseReference", e.getMessage());
        }
    }

    public void DataGroupOnFirebase(final Group group, final Boolean add) {
        try {
            if (add) {
                databaseReference
                        .child("TodoList")
                        .child("Groups")
                        .child(String.valueOf(group.getId()))
                        .setValue(group);
            } else
                databaseReference
                        .child("TodoList")
                        .child("Groups")
                        .child(String.valueOf(group.getId()))
                        .removeValue();


        } catch (Exception e) {
            Log.e("DataOnFirebase", e.getMessage());
        }
    }

    public void DataMyGroupOnFirebase(final Group group, final Boolean add) {
        try {
            if (add) {
                databaseReference
                        .child("TodoList")
                        .child("MyGroups")
                        .child(uID)
                        .child(String.valueOf(group.getId()))
                        .setValue(group);
            } else
                databaseReference
                        .child("TodoList")
                        .child("MyGroups")
                        .child(uID)
                        .child(String.valueOf(group.getId()))
                        .removeValue();


        } catch (Exception e) {
            Log.e("DataOnFirebase", e.getMessage());
        }
    }

    public String GetNewKey() {
        String retorno = "";
        try {

            retorno = databaseReference
                    .child("TodoList")
                    .child(uID)
                    .push()
                    .getKey();

        }
        catch (Exception e){
            Log.e("DataOnFirebase", e.getMessage());
        }
        return retorno;
    }
}
