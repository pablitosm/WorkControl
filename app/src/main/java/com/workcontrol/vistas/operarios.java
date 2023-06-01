package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

public class operarios extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operarios);
        setNavigationViewListener();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(operarios.this, InicioAdmin.class));
                break;
            case R.id.panel_control:
                startActivity(new Intent(operarios.this, panel_control.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(operarios.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(operarios.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(operarios.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(operarios.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(operarios.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(operarios.this, MainActivity.class));
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