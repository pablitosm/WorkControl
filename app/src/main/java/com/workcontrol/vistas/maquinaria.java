package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.MaquinariaModelo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class maquinaria extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore database;
    public List<MaquinariaModelo> maquinariaMapa = new ArrayList<>();
    public List<Entry> listaEntry = new ArrayList<>();

    Button buttonAgnadirMaquineto, buttonEliminarMaquineto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_maquinaria);

        buttonAgnadirMaquineto = findViewById(R.id.buttonAgnadirMaquineto);
        buttonEliminarMaquineto = findViewById(R.id.buttonEliminarMaquineto);

        setNavigationViewListener();
        recuperarDatosDBPrueba();

        buttonAgnadirMaquineto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(maquinaria.this, agnadirMaquina.class));
            }
        });

        buttonEliminarMaquineto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(maquinaria.this, eliminarMaquina.class));
            }
        });
    }

    public void showTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main1);  //Table layout
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv1 = new TextView(this);
        tv1.setText(" Fecha fabricacion ");
        tv1.setTextSize(40);
        tv1.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Horas de uso ");
        tv2.setTextSize(40);
        tv2.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Matricula ");
        tv3.setTextSize(40);
        tv3.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv3.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Nombre ");
        tv4.setTextSize(40);
        tv4.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv4.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Potencia ");
        tv5.setTextSize(40);
        tv5.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv5.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv5);
        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < maquinariaMapa.size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            TextView t1v = new TextView(this);
            t1v.setText(maquinariaMapa.get(i).getFecha_fabricacion());
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(maquinariaMapa.get(i).getHoras_uso());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(maquinariaMapa.get(i).getMatricula());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(maquinariaMapa.get(i).getNombre_maquina());
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(maquinariaMapa.get(i).getPotencia() + " cv" + " | " + Math.floor((Integer.parseInt(maquinariaMapa.get(i).getPotencia()) * 0.7355))   + " kw");
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            stk.addView(tbrow);

        }

    }

    public void recuperarDatosDBPrueba() {

            database.collection("Maquinaria").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {

                            MaquinariaModelo miObjeto = document.toObject(MaquinariaModelo.class);

                            maquinariaMapa.add(new MaquinariaModelo(miObjeto.getFecha_fabricacion(), miObjeto.getHoras_uso(),
                                    miObjeto.getMatricula(), miObjeto.getNombre_maquina(), miObjeto.getPotencia()));

                        }
                        Log.d(TAG, "onComplete: " + maquinariaMapa);
                        showTableLayout();
                        dibujar();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    private void dibujar() {
        LineChart lineChart = findViewById(R.id.hola);

        Log.d(TAG, "maquinariaaaa: " + maquinariaMapa);

        for (int i = 0; i < maquinariaMapa.size(); i++) {
            listaEntry.add(new Entry(i, Float.parseFloat(maquinariaMapa.get(i).getHoras_uso())));
        }

        Log.d(TAG, "listaEntry: " + listaEntry);

        LineDataSet lineDataSet = new LineDataSet(listaEntry, "Horas de uso");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(16f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(5f);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(true);
        lineChart.getDescription().setText("Horas de uso");
        lineChart.animateY(2000);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getDescription().setTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();

        xAxis.setTextColor(Color.WHITE);
        yAxis.setTextColor(Color.WHITE);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(maquinaria.this, InicioAdmin.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(maquinaria.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(maquinaria.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(maquinaria.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(maquinaria.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(maquinaria.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(maquinaria.this, MainActivity.class));
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