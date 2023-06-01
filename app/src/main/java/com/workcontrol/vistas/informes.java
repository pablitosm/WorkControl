package com.workcontrol.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;

import java.util.ArrayList;

public class informes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
        setNavigationViewListener();
        PieChart pieChart = findViewById(R.id.pieChart);
//        BarChart barChart = findViewById(R.id.barChart);

        database  = FirebaseFirestore.getInstance();
        // CollectionReference documentReference = database.collection("Informes").get();


        ArrayList<PieEntry> pruebaGrafico = new ArrayList<>();
        pruebaGrafico.add(new PieEntry(120, "prueba1"));
        pruebaGrafico.add(new PieEntry(234, "prueba2"));
        pruebaGrafico.add(new PieEntry(345,  "prueba3"));
        pruebaGrafico.add(new PieEntry(3, "prueba4"));

//        ArrayList<BarEntry> pruebaGrafico1 = new ArrayList<>();
//        pruebaGrafico1.add(new BarEntry(120, 234));
//        pruebaGrafico1.add(new BarEntry(234, 345));
//        pruebaGrafico1.add(new BarEntry(345,  34));
//        pruebaGrafico1.add(new BarEntry(3, 123));

//        BarDataSet barDataSet = new BarDataSet(pruebaGrafico1, "Prueba gráfico");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);

//        BarData barData = new BarData(barDataSet);
//
//        barChart.setFitBars(true);
//        barChart.setData(barData);
//        barChart.getDescription().setText("Hola");
//        barChart.animateY(2000);


        PieDataSet pieDataSet = new PieDataSet(pruebaGrafico, "Prueba gráfico");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("Prueba gráfico");
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

}