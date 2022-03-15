package com.mestaoui.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
import java.util.List;

public class RecycleActivity extends AppCompatActivity {
    private static final String TAG = "RecycleActivity";
    private RecyclerView recycle;
    RequestQueue requestQueue;
    String loadUrl = "http://192.168.1.107/phpvolley/ws/loadEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recycle = findViewById(R.id.recycle);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,
                loadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                recycle.setAdapter(new EtudiantAdapter(getApplicationContext(), (List<Etudiant>) etudiants));
                recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }
}