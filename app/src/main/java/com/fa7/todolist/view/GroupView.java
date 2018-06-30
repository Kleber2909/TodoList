package com.fa7.todolist.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fa7.todolist.R;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Group;
import com.fa7.todolist.utils.SnackbarMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupView extends AppCompatActivity {

    private static GroupController groupController;
    public static LinearLayout lay_group;
    private SwipeMenuCreator creator;
    private SwipeMenuListView mListView;
    private ListView listViewGroups;
    private static List<Group> groupList;
    static AlertDialog alertDialog;
    private SimpleAdapter simpleAdapter;
    private FloatingActionButton fabNovoGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups);
        InitializeComponents();
        CreatorSwipeMenuCreator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        groupController = new GroupController(this);
        new UpdateListViewTask(getBaseContext()).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                ShowViewAddGroup();
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void InitializeComponents() {
        try {
            lay_group = (LinearLayout) findViewById(R.id.lay_group);
            fabNovoGrupo = findViewById(R.id.fabNovoGrupo);
            mListView = (SwipeMenuListView) findViewById(R.id.listGroups);
            listViewGroups = (ListView) findViewById(R.id.listGroups);
            listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(GroupView.this, CollaboratorDetailView.class);
                    Bundle b = new Bundle();
                    b.putLong("idGrupo", groupList.get(position).getId());
                    b.putString("nomeGrupo", groupList.get(position).getNomeGrupo());
                    intent.putExtras(b);
                    startActivity(intent);

                }
            });

            fabNovoGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowViewAddGroup();
                }
            });

        }
        catch (Exception e)
        {
            Log.e("InitializeComponents", e.getMessage());
        }
    }

    private void ShowViewAddGroup() {
        LayoutInflater li = getLayoutInflater();
        final View view = li.inflate(R.layout.my_groups_add_join, null);
        final TextView txt_desc = (TextView) view.findViewById(R.id.txt_desc);
        final EditText edt_groupname = (EditText) view.findViewById(R.id.edt_groupname);
        final EditText edt_groupname_join = (EditText) view.findViewById(R.id.edt_groupname_join);
        final Switch choice = (Switch) view.findViewById(R.id.choice);
        txt_desc.setText("Adicionar Grupo");
        choice.setChecked(true);
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice.isChecked()) {
                    view.findViewById(R.id.lay_add).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.lay_join).setVisibility(View.GONE);
                    txt_desc.setText("Adicionar Grupo");
                } else {
                    view.findViewById(R.id.lay_add).setVisibility(View.GONE);
                    view.findViewById(R.id.lay_join).setVisibility(View.VISIBLE);
                    txt_desc.setText("Entrar em Grupo existente");
                }
            }
        });


        view.findViewById(R.id.btn_add_join).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {
                    if(choice.isChecked()) {
                        choice.setTextOn("Adicionar Grupo");
                        String nome = edt_groupname.getText().toString();

                        if (nome.equals("")) {
                            SnackbarMessage.setProgressMessage(lay_group, false, ": Selecione o grupo.");
                            return;
                        }

                        Group activity = new Group(nome);
                        new SaveGroupTask(getApplicationContext(), activity).execute();
                        new UpdateListViewTask(getApplicationContext()).execute();

                    } else {
                        choice.setTextOn("Entrar em Grupo existente");
                        String idGroup = edt_groupname_join.getText().toString();

                        if (idGroup.equals("")) {
                            SnackbarMessage.setProgressMessage(lay_group, false, ": Informe o código do grupo.");
                            return;
                        }

                        Group group = new Group(Long.parseLong(idGroup));
                        new JoinGroupTask(getApplicationContext(), group).execute();
                        new UpdateListViewTask(getApplicationContext()).execute();
                    }

                    alertDialog.dismiss();
                } catch (Exception e) {
                    SnackbarMessage.setProgressMessage(lay_group, false, e.getMessage());
                }
            }
        });

        ShowDialogWithLayout(view);
    }

    private void  CreatorSwipeMenuCreator() {
        // step 1. create a MenuCreator
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(17, 102, 17)));
                item1.setWidth(dp2px(90));
                item1.setTitle("Compartilhar");
                item1.setTitleSize(10);
                item1.setTitleColor(Color.WHITE);
                item1.setIcon(R.mipmap.ic_sharewhite);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(86, 178, 232)));
                item2.setWidth(dp2px(90));
                item2.setTitle("Editar");
                item2.setTitleSize(10);
                item2.setTitleColor(Color.WHITE);
                item2.setIcon(R.mipmap.ic_editwhite);
                menu.addMenuItem(item2);

                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(212, 32, 32)));
                item3.setWidth(dp2px(90));
                item3.setTitle("Excluir");
                item3.setTitleSize(10);
                item3.setTitleColor(Color.WHITE);
                item3.setIcon(R.mipmap.ic_deletewhite);
                menu.addMenuItem(item3);
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
                        // Recupera o grupo selecionado
                        nome = groupList.get(position).getNomeGrupo();
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Olá! "+
                                "Você foi convidado para entrar no grupo de atividades " +
                                nome + ". Use o código a seguir para entrar " + String.valueOf(groupList.get(position).getId());
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Convite para grupo de atividades");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Compartilhar via"));
                        //Toast.makeText(getBaseContext(), "1 - Grupo " + nome, Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        nome = groupList.get(position).getNomeGrupo();
                        idGroup = groupList.get(position).getId();
                        LayoutInflater li = getLayoutInflater();
                        View view = li.inflate(R.layout.my_groups_update, null);
                        final EditText edt_groupname_old = (EditText) view.findViewById(R.id.edt_groupname_old);
                        final EditText edt_groupname_new = (EditText) view.findViewById(R.id.edt_groupname_new);
                        edt_groupname_old.setText(nome);
                        view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                            public void onClick(View arg0) {
                                try {
                                    String new_name = edt_groupname_new.getText().toString();

                                    if(!nome.equals(new_name) && !new_name.equals("")) {
                                        Group group = new Group(idGroup, new_name);
                                        new UpdateGroupTask(getApplicationContext(), group).execute();
                                        new UpdateListViewTask(getApplicationContext()).execute();
                                        alertDialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    SnackbarMessage.setProgressMessage(lay_group, false, e.getMessage());
                                }
                            }
                        });

                        ShowDialogWithLayout(view);

                        break;

                    case 2:
                        nome = groupList.get(position).getNomeGrupo();
                        idGroup =  groupList.get(position).getId();
                        ShowDialogWithoutLayout(nome, idGroup);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }

    private void ShowDialogWithLayout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void ShowDialogWithoutLayout(String nome, long id) {
        try {
            if (id == 0)
                return;

            final long idGroup = id;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Remover grupo");
            builder.setMessage("Você tem certeza que deseja Remover o grupo " + nome + "?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    new RemoveGroupTask(getApplicationContext(), new Group(idGroup)).execute();
                    new UpdateListViewTask(getApplicationContext()).execute();
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    //Não faz nada
                }
            });

            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Log.i("RemoveGroup", e.getMessage());
        }
    }

    private class UpdateListViewTask extends AsyncTask<Void, Void, Void> {

        Context context;
        public UpdateListViewTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                groupList = groupController.GetAllGroup();

                ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
                for (int i=0;i<groupList.size();i++)
                {
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("nome",groupList.get(i).getNomeGrupo());
                    arrayList.add(hashMap);
                }

                String[] from={"nome"};//string array
                int[] to={R.id.txt_group};//int array of views id's
                simpleAdapter = new SimpleAdapter(context, arrayList ,R.layout.my_groups_item,from,to);//Create object and set the parameters for simpleAdapter

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewGroups.setAdapter(simpleAdapter);
                    }
                });

            }catch (Exception ex){
                String a = "";
            }
            return null;
        }

        @Override
        protected void onPreExecute () {
            // Progresso.
        }

    }

    private static class SaveGroupTask extends AsyncTask<Group, Void, Boolean> {
        static Context context;
        Group group;
        boolean progress;

        public SaveGroupTask(Context context, Group group){
            this.context = context;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Group... params) {
            try {
                groupController.AddNewGroup(group);
                progress = true;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
            SnackbarMessage.setProgressMessage(lay_group, progress, " ao Adicionar Grupo.");
        }
    }

    private static class JoinGroupTask extends AsyncTask<Group, Void, Boolean> {
        static Context context;
        Group group;
        boolean progress;

        public JoinGroupTask(Context context, Group group){
            this.context = context;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Group... params) {
            try {
                groupController.JoinExistingGroup(group.getId(), true);

                progress = true;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);
            SnackbarMessage.setProgressMessage(lay_group, progress, " ao Entrar em Grupo Existente.");
        }
    }

    private static class RemoveGroupTask extends AsyncTask<Group, Void, Boolean> {
        Context context;
        Group group;
        boolean progress;

        public RemoveGroupTask(Context context, Group group){
            this.context = context;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Group... params) {
            try {
                progress = groupController.RemoveGroup(group);
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);

            if(progress)
                groupController.GetSynchronizeFirebase();

            SnackbarMessage.setProgressMessage(lay_group, progress, " ao Remover Grupo.");
        }
    }

    private static class UpdateGroupTask extends AsyncTask<Group, Void, Boolean> {
        Context context;
        Group group;
        boolean progress;

        public UpdateGroupTask(Context context, Group group){
            this.context = context;
            this.group = group;
        }

        @Override
        protected Boolean doInBackground(Group... params) {
            try {
                groupController.UpdateGroupName(group);
                progress = true;
            }
            catch (Exception e){
                Log.e("Erro", e.getMessage());
            }

            return progress;
        }

        @Override
        protected void onPostExecute(Boolean progress) {
            super.onPostExecute(progress);

            if(progress)
                groupController.GetSynchronizeFirebase();

            SnackbarMessage.setProgressMessage(lay_group, progress, " ao Atualizar Grupo.");
        }
    }

    private int dp2px(int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
