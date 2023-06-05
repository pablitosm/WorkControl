package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

import java.util.HashMap;
import java.util.Map;

public class maquinaria extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_maquinaria);
        setNavigationViewListener();
        recuperarDatosDBPrueba();
    }

    public void recuperarDatosDBPrueba() {
            DocumentReference docRef = database.collection("Users").document("53sUJvEYQsGsapRoMQ4x");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, String> datosUsuarios = new HashMap<>();
                            Log.d(TAG, "Prueba hola hola hola - " + document.getData());
                        } else {
                            Log.d(TAG, "Prueba adios adios adios");
                        }
                    } else {
                        Log.d(TAG, "el get ha fallado - " + task.getException());
                    }
                }
            });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(maquinaria.this, InicioAdmin.class));
                break;
            case R.id.panel_control:
                startActivity(new Intent(maquinaria.this, panel_control.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(maquinaria.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(maquinaria.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(maquinaria.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(maquinaria.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(maquinaria.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(maquinaria.this, MainActivity.class));
                break;

        }
        // drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
    }

}