package com.workcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    EditText textoUsuario;
    EditText textoContrasegna;

    Usuario[] usuarioLogin = {new Usuario()};

    Button buttonLogin;
    Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistro = findViewById(R.id.buttonRegistrar);

        textoUsuario = findViewById(R.id.editTextTextPersonName);
        textoContrasegna = findViewById(R.id.editTextTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dni = new ArrayList<>();
                nombre = new ArrayList<>();
                apellido = new ArrayList<>();
                correo = new ArrayList<>();
                contrasegna = new ArrayList<>();

                String dniRecuperado = textoUsuario.getText().toString();
                String contrasengaRecuperado = textoContrasegna.getText().toString();

                Usuario usuarioRecuperado = new Usuario();
                usuarioRecuperado.setDni(dniRecuperado);
                usuarioRecuperado.setContrasegna(contrasengaRecuperado);

                boolean pepe = true;
                if (pepe) {
                    Intent intent = new Intent(MainActivity.this, Inicio.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login exitoso!", Toast.LENGTH_LONG).show();
                    } else {
                    Toast.makeText(getApplicationContext(), "Login incorrecto!", Toast.LENGTH_LONG).show();

                }
            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });


    }
}