package com.fa7.todolist.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fa7.todolist.R;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Group;

import java.util.List;

public class GroupView extends AppCompatActivity {

    private GroupController groupController;
    public static LinearLayout lay_group;
    private FloatingActionButton btn_add;
    private SwipeMenuCreator creator;
    private SwipeMenuListView mListView;
    private ListView listViewGroups;
    private static List<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups);
        InitializeComponents();

        CreatorSwipeMenuCreator();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveGroup();
            }
        });
    }
    
    private void InitializeComponents() {
        try {
            lay_group = (LinearLayout) findViewById(R.id.lay_group);
            btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
            mListView = (SwipeMenuListView) findViewById(R.id.listGroups);
            listViewGroups = (ListView) findViewById(R.id.listGroups);
            groupController = new GroupController(getApplicationContext());
            new GetAllGroup(getBaseContext()).execute();

            listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getBaseContext(), "Click ListView", Toast.LENGTH_SHORT);

                }
            });
        }
        catch (Exception e)
        {
            Log.e("InitializeComponents", e.getMessage());
        }

    }

    private void SaveGroup() {

    }

    private void  CreatorSwipeMenuCreator() {
        // step 1. create a MenuCreator
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
               // item1.setBackground(new ColorDrawable(Color.rgb(68, 216, 113)));
                item1.setWidth(100);
//                item1.setTitle("Compartilhar");
//                item1.setTitleSize(10);
//                item1.setTitleColor(Color.BLACK);
                item1.setIcon(R.mipmap.ic_share);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
               // item2.setBackground(new ColorDrawable(Color.rgb(86, 178, 232)));
                item2.setWidth(100);
//                item2.setTitle("Editar");
//                item2.setTitleSize(10);
//                item2.setTitleColor(Color.BLACK);
                item2.setIcon(R.mipmap.ic_edit);
                menu.addMenuItem(item2);

                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
              //  item3.setBackground(new ColorDrawable(Color.rgb(212, 32, 32)));
                item3.setWidth(100);
//                item3.setTitle("Excluir");
//                item3.setTitleSize(10);
//                item3.setTitleColor(Color.BLACK);
                item3.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(item3);
            }
        };

int x =1;
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String nome;
                switch (index) {
                    case 0:
                        // Recupera o grupo selecionado
                        nome = groupList.get(position).getNomeGrupo();
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Olá! "+
                                "Você foi convidado para entrar no grupo de atividades " +
                                nome + ". Use o código " + groupList.get(position).getId() + " para entrar!";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Convite para grupo de atividades");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Compartilhar via"));
                        Toast.makeText(getBaseContext(), "1 - Grupo " + nome, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        nome = groupList.get(position).getNomeGrupo();
                        Toast.makeText(getBaseContext(), "2 - Grupo " + nome, Toast.LENGTH_LONG).show();
                        break;

                    case 2:
                        // delete
                        nome = groupList.get(position).getNomeGrupo();
                        Toast.makeText(getBaseContext(), "3 - Grupo " + nome, Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }

    private class GetAllGroup extends AsyncTask<Void, Void, Void> {

        Context context;
        public GetAllGroup(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            groupList = groupController.GetAllGroup();
            final ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(context,
                    android.R.layout.simple_list_item_1, groupList);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listViewGroups.setAdapter(adapter);
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
