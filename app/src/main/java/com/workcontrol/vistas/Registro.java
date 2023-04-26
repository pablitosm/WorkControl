package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText TextoDNI, TextoNombre, TextoApellido, TextoCorreo, TextoContrasegna;
    Button buttonRegistrar;

    FirebaseDatabase nodoRaiz;
    DatabaseReference referencia;

    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        buttonRegistrar = findViewById(R.id.buttonRegistro);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        TextoDNI = findViewById(R.id.editTextDNI);
        TextoNombre = findViewById(R.id.editTextNombre);
        TextoApellido = findViewById(R.id.editTextApellido);
        TextoCorreo = findViewById(R.id.editTextCorreo);
        TextoContrasegna = findViewById(R.id.editTextTextContrasegna);

        buttonRegistrar = findViewById(R.id.buttonRegistro);
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = TextoDNI.getText().toString();
                String nombre = TextoNombre.getText().toString();
                String apellido = TextoApellido.getText().toString();
                String correo = TextoCorreo.getText().toString();
                String contrasegna = TextoContrasegna.getText().toString();

                auth.createUserWithEmailAndPassword(correo, contrasegna).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Map<String, String> datosUsuarios = new HashMap<>();
                        datosUsuarios.put("dni", dni);
                        datosUsuarios.put("nombre", nombre);
                        datosUsuarios.put("apellido", apellido);
                        datosUsuarios.put("correo", correo);
                        datosUsuarios.put("contrasegna", contrasegna);

                        database.collection("Users").add(datosUsuarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), dni + " registrado correctamente", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(Registro.this, MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registro.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                });


            }
            });
    }
}