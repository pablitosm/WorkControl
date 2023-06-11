package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.InformesModelo;

import java.util.ArrayList;

public class informes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseFirestore database;
    public ArrayList<InformesModelo> informesLista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
        setNavigationViewListener();
        database  = FirebaseFirestore.getInstance();
        recuperarDatosDB();
    }

    public void recuperarDatosDB() {
        database.collection("Informes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {

                        InformesModelo miObjeto = document.toObject(InformesModelo.class);
                        informesLista.add(new InformesModelo(miObjeto.getCantidad_material()));
                    }
                    dibujar();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }


        });
    }


    public void dibujar() {
        ArrayList<Float> listaFloats = new ArrayList<>();
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> pruebaGrafico = new ArrayList<>();

        for (int i = 0; i < informesLista.size(); i++) {
            listaFloats.add(informesLista.get(i).getCantidad_material());

        }
        for (int i = 0; i < listaFloats.size(); i++) {
            pruebaGrafico.add(new PieEntry(listaFloats.get(i), "Toneladas"));

        }


        Log.d(TAG, "listaFloats" + listaFloats);


        PieDataSet pieDataSet = new PieDataSet(pruebaGrafico, "Toneladas extraidas");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("Toneladas extraidas");
        pieChart.animate();
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(informes.this, InicioAdmin.class));
                break;
            case R.id.panel_control:
                startActivity(new Intent(informes.this, panel_control.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(informes.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(informes.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(informes.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(informes.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(informes.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(informes.this, MainActivity.class));
                break;

        }
        // drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class COLORES_PERSO {
    }
}