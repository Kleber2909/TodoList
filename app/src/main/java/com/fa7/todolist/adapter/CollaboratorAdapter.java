package com.fa7.todolist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fa7.todolist.R;
import com.fa7.todolist.model.Collaborator;
import java.util.List;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.ViewHolderAdapter> {

    List<Collaborator> collaborators;
    Context context;

    public CollaboratorAdapter(Context context, List<Collaborator> collaborators) {
        this.collaborators = collaborators;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collaborator_detail_item, parent, false);
        return new ViewHolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter holder, int position) {
        Collaborator tm = collaborators.get(position);

        holder.txt_email.setText(tm.getEmail());
        holder.txt_name.setText(tm.getNomeColaborador());
        holder.img_profile.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return collaborators.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {

        TextView txt_name, txt_email;
        ImageView img_profile;

        public ViewHolderAdapter(View itemView) {
            super(itemView);

            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_email = (TextView) itemView.findViewById(R.id.txt_email);
            img_profile = (ImageView) itemView.findViewById(R.id.img_profile);
        }
    }
}
