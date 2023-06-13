package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class trabajo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button buttonMandarTrabajo;
    TextView editTextMandarTrabajo, editTextMinPaladas;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);

        setNavigationViewListener();

        buttonMandarTrabajo = findViewById(R.id.buttonMandarTrabajo);
        editTextMandarTrabajo = findViewById(R.id.editTextMandarTrabajo);
        editTextMinPaladas = findViewById(R.id.editTextMinPaladas);

        buttonMandarTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String collectionPath = "Operarios";
                String operarioId = editTextMandarTrabajo.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection(collectionPath);
                Map<String, String> listaCosasOperarios = new HashMap<>();
                Query query = collectionRef.whereEqualTo("numero_empleado", operarioId);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombreMaquina = document.getString("nombre_maquina");
                            String nombreOperario = document.getString("nombre_operario");

                            listaCosasOperarios.put("nombre_maquina", nombreMaquina);
                            listaCosasOperarios.put("nombre_operario", nombreOperario);
                        }

                        Map<String, Object> datosOperarios = new HashMap<>();
                        datosOperarios.put("fecha_trabajo", new Date(2002 - 1901, 12, 20));
                        datosOperarios.put("minimo_paladas", editTextMinPaladas.getText().toString());
                        datosOperarios.put("nombre_maquina", listaCosasOperarios.get("nombre_maquina"));
                        datosOperarios.put("nombre_operario", listaCosasOperarios.get("nombre_operario"));
                        datosOperarios.put("ubicacion", "");

                        Log.d(TAG, "mapa datos a ver si va: " + datosOperarios);

                        db.collection("trabajosOperarios").add(datosOperarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), listaCosasOperarios.get("nombre_operario") + " trabajo registrado correctamente", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {
                        Log.d(TAG, "Error al obtener los operarios: ", task.getException());
                    }
                });


            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(trabajo.this, InicioAdmin.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(trabajo.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(trabajo.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(trabajo.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(trabajo.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(trabajo.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(trabajo.this, MainActivity.class));
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