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
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.OperariosModelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class trabajo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button buttonMandarTrabajo;
    TextView editTextMandarTrabajo, editTextMinPaladas;
    FirebaseFirestore database;

    public List<OperariosModelo> operariosMapa = new ArrayList<>();
    public List<Entry> listaEntry = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);
        database = FirebaseFirestore.getInstance();

        setNavigationViewListener();
        recuperarDatosDBPrueba();

        buttonMandarTrabajo = findViewById(R.id.buttonMandarTrabajo);
        editTextMandarTrabajo = findViewById(R.id.editTextMandarTrabajo);
        editTextMinPaladas = findViewById(R.id.editTextMinPaladas);

        buttonMandarTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String collectionPath = "Operarios";
                String operarioId = editTextMandarTrabajo.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection(collectionPath);
                Map<String, String> listaCosasOperarios = new HashMap<>();
                Query query = collectionRef.whereEqualTo("numero_empleado", operarioId);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombreMaquina = document.getString("nombre_maquina");
                            String nombreOperario = document.getString("nombre_operario");

                            listaCosasOperarios.put("nombre_maquina", nombreMaquina);
                            listaCosasOperarios.put("nombre_operario", nombreOperario);
                        }

                        Calendar calendar = Calendar.getInstance();
                        int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        int mes = calendar.get(Calendar.MONTH);
                        int anio = calendar.get(Calendar.YEAR);

                        Map<String, Object> datosOperarios = new HashMap<>();
                        datosOperarios.put("fecha_trabajo", new Date(anio - 1901, mes, dia));
                        datosOperarios.put("minimo_paladas", editTextMinPaladas.getText().toString());
                        datosOperarios.put("nombre_maquina", listaCosasOperarios.get("nombre_maquina"));
                        datosOperarios.put("nombre_operario", listaCosasOperarios.get("nombre_operario"));
                        datosOperarios.put("ubicacion", "");
                        datosOperarios.put("estado_trabajo", "En progreso");

                        db.collection("trabajosOperarios").add(datosOperarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Trabajo enviado a " + listaCosasOperarios.get("nombre_operario") + " correctamente" , Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error al registrar operario", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Log.d(TAG, "Error al obtener los operarios: ", task.getException());
                    }
                });
            }
        });
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
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showTableLayout() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main2);  //Table layout
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

    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mapa:
                startActivity(new Intent(trabajo.this, InicioAdmin.class));
                break;
            case R.id.iniciar_trabajo:
                startActivity(new Intent(trabajo.this, trabajo.class));
                break;

            case R.id.informes:
                startActivity(new Intent(trabajo.this, informes.class));
                break;
            case R.id.turnos:
                startActivity(new Intent(trabajo.this, turnos.class));
                break;

            case R.id.maquinaria:
                startActivity(new Intent(trabajo.this, maquinaria.class));
                break;
            case R.id.operarios:
                startActivity(new Intent(trabajo.this, operarios.class));
                break;
            case R.id.cerrar_sesion:
                startActivity(new Intent(trabajo.this, MainActivity.class));
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