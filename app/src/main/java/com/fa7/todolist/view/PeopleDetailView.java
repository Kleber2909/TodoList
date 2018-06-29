package com.fa7.todolist.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fa7.todolist.R;
import com.fa7.todolist.adapters.CollaboratorAdapter;
import com.fa7.todolist.controller.CollaboratorController;
import com.fa7.todolist.model.Collaborator;
import java.util.List;

public class PeopleDetailView extends AppCompatActivity {

    private LinearLayout lay_people;
    private TextView txt_group_name;
    private RecyclerView rv_people;
    private CollaboratorController collaboratorController;
    private static List<Collaborator> collaboratorList;

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
            case R.id.item_add:
                return true;
            case R.id.item_share:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreatorSwipeMenuCreator() {

    }

    private void InitializeComponents() {
        lay_people = (LinearLayout) findViewById(R.id.lay_people);
        txt_group_name = (TextView) findViewById(R.id.txt_group_name);
        rv_people = (RecyclerView) findViewById(R.id.rv_people);
        rv_people.setHasFixedSize(true);
        rv_people.setLayoutManager(new LinearLayoutManager(this));
    }

    private class UpdateListViewTask extends AsyncTask<Void, Void, Void> {

        Context context;
        public UpdateListViewTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            collaboratorList = collaboratorController.GetAllGroup();

            if (collaboratorList.size() == 0) {

                Collaborator c = new Collaborator();
                c.setEmail("leandro.jpinh@hotmail.com");
                c.setId("1");
                c.setNomeColaborador("Leandro Jackson");
                collaboratorList.add(c);
                collaboratorList.add(c);
                collaboratorList.add(c);
                collaboratorList.add(c);
                collaboratorList.add(c);
            }

            final CollaboratorAdapter adapter = new CollaboratorAdapter(context, collaboratorList);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rv_people.setAdapter(adapter);
                }
            });


            return null;
        }

        @Override
        protected void onPreExecute () {
            // Progresso.
        }
    }
}
