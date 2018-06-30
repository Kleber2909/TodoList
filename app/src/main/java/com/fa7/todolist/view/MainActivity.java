package com.fa7.todolist.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fa7.todolist.R;
import com.fa7.todolist.adapter.ActivityAdapter;
import com.fa7.todolist.client.ActivityClient;
import com.fa7.todolist.controller.GroupController;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.persistence.firebase.FireBasePersistence;
import com.fa7.todolist.persistence.room.ActivityDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Calendar oCalendar = Calendar.getInstance();
    private static ActivityClient oActivityClient;
    private FireBasePersistence oFireBasePersistence;
    private FloatingActionButton fabAnterior, fabProximo, fabNovo;
    private TextView txbData;
    static List<ActivityDAO.ActivityAndGroup> oList;

    private ActivityAdapter adapter;
    private int LineView;
    private ListView lstAtividades;

    private int TipoFiltro = 0;
    private int IdList = 0;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        oFireBasePersistence = new FireBasePersistence(getApplicationContext());
//        oFireBasePersistence.GetGroupOfFirebase();

//        GroupController oG = new GroupController(this);
//        oG.GetSynchronizeFirebase();

        oActivityClient = new ActivityClient(this);
//        oList = oActivityClient.getAll();

        fabAnterior = findViewById(R.id.fabAnterior);
        fabAnterior.setOnClickListener(this);
        fabProximo = findViewById(R.id.fabProximo);
        fabProximo.setOnClickListener(this);
        fabNovo = findViewById(R.id.fabNovo);
        fabNovo.setOnClickListener(this);
        txbData = findViewById(R.id.txbData);

        txbData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=oCalendar;
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);

                        oCalendar = myCalendar;
                        exibirData();

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Selecionar Data");
                mDatePicker.show();  }
        });

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Todas");
        spec.setContent(R.id.tabTodos);
        spec.setIndicator("Todas");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Pendentes");
        spec.setContent(R.id.tabPendentes);
        spec.setIndicator("Pendentes");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Concluidas");
        spec.setContent(R.id.tabRealizadas);
        spec.setIndicator("Concluidas");
        host.addTab(spec);

        host.getTabWidget().getChildAt(0).getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        host.getTabWidget().getChildAt(1).getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        host.getTabWidget().getChildAt(2).getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("Todas")){
                    TipoFiltro = 0;
                }else if(tabId.equals("Pendentes")) {
                    TipoFiltro = 1;
                }else{
                    TipoFiltro = 2;
                }
                exibirData();
            }
        });

        SwipeMenuListView mListView = (SwipeMenuListView) findViewById(R.id.lstAtividades);
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                if (oList.get(IdList).oActivity.getStatus().equals("Pendente")) {
                    createPendente(menu);
                }else{
                    createRealizada(menu);
                }
                IdList++;
            }

            private void createPendente(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(68, 216, 113)));
                item1.setWidth(dp2px(90));
                item1.setTitle("Concluído");
                item1.setTitleSize(10);
                item1.setTitleColor(Color.BLACK);
                item1.setIcon(R.mipmap.ic_checked_transp);
                menu.addMenuItem(item1);

                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(86, 178, 232)));
                item3.setWidth(dp2px(90));
                item3.setTitle("Editar");
                item3.setTitleSize(10);
                item3.setTitleColor(Color.BLACK);
                item3.setIcon(R.mipmap.ic_edit_transp);
                menu.addMenuItem(item3);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(212, 32, 32)));
                item2.setWidth(dp2px(90));
                item2.setTitle("Excluir");
                item2.setTitleSize(10);
                item2.setTitleColor(Color.BLACK);
                item2.setIcon(R.mipmap.ic_delete_transp);

                menu.addMenuItem(item2);
            }

            private void createRealizada(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(68, 216, 113)));
                item1.setWidth(dp2px(90));
                item1.setTitle("Pendente");
                item1.setTitleSize(10);
                item1.setTitleColor(Color.BLACK);
                item1.setIcon(R.mipmap.ic_unchecked_transp);
                menu.addMenuItem(item1);

                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(86, 178, 232)));
                item3.setWidth(dp2px(90));
                item3.setTitle("Editar");
                item3.setTitleSize(10);
                item3.setTitleColor(Color.BLACK);
                item3.setIcon(R.mipmap.ic_edit_transp);
                menu.addMenuItem(item3);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(212, 32, 32)));
                item2.setWidth(dp2px(90));
                item2.setTitle("Excluir");
                item2.setTitleSize(10);
                item2.setTitleColor(Color.BLACK);
                item2.setIcon(R.mipmap.ic_delete_transp);
                menu.addMenuItem(item2);
            }

        };

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                IdList = position;
                id = oList.get(position).oActivity.getId();

                switch (index) {
                    case 0:
                        new dbAsyncTaskUpdateStatus().execute();
                        break;

                    case 1:
                        Intent intent = new Intent(MainActivity.this, ActivityView.class);
                        Bundle b = new Bundle();
                        b.putLong("key", oList.get(IdList).oActivity.getId()); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);

                        break;

                    case 2:
                        // delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Deseja realmente excluir esta atividade ?")
                                .setPositiveButton("Sim", dialogClickListener)
                                .setNegativeButton("Não", dialogClickListener).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        exibirData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LstGrupo:
                Intent intent = new Intent(MainActivity.this, GroupView.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAnterior:
                oCalendar.add(Calendar.DAY_OF_MONTH, -1);
                exibirData();
                break;
            case R.id.fabProximo:
                oCalendar.add(Calendar.DAY_OF_MONTH, 1);
                exibirData();
                break;
            case R.id.fabNovo:
                Intent intent = new Intent(MainActivity.this, ActivityView.class);
                startActivity(intent);
                break;
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    new dbAsyncTaskDelete().execute();
                    Toast.makeText(getBaseContext(), "Registro Excluído !", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void exibirData()
    {
        txbData.setText(getDate());

        new dbAsyncTask().execute();
    }

    public void LoadLista()
    {
        lstAtividades = (ListView) findViewById(R.id.lstAtividades);
        IdList = 0;

        if(TipoFiltro == 0){
            //Todos
        }else if(TipoFiltro == 1) {
            //Pendentes
        }else{
            //Realizadas
        }

        ArrayList<ActivityDAO.ActivityAndGroup> arrayOfUsers = (ArrayList<ActivityDAO.ActivityAndGroup>) oList;
        // Create the adapter to convert the array to views
        adapter = new ActivityAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        lstAtividades.setAdapter(adapter);
    }

    private class dbAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String Status = "";
                if(TipoFiltro == 0){
                    Status = "Todos";
                }else if(TipoFiltro == 1) {
                    Status = "Pendente";
                }else{
                    Status = "Concluido";
                }

                oList = oActivityClient.loadAllByDate(getDate(), Status);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadLista();
                    }
                });

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void param) {
            try {
                super.onPostExecute(param);
                // SincFirebase();
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }

    private class dbAsyncTaskUpdateStatus extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                oActivityClient.updateStatus(oList.get(IdList).oActivity.getId(), (oList.get(IdList).oActivity.getStatus().equals("Pendente") ? "Concluido" : "Pendente"));

                String Status = "";
                if(TipoFiltro == 0){
                    Status = "Todos";
                }else if(TipoFiltro == 1) {
                    Status = "Pendente";
                }else{
                    Status = "Concluido";
                }
                oList = oActivityClient.loadAllByDate(getDate(), Status);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadLista();
                    }
                });

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void param) {
            try {
                super.onPostExecute(param);
                // SincFirebase();
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }

    private class dbAsyncTaskDelete extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                oActivityClient.deleteById(oList.get(IdList).oActivity.getId());

                String Status = "";
                if(TipoFiltro == 0){
                    Status = "Todos";
                }else if(TipoFiltro == 1) {
                    Status = "Pendente";
                }else{
                    Status = "Concluido";
                }
                oList = oActivityClient.loadAllByDate(getDate(), Status);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadLista();
                    }
                });

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void param) {
            try {
                super.onPostExecute(param);
                // SincFirebase();
            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
            }
        }
    }

    private int dp2px(int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private String getDate()
    {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String formatted = format1.format(oCalendar.getTime());
        return formatted;
    }

}
