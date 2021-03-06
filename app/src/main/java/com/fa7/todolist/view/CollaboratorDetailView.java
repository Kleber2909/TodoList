package com.fa7.todolist.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fa7.todolist.R;
import com.fa7.todolist.adapter.CollaboratorAdapterLV;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.model.Collaborator;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.utils.SnackbarMessage;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorDetailView extends AppCompatActivity {

    private LinearLayout lay_people;
    private TextView txt_group_name;
    private static ListView lv_collaborator;
    private CollaboratorAdapterLV collaboratorAdapterLV;
    private CollaboratorController collaboratorController;
    private static List<Collaborator> collaboratorList;
    private Group group;
    private SwipeMenuCreator creator;
    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collaborator_detail);
        InitializeComponents();
        CreatorSwipeMenuCreator();
    }

    @Override
    protected void onStart() {
        super.onStart();

        group = new Group();
        group.setId(getIntent().getExtras().getLong("idGrupo"));
        group.setNomeGrupo(getIntent().getExtras().getString("nomeGrupo"));

        if(group != null) {
            txt_group_name.setText(group.getNomeGrupo());
            collaboratorController = new CollaboratorController(this);
            collaboratorList = new ArrayList<Collaborator>();

        try {
            synchronized(collaboratorList) {
                new ListCollaboratorsTask(this).execute();
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ListCollaboratorsTask(getApplicationContext()).execute();
    } else {
        SnackbarMessage.setProgressMessage(lay_people, false, " você deve selecionar um grupo!");
    }
    }

    public void updateUI(List<Collaborator> collaboratorList) {
        collaboratorAdapterLV = new CollaboratorAdapterLV(collaboratorList, this);
        lv_collaborator.setAdapter(collaboratorAdapterLV);
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
                if(group.getId() != 0) {
                    String nome = txt_group_name.getText().toString();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Olá! " +
                            "Você foi convidado para entrar no grupo de atividades " +
                            nome + ". Use o código " + group.getId() + " para entrar!";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Convite para grupo de atividades");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Compartilhar via"));
                    Toast.makeText(getBaseContext(), "1 - Grupo " + nome, Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreatorSwipeMenuCreator() {
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(212, 32, 32)));
                item2.setWidth(dp2px(90));
                item2.setTitle("Excluir");
                item2.setTitleSize(10);
                item2.setTitleColor(Color.WHITE);
                item2.setIcon(R.mipmap.ic_deletewhite);
                menu.addMenuItem(item2);
            }
        };

        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Collaborator collaborator = collaboratorList.get(position);
                        boolean remove = collaboratorController.Remove(collaborator);
                        collaboratorList.remove(position);
                        new ListCollaboratorsTask(getApplicationContext()).execute();
                        SnackbarMessage.setProgressMessage(lay_people, remove, " ao Remover o Participante.");
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
        lv_collaborator = (ListView) findViewById(R.id.lv_collaborator);
        mListView = (SwipeMenuListView) findViewById(R.id.lv_collaborator);
    }

    private class ListCollaboratorsTask extends AsyncTask<Void, Void, List<Collaborator>> {
        private final ProgressDialog dialog = new ProgressDialog(CollaboratorDetailView.this);
        Context context;
        boolean progress;

        public ListCollaboratorsTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected List<Collaborator> doInBackground(Void... voids) {
            try {
                collaboratorList = collaboratorController.GetAllCollaborators(group.getId());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(collaboratorList);
                    }
                });

                progress = true;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return collaboratorList;
        }

        @Override
        protected void onPostExecute(List<Collaborator> list) {
            super.onPostExecute(list);

            dialog.dismiss();
        }

    }

    private int dp2px(int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
