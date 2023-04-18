package com.workcontrol.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.workcontrol.R;

public class Registro extends AppCompatActivity {

    EditText TextoDNI, TextoNombre, TextoApellido, TextoCorreo, TextoContrasegna;
    Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

//        TextoDNI = findViewById();
//        TextoNombre = findViewById();
//        TextoApellido = findViewById();
//        TextoCorreo = findViewById();
//        TextoContrasegna = findViewById();
    }
}