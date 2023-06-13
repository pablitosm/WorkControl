package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

public class panel_control extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_control);
        setNavigationViewListener();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(panel_control.this, InicioAdmin.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(panel_control.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(panel_control.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(panel_control.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(panel_control.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(panel_control.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(panel_control.this, MainActivity.class));
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