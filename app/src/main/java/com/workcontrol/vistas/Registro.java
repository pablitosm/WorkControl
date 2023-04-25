package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.workcontrol.R;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText TextoDNI, TextoNombre, TextoApellido, TextoCorreo, TextoContrasegna;
    Button buttonRegistrar;

    FirebaseDatabase nodoRaiz;
    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        buttonRegistrar = findViewById(R.id.buttonRegistro);

        nodoRaiz = FirebaseDatabase.getInstance();
        referencia = nodoRaiz.getReference();

        TextoDNI = findViewById(R.id.editTextDNI);
        TextoNombre = findViewById(R.id.editTextNombre);
        TextoApellido = findViewById(R.id.editTextApellido);
        TextoCorreo = findViewById(R.id.editTextCorreo);
        TextoContrasegna = findViewById(R.id.editTextTextContrasegna);

        buttonRegistrar = findViewById(R.id.buttonRegistro);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getDNI = TextoDNI.getText().toString().trim();
                String getNombre = TextoNombre.getText().toString().trim();
                String getApellido = TextoApellido.getText().toString().trim();
                String getCorreo = TextoCorreo.getText().toString().trim();
                String getContrasegna = TextoContrasegna.getText().toString().trim();

                HashMap<String, Object> mapica = new HashMap<>();
                    mapica.put("DNI", getDNI);
                    mapica.put("Nombre", getNombre);
                    mapica.put("Apellido", getApellido);
                    mapica.put("Correo", getCorreo);
                    mapica.put("Contrasegna", getContrasegna);

                    referencia.child("users")
                            .child(getNombre)
                            .setValue(mapica)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Dato añadido correctamente", Toast.LENGTH_LONG).show();

                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error al añadir el dato", Toast.LENGTH_LONG).show();
                                }
                            });
            }
        });
    }
}