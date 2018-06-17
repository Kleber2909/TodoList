package com.fa7.todolist.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fa7.todolist.R;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    EditText id, grupo;
    FireBasePersistence fireBasePersistence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.editText);
        grupo = findViewById(R.id.editText2);
        fireBasePersistence = new FireBasePersistence(getApplicationContext());

    }

    public void gravar(View view){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        String newKey = fireBasePersistence.GetNewKey();
        id.setText(newKey);
        fireBasePersistence.DataOnFirebase(new Group(newKey, grupo.getText().toString()), newKey, true);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        int y =0;
        super.onStart();
    }

    public void remover(View view){
        new FireBasePersistence(getApplicationContext())
                .DataOnFirebase(new Group(id.getText().toString(), grupo.getText().toString()), id.getText().toString(), false);
    }
}
