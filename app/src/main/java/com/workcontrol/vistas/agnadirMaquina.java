package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.R;

import java.util.HashMap;
import java.util.Map;

public class agnadirMaquina extends AppCompatActivity {

    TextView fechaFabricacion, horasUso, matricula, nombreMaquina, potencia;
    Button buttonRegistrar;
    Button buttonAtras;

    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agnadir_maquina);
        database = FirebaseFirestore.getInstance();

        fechaFabricacion = findViewById(R.id.editTextFechaFab);
        horasUso = findViewById(R.id.editTextHorasUso);
        matricula = findViewById(R.id.editTextMatricula);
        nombreMaquina = findViewById(R.id.editTextNombreMaquineto);
        potencia = findViewById(R.id.editTextPotencia);

        buttonRegistrar = findViewById(R.id.buttonRegistrarMaquina);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fecha_fabricacion = fechaFabricacion.getText().toString();
                String horas_uso = horasUso.getText().toString();
                String matriculaMaquina = matricula.getText().toString();
                String nombre_maquina = nombreMaquina.getText().toString();
                String potenciaMaquina = potencia.getText().toString();

                Map<String, String> datosMaquinas = new HashMap<>();
                datosMaquinas.put("fecha_fabricacion", fecha_fabricacion);
                datosMaquinas.put("horas_uso", horas_uso);
                datosMaquinas.put("matricula", matriculaMaquina);
                datosMaquinas.put("nombre_maquina", nombre_maquina);
                datosMaquinas.put("potencia", potenciaMaquina);

                database.collection("Maquinaria").add(datosMaquinas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), nombre_maquina + " registrado correctamente", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(agnadirMaquina.this, maquinaria.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


        buttonAtras = findViewById(R.id.buttonAtras);

        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(agnadirMaquina.this, maquinaria.class));
            }
        });


    }
}