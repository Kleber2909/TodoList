package com.fa7.todolist.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fa7.todolist.R;
import com.fa7.todolist.model.Collaborator;

import java.util.List;

public class CollaboratorAdapterLV extends BaseAdapter {

    private final List<Collaborator> collaboratorList;
    private final Activity activity;

    public CollaboratorAdapterLV(List<Collaborator> collaborators, Activity act) {
        this.collaboratorList = collaborators;
        this.activity = act;
    }

    @Override
    public int getCount() {
        return collaboratorList.size();
    }

    @Override
    public Object getItem(int i) {
        return collaboratorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = activity.getLayoutInflater().inflate(R.layout.people_detail_item, viewGroup, false);
        Collaborator collaborator = collaboratorList.get(i);

        //pegando as referências das Views
        TextView name = (TextView)
                view.findViewById(R.id.txt_name);
        TextView email = (TextView)
                view.findViewById(R.id.txt_email);
        ImageView profile = (ImageView)
                view.findViewById(R.id.img_profile);

        //populando as Views
        name.setText(collaborator.getNomeColaborador());
        email.setText(collaborator.getEmail());
        profile.setImageResource(R.drawable.ic_launcher_background);

        return view;
    }
}
