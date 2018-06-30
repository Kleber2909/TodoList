package com.fa7.todolist.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fa7.todolist.R;
import com.fa7.todolist.adapters.CollaboratorAdapter;
import com.fa7.todolist.adapters.CollaboratorAdapterLV;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.utils.SnackbarMessage;

import java.util.ArrayList;
import java.util.List;

public class PeopleDetailView extends AppCompatActivity {

    private LinearLayout lay_people;
    private TextView txt_group_name;
    private ListView listCollaborators;
    private CollaboratorController collaboratorController;
    private static List<Collaborator> collaboratorList;
    private static long idGroup;
    private SwipeMenuCreator creator;
    private SwipeMenuListView mListView;
    private CollaboratorAdapterLV adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_detail);
        InitializeComponents();
        CreatorSwipeMenuCreator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        collaboratorController = new CollaboratorController(this);
        new PeopleDetailView.UpdateListViewTask(getBaseContext()).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.people_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                // Recupera o grupo selecionado
                //if(idGroup != 0) {
                    String nome = txt_group_name.getText().toString();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Olá! " +
                            "Você foi convidado para entrar no grupo de atividades " +
                            nome + ". Use o código " + idGroup + " para entrar!";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Convite para grupo de atividades");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Compartilhar via"));
                    Toast.makeText(getBaseContext(), "1 - Grupo " + nome, Toast.LENGTH_LONG).show();
                    return true;
                //}

                //return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreatorSwipeMenuCreator() {
// step 1. create a MenuCreator
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem delete = new SwipeMenuItem(getApplicationContext());
                delete.setBackground(new ColorDrawable(Color.rgb(255, 79, 71)));
                delete.setWidth(100);
                delete.setIcon(R.mipmap.ic_item_delete);
                menu.addMenuItem(delete);
            }
        };

        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final String nome;
                final Long idGroup;
                switch (index) {
                    case 0:
                        Collaborator collaborator = collaboratorList.get(position);
                        boolean remove = collaboratorController.Remove(collaborator);
                        collaboratorList.remove(position);
                        SnackbarMessage.setProgressMessage(lay_people, remove, " ao Remover o Participante.");
                        new UpdateListViewTask(getApplicationContext()).execute();
                        return remove;
                }
                return false;
            }
        });

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }

    private void InitializeComponents() {
        lay_people = (LinearLayout) findViewById(R.id.lay_people);
        txt_group_name = (TextView) findViewById(R.id.txt_group_name);
        listCollaborators = (ListView) findViewById(R.id.listCollaborators);
        mListView = (SwipeMenuListView) findViewById(R.id.listCollaborators);
        idGroup = getIntent().getLongExtra("id_group", 0);

        collaboratorList = new ArrayList<Collaborator>();

        Collaborator c = new Collaborator();
        c.setEmail("leandro.jpinh@hotmail.com");
        c.setId("1");
        c.setNomeColaborador("Leandro Jackson");
        collaboratorList.add(c);
        collaboratorList.add(c);
        collaboratorList.add(c);
        collaboratorList.add(c);
        collaboratorList.add(c);

        adapter = new CollaboratorAdapterLV(collaboratorList, this);
        listCollaborators.setAdapter(adapter);
    }

    private class UpdateListViewTask extends AsyncTask<Void, Void, Void> {

        Context context;

        public UpdateListViewTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            //collaboratorList = collaboratorController.GetAllGroup();
            //final CollaboratorAdapter adapter = new CollaboratorAdapter(context, collaboratorList);


            listCollaborators.setAdapter(adapter);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listCollaborators.setAdapter(adapter);
                }
            });


            return null;
        }

        @Override
        protected void onPreExecute() {
            // Progresso.
        }
    }
}
