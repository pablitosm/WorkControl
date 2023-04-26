package com.workcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.modelo.Usuario;
import com.workcontrol.vistas.Inicio;
import com.workcontrol.vistas.Registro;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dni;
    ArrayList<String> nombre;
    ArrayList<String> apellido;
    ArrayList<String> correo;
    ArrayList<String> contrasegna;

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText textoUsuario;
    EditText textoContrasegna;

    Usuario[] usuarioLogin = {new Usuario()};

    Button buttonLogin;
    Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistro = findViewById(R.id.buttonRegistrar);

        textoUsuario = findViewById(R.id.editTextTextPersonName);
        textoContrasegna = findViewById(R.id.editTextTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correoR = textoUsuario.getText().toString();
                String contrasegnaR = textoContrasegna.getText().toString();

                auth.signInWithEmailAndPassword(correoR, contrasegnaR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, Inicio.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Ese usuario no está registrado", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}