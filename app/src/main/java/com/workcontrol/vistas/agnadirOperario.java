package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.R;

import java.util.HashMap;
import java.util.Map;

public class agnadirOperario extends AppCompatActivity {

    EditText fechaContrato, nombreMaquina, nombreOperario, numeroEmpleado, puestoTrabajo, salario;
    Button buttonRegistrar, button2;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agnadir_operario);
        database = FirebaseFirestore.getInstance();
        fechaContrato = findViewById(R.id.editTextFecha);
        nombreMaquina = findViewById(R.id.editTextNombreMaquina);
        nombreOperario = findViewById(R.id.editTextNombreEmpleado);
        numeroEmpleado = findViewById(R.id.editTextNumEmpleado);
        puestoTrabajo = findViewById(R.id.editTextPuesto);
        salario = findViewById(R.id.editTextSalario);

        buttonRegistrar = findViewById(R.id.buttonRegistrarOperario);
        button2 = findViewById(R.id.button2);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha_Contrato = fechaContrato.getText().toString();
                String nombre_Maquina = nombreMaquina.getText().toString();
                String nombre_Operario = nombreOperario.getText().toString();
                String numero_Empleado = numeroEmpleado.getText().toString();
                String puesto_Trabajo = puestoTrabajo.getText().toString();
                Float salario_empleado = Float.parseFloat(salario.getText().toString());



                Map<String, Object> datosOperarios = new HashMap<>();
                datosOperarios.put("fecha_contrato", fecha_Contrato);
                datosOperarios.put("nombre_maquina", nombre_Maquina);
                datosOperarios.put("nombre_operario", nombre_Operario);
                datosOperarios.put("numero_empleado", numero_Empleado);
                datosOperarios.put("puesto_trabajo", puesto_Trabajo);
                datosOperarios.put("salario", salario_empleado);

                database.collection("Operarios").add(datosOperarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), nombre_Operario + " registrado correctamente", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(agnadirOperario.this, operarios.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(agnadirOperario.this, operarios.class));
            }
        });


    }
}