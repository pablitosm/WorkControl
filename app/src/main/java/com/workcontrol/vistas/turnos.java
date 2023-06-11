package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.adaptador.TurnosAdaptador;
import com.workcontrol.modelo.TurnosModelo;
import com.workcontrol.modelo.UsuarioModelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class turnos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore database;

    public List<TurnosModelo> turnosMapa = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);
        database = FirebaseFirestore.getInstance();
        setNavigationViewListener();
        recuperarDatosDBPrueba();


    }


    public void recuperarDatosDBPrueba() {

        database.collection("Turnos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {

                        TurnosModelo miObjeto = document.toObject(TurnosModelo.class);


                        turnosMapa.add(new TurnosModelo(miObjeto.getNumero_cargas(), miObjeto.getNombre_maquina(),
                                miObjeto.getNombre_operario(), miObjeto.getFecha_inicio(), miObjeto.getFecha_fin(), miObjeto.getTurno()));


                   }

                    showTableLayout();

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void showTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);  //Table layout
        TableRow tbrow0 = new TableRow(this); //Table row for headers

        //Table Headers
        TextView tv1 = new TextView(this);
        tv1.setText(" Operario ");
        tv1.setTextSize(40);
        tv1.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Maquina ");
        tv2.setTextSize(40);
        tv2.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Inicio ");
        tv3.setTextSize(40);
        tv3.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv3.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Fin ");
        tv4.setTextSize(40);
        tv4.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv4.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText(" Turno ");
        tv5.setTextSize(40);
        tv5.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv5.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText(" Cargas ");
        tv6.setTextSize(40);
        tv6.setTextColor(getResources().getColor(R.color.textoNaranja));
        tv6.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv6);

        //End of Table Headers
        //Add to the tablelayout
        stk.addView(tbrow0);

        //Below is the Table data with 4 columns
        for (int i = 0; i < turnosMapa.size(); i++) {
            TableRow tbrow = new TableRow(this); //Table row for data

            TextView t1v = new TextView(this);
            t1v.setText(turnosMapa.get(i).getNombre_operario());
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(turnosMapa.get(i).getNombre_maquina());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(turnosMapa.get(i).getFecha_inicio());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(turnosMapa.get(i).getFecha_fin());
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setText(turnosMapa.get(i).getTurno());
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            stk.addView(tbrow);


            TextView t6v = new TextView(this);
            t6v.setText(turnosMapa.get(i).getNumero_cargas());
            t6v.setTextColor(Color.WHITE);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);


        }

    }

    @SuppressLint("NonConstantResourceId")
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