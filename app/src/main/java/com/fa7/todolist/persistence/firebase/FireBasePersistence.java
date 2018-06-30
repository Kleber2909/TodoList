package com.fa7.todolist.persistence.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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
        try {
            this.context = context;
            Collaborator collaborator = new CollaboratorController(context).GetUserLocal();
            if (collaborator.getTypeLogin().equals("G"))
                firebaseAuthWithGoogle(collaborator.getId());
            else
                GetDataBaseReference();
        }
        catch (Exception e){
            Log.e("Erro", e.getMessage());
        }
    }

    // Login E-mail
    private void GetDataBaseReference() {
        try {
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword("c@c.com.br", "123456")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uID = task.getResult().getUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            GetGroupOfFirebase();
                            Log.i("GetDataBaseReference", "Logaod" + uID);
                        } else {
                            Log.e("Erro no login", "Erro no login");
                        }
                    }
                });
        }
        catch (Exception e){
            Log.e("GetDataBaseReference", e.getMessage());
        }
    }

    // Login Google
    private void firebaseAuthWithGoogle(String IdToken) {
        Log.d("AuthWithGoogle", "firebaseAuthWithGoogle:" + IdToken);

        AuthCredential credential = GoogleAuthProvider.getCredential(IdToken, null);
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            GetGroupOfFirebase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthWithGoogle", "signInWithCredential:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Login Facebook
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookAccessToken", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            GetGroupOfFirebase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookAccessToken", "signInWithCredential:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GroupOnFirebase(final Group group, final Boolean action) {
        try {
            if (action) {
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

            this.MyGroupOnFirebase(new Group(group.getId()), action);
        } catch (Exception e) {
            Log.e("DataOnFirebase", e.getMessage());
        }
    }

    public void ActivityOnFirebase(final Activity activity, Collaborator collaborator, final Boolean add) {
        try {
            if (add) {
                databaseReference
                        .child("TodoList")
                        .child("Groups")
                        .child(activity.getIdGrupo())
                        .child("activityList")
                        .child(String.valueOf(activity.getId()))
                        .setValue(activity);

                databaseReference
                        .child("TodoList")
                        .child("Groups")
                        .child(activity.getIdGrupo())
                        .child("collaboratorList")
                        .child(String.valueOf(collaborator.getIdFarebase()))
                        .setValue(collaborator);

            } else
                databaseReference
                        .child("TodoList")
                        .child("Groups")
                        .child(activity.getIdGrupo())
                        .child("activityList")
                        .child(String.valueOf(activity.getId()))
                        .removeValue();
        } catch (Exception e) {
            Log.e("ActivityOnFirebase", e.getMessage());
        }
    }

    public void MyGroupOnFirebase(final Group group, final Boolean action) {
        try {
            if (action) {
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
                    .addValueEventListener(new ValueEventListener() {
                    //.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<Group> groupList = new ArrayList<Group>();
                            try {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Group g = child.getValue(Group.class);
                                    groupList.add(g);
                                }
                               if(groupList.size() > 0)
                                   GroupController.GetMyGroups(groupList);
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
                                    GroupController.UpdateSqLite(dataSnapshot.getValue(Group.class));
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
