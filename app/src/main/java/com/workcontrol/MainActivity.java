package com.workcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.vistas.InicioAdmin;
import com.workcontrol.vistas.Registro;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText textoUsuario;
    EditText textoContrasegna;

    Button buttonLogin;
    Button buttonRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date currentTime = Calendar.getInstance().getTime();

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistro = findViewById(R.id.buttonRegistrar);

        textoUsuario = findViewById(R.id.editTextTextPersonName);
        textoContrasegna = findViewById(R.id.editTextTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String correoR = textoUsuario.getText().toString();
                    String contrasegnaR = textoContrasegna.getText().toString();



                    auth.signInWithEmailAndPassword(correoR, contrasegnaR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, InicioAdmin.class));
                                // hacer que detecte si el usuario existe pero la contraseña es incorrecta
                                // (¡no funciona!)
                            } else if (auth.fetchSignInMethodsForEmail(correoR) == null) {
                                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (IllegalArgumentException iae) {
                    Toast.makeText(getApplicationContext(), "El usuario o la contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });
    }
}