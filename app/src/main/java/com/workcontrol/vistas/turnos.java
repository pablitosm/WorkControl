package com.workcontrol.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.workcontrol.R;
import com.workcontrol.adaptador.TurnosAdaptador;
import com.workcontrol.modelo.TurnosModelo;

import java.util.ArrayList;
import java.util.List;

public class turnos extends AppCompatActivity {

    RecyclerView recyclerView;
    TurnosAdaptador turnosAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);

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
}