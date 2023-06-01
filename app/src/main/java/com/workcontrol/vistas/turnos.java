package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.adaptador.TurnosAdaptador;
import com.workcontrol.modelo.TurnosModelo;

import java.util.ArrayList;
import java.util.List;

public class turnos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    TurnosAdaptador turnosAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);
        setNavigationViewListener();
        recyclerView = findViewById(R.id.recycler_view);
        setRecyclerView();

    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        turnosAdaptador = new TurnosAdaptador(this, getList());
        recyclerView.setAdapter(turnosAdaptador);
    }

    private List<TurnosModelo> getList()  {
        List<TurnosModelo> lista_turnos = new ArrayList<>();
        lista_turnos.add(new TurnosModelo(14, "Excavadora 12311", "Pepito Grillo", "30/05/2023 23:15", "30/05/2023 23:16", "Día"));
        lista_turnos.add(new TurnosModelo(59, "Excavadora 45467", "Manolo Lama", "30/05/2023 23:16", "30/05/2023 23:17", "No"));
        return lista_turnos;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(turnos.this, InicioAdmin.class));
                break;
            case R.id.panel_control:
                startActivity(new Intent(turnos.this, panel_control.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(turnos.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(turnos.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(turnos.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(turnos.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(turnos.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(turnos.this, MainActivity.class));
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