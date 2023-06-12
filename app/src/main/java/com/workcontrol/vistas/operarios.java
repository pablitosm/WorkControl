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
import android.view.Gravity;
import android.view.MenuItem;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.OperariosModelo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class operarios extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore database;
    public List<OperariosModelo> operariosMapa = new ArrayList<>();
    public List<Entry> listaEntry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_operarios);

        setNavigationViewListener();
        recuperarDatosDBPrueba();
    }

    @SuppressLint("SetTextI18n")
    public void showTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main1);  //Table layout
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv1 = new TextView(this);
        tv1.setText(" Empleado ");
        tv1.setTextSize(40);
        tv1.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Nombre maquina ");
        tv2.setTextSize(40);
        tv2.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Nombre ");
        tv3.setTextSize(40);
        tv3.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv3.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Fecha de contrato ");
        tv4.setTextSize(40);
        tv4.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv4.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Puesto ");
        tv5.setTextSize(40);
        tv5.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv5.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" Salario ");
        tv6.setTextSize(40);
        tv6.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv6.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv6);
        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < operariosMapa.size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            TextView t1v = new TextView(this);
            t1v.setText(operariosMapa.get(i).getNumero_empleado());
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(operariosMapa.get(i).getNombre_maquina());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(operariosMapa.get(i).getNombre_operario());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(operariosMapa.get(i).getFecha_contrato());
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(operariosMapa.get(i).getPuesto_trabajo());
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this);
            t6v.setText(operariosMapa.get(i).getSalario() + "");
            t6v.setTextColor(Color.WHITE);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);
            stk.addView(tbrow);

        }

    }

    public void recuperarDatosDBPrueba() {

        database.collection("Operarios").orderBy("numero_empleado", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {

                        OperariosModelo miObjeto = document.toObject(OperariosModelo.class);

                        if (miObjeto != null) {
                            operariosMapa.add(new OperariosModelo(miObjeto.getFecha_contrato(), miObjeto.getNombre_maquina(),
                                    miObjeto.getNombre_operario(), miObjeto.getNumero_empleado(), miObjeto.getPuesto_trabajo(), miObjeto.getSalario()));
                        }

                    }
                    Log.d(TAG, "onComplete: " + operariosMapa);
                    showTableLayout();
                    dibujar();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void dibujar() {
        LineChart lineChart = findViewById(R.id.holaaquetal);

        Log.d(TAG, "maquinariaaaa: " + operariosMapa + " | "+  operariosMapa.size());

        Log.d(TAG, "dibujar: " + operariosMapa.get(0).getSalario());

        for (int i = 0; i < operariosMapa.size(); i++) {
            listaEntry.add(new Entry(i, (operariosMapa.get(i).getSalario())));
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

    @SuppressLint("NonConstantResourceId")
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