package com.fa7.todolist.persistence.firebase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fa7.todolist.control.GroupControl;
import com.fa7.todolist.model.Group;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
                    .signInWithEmailAndPassword("c@c.com.br", "123456")
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

    public void GroupOnFirebase(final Group group, final Boolean add) {
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

    public void MyGroupOnFirebase(final Group group, final Boolean add) {
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

    public void GetGroupOfFirebase() {
        try {
            databaseReference
                    .child("TodoList")
                    .child("MyGroups")
                    .child(uID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<Group> groupList = new ArrayList<Group>();
                            try {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Group g = child.getValue(Group.class);
                                    groupList.add(g);
                                }
                               if(groupList.size() > 0)
                                   GroupControl.GetMyGroups(groupList);
                            } catch (Exception e) {
                                Log.e("onDataChange", "GetGroupOfFirebasee" + e.getMessage());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("onCancelled", "GetGroupOfFirebasee" + databaseError.getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.e("GetGroupOfFirebase", e.getMessage());
        }
    }

    public void GetActivitysOfFirebase(final Group group) {
        try {
            databaseReference
                    .child("TodoList")
                    .child("Groups")
                    .child(String.valueOf(group.getId()))
                    //.equalTo(String.valueOf(group.getId()))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                if(dataSnapshot.exists()) {
                                    GroupControl.UpdateSqLite(dataSnapshot.getValue(Group.class));
                                }
                            } catch (Exception e) {
                                Log.e("onDataChange", "GetActivitysOfFirebase" + e.getMessage());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("onCancelled", "GetActivitysOfFirebase" + databaseError.getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.e("GetActivitysOfFirebase", e.getMessage());
        }
    }

}
