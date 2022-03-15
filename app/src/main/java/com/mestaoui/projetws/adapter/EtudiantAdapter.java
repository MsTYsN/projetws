package com.mestaoui.projetws.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mestaoui.projetws.R;
import com.mestaoui.projetws.beans.Etudiant;

import java.util.List;

public class EtudiantAdapter extends BaseAdapter{
    private static final String TAG = "EtudiantAdapter";
    private List<Etudiant> etudiants;
    private LayoutInflater inflater;

    public EtudiantAdapter(Activity activity, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Object getItem(int position) {
        return etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.etudiant_item, null);

        TextView idE = convertView.findViewById(R.id.idE);
        TextView nom = convertView.findViewById(R.id.nom);
        TextView prenom = convertView.findViewById(R.id.prenom);
        TextView ville = convertView.findViewById(R.id.ville);
        TextView sexe = convertView.findViewById(R.id.sexe);

        idE.setText(etudiants.get(position).getId()+"");
        nom.setText(etudiants.get(position).getNom());
        prenom.setText(etudiants.get(position).getPrenom());
        ville.setText(etudiants.get(position).getVille());
        sexe.setText(etudiants.get(position).getSexe());


        return convertView;
    }
}
