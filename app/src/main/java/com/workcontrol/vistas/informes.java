package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.InformesModelo;
import com.workcontrol.modelo.TotalMaterialModelo;
import com.workcontrol.modelo.TurnosModelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class informes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseFirestore database;
    public ArrayList<InformesModelo> informesLista = new ArrayList<>();
    public List<TotalMaterialModelo>listaMaterialFecha = new ArrayList<>();
    public List<Entry> listaEntry = new ArrayList<>();


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


        float contador = 0;

        for (int i = 0; i < informesLista.size(); i++) {
            listaFloats.add(informesLista.get(i).getCantidad_material());
            contador += informesLista.get(i).getCantidad_material();

        }
        for (int i = 0; i < listaFloats.size(); i++) {
            pruebaGrafico.add(new PieEntry(listaFloats.get(i), "Toneladas"));

        }

        Calendar calendar = Calendar.getInstance();

        // Obtén el día del mes actual
        int diaDelMes = calendar.get(Calendar.DAY_OF_MONTH);

        Map<String, Float> datosTotalMaterial = new HashMap<>();
        datosTotalMaterial.put("cantidad_material", contador);
        datosTotalMaterial.put("fecha_extraccion", (float) diaDelMes);
        
        database.collection("TotalMaterial").add(datosTotalMaterial).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG, "onComplete: Datos añadidos correctamente" );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error al registrar datos en base de datos");
            }
        });

        database.collection("TotalMaterial").orderBy("fecha_extraccion", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        TotalMaterialModelo miObjeto = document.toObject(TotalMaterialModelo.class);

                        if (miObjeto != null) {
                            listaMaterialFecha.add(new TotalMaterialModelo(miObjeto.getCantidad_material(),
                                    miObjeto.getFecha_extraccion()));
                        }

                    }
                    // aquiii
                    graficoLineas();
                    // aquiii
                } else {
                    Log.d(TAG, "Error al obtener documento/s: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Fallo al obtener documentos");
            }
        });

        PieDataSet pieDataSet = new PieDataSet(pruebaGrafico, "Toneladas extraidas");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.getDescription().setText("Total toneladas extraidas diarias");
        pieChart.animateY(2000);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.getDescription().setTextColor(Color.WHITE);


    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void graficoLineas(){
        Log.d(TAG, "listaMaterialFecha.size: " + listaMaterialFecha.size());

        LineChart lineChart = findViewById(R.id.lineChart);
        for (int i = 0; i < listaMaterialFecha.size() - 1; i++) {
            listaEntry.add(new Entry(listaMaterialFecha.get(i).getFecha_extraccion(),listaMaterialFecha.get(i).getCantidad_material()));
        }

        LineDataSet lineDataSet = new LineDataSet(listaEntry, "Toneladas extraidas");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(16f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(5f);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(true);
        lineChart.getDescription().setText("Total toneladas extraidas por día");
        lineChart.animateY(2000);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getDescription().setTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();

        xAxis.setTextColor(Color.WHITE);
        yAxis.setTextColor(Color.WHITE);
    }
    @SuppressLint("NonConstantResourceId")
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