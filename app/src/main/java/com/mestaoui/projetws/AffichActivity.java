package com.mestaoui.projetws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mestaoui.projetws.adapter.EtudiantAdapter;
import com.mestaoui.projetws.beans.Etudiant;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AffichActivity extends AppCompatActivity{
    private static final String TAG = "RecycleActivity";
    private ListView liste;
    RequestQueue requestQueue;
    String loadUrl = "http://192.168.100.162/phpvolley/ws/loadEtudiant.php";
    String deleteUrl = "http://192.168.100.162/phpvolley/ws/deleteEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affich);

        liste = findViewById(R.id.liste);

        loadData();

    }

    public void loadData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                loadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                liste.setAdapter(new EtudiantAdapter(AffichActivity.this, (List<Etudiant>) etudiants));
                liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final TextView idE = view.findViewById(R.id.idE);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AffichActivity.this);
                        alertDialogBuilder.setMessage("Veuillez choisir une option :");

                        alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestQueue = Volley.newRequestQueue(AffichActivity.this);
                                StringRequest request = new StringRequest(Request.Method.POST,
                                        deleteUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, response);
                                        //Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                                        //Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                                        loadData();
                                        Toast.makeText(AffichActivity.this, "Suppression avec succès", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(AffichActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("id", idE.getText().toString());
                                        return params;
                                    }
                                };
                                requestQueue.add(request);
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AffichActivity.this, EditActivity.class);
                                intent.putExtra("idE", idE.getText().toString());
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }
}