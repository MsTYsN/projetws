package com.mestaoui.projetws.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mestaoui.projetws.R;
import com.mestaoui.projetws.beans.Etudiant;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>{
    private static final String TAG = "EtudiantAdapter";
    private List<Etudiant> etudiants;
    private LayoutInflater inflater;
    private Context context;
    RequestQueue requestQueue;
    String deleteUrl = "http://192.168.1.107/phpvolley/ws/deleteEtudiant.php";

    public EtudiantAdapter(Context context, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.etudiant_item, parent, false);
        return new EtudiantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int position) {
        holder.id.setText(etudiants.get(position).getId()+"");
        holder.nom.setText(etudiants.get(position).getNom());
        holder.prenom.setText(etudiants.get(position).getPrenom());
        holder.ville.setText(etudiants.get(position).getVille());
        holder.sexe.setText(etudiants.get(position).getSexe());
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public class EtudiantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id,nom,prenom,ville,sexe;

        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idE);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            ville = itemView.findViewById(R.id.ville);
            sexe = itemView.findViewById(R.id.sexe);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final TextView id = v.findViewById(R.id.idE);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Veuillez choisir une option :");

            alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestQueue = Volley.newRequestQueue(context);
                    StringRequest request = new StringRequest(Request.Method.POST,
                            deleteUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                            Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("id", nom.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            });
            alertDialogBuilder.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
