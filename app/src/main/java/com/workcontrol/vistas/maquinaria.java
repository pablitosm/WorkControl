package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.UsuarioModelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class maquinaria extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore database;

    TextView textViewHoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_maquinaria);

        textViewHoal = findViewById(R.id.textView9);

        setNavigationViewListener();
        recuperarDatosDBPrueba();
    }

    public void recuperarDatosDBPrueba() {

            database.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {

                            // Log.d(TAG, document.getId() + " => " + document.getData());
                            UsuarioModelo usuario = document.toObject(UsuarioModelo.class);
                            Map<Object, Object> datosUsuarios = new HashMap<>();
                            datosUsuarios.put("users", usuario);

                            Log.d(TAG, document.getId() + "prueba => " + datosUsuarios);


                            textViewHoal.setText(Objects.requireNonNull(document.getData()).toString());
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
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