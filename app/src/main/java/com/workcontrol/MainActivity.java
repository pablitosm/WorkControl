package com.workcontrol;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.workcontrol.vistas.InicioAdmin;
import com.workcontrol.vistas.InicioUsuario;
import com.workcontrol.vistas.Registro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText textoUsuario;
    EditText textoContrasegna;

    TextView textoRegistro;

    Button buttonLogin;

    public List<String> listaAdmins = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date currentTime = Calendar.getInstance().getTime();

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonLogin = findViewById(R.id.buttonLogin);

        textoRegistro = findViewById(R.id.textViewRegistro);
        textoUsuario = findViewById(R.id.editTextTextPersonName);
        textoContrasegna = findViewById(R.id.editTextTextContrasegna);

        listaAdmins.add("adriansanmigu@gmail.com");
        listaAdmins.add("samuel@gmail.com");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String correoR = textoUsuario.getText().toString();
                    String contrasegnaR = textoContrasegna.getText().toString();
                    // if (correoR == "adriansanmigu@gmail.com") {
                        auth.signInWithEmailAndPassword(correoR, contrasegnaR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @SuppressLint("RestrictedApi")
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "onComplete: " + correoR + contrasegnaR);
                                if (task.isSuccessful() && listaAdmins.contains(correoR.toLowerCase())) {
                                    startActivity(new Intent(MainActivity.this, InicioAdmin.class));
                                    Toast.makeText(getApplicationContext(), "Logeado como administrador", Toast.LENGTH_LONG).show();
                                } else if (!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                }

                                if (task.isSuccessful() && !listaAdmins.contains(correoR.toLowerCase())) {
                                    startActivity(new Intent(MainActivity.this, InicioUsuario.class));
                                    Toast.makeText(getApplicationContext(), "Logeado como operario", Toast.LENGTH_LONG).show();
                                }
                                if (auth.fetchSignInMethodsForEmail(correoR) == null) {
                                    Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                } catch (IllegalArgumentException iae) {
                    Toast.makeText(getApplicationContext(), "El usuario o la contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });
        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });



    }
}