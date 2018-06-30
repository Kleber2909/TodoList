package com.fa7.todolist.adapter;

import android.content.Context;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.fa7.todolist.R;
import com.fa7.todolist.model.Activity;
import com.fa7.todolist.persistence.room.ActivityDAO;

import java.text.NumberFormat;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Locale;
        import java.util.Set;

public class ActivityAdapter extends ArrayAdapter<ActivityDAO.ActivityAndGroup> {

    private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    public ActivityAdapter(Context context, ArrayList<ActivityDAO.ActivityAndGroup> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ActivityDAO.ActivityAndGroup oActivity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_item_list, parent, false);
        }

        // Lookup view for data population
        TextView txbTitulo = convertView.findViewById(R.id.txbTitulo);
        TextView txbGrupo = convertView.findViewById(R.id.txbGrupo);
        TextView txbPrioridade = convertView.findViewById(R.id.txbPrioridade);
        TextView txbStatus = convertView.findViewById(R.id.txbStatus);
        ImageView imgChecked = convertView.findViewById(R.id.imgChecked);

        txbTitulo.setText(oActivity.oActivity.getTitulo());
        txbGrupo.setText(oActivity.nomeGrupo);
        txbPrioridade.setText(oActivity.oActivity.getPrioridade());
        txbStatus.setText(oActivity.oActivity.getStatus());

        if(oActivity.oActivity.getStatus().equals("Pendente"))
            imgChecked.setVisibility(View.INVISIBLE);
        else
            imgChecked.setVisibility(View.VISIBLE);

        return convertView;
    }

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    public int countSelection() {
        return mSelection.size();
    }
}