package com.workcontrol.vistas;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.MainActivity;
import com.workcontrol.R;
import com.workcontrol.modelo.MaquinariaModelo;
import com.workcontrol.modelo.OperariosModelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class trabajoEnCurso extends AppCompatActivity {

    public List<ListElement> trabajoEnCursoMapa = new ArrayList<>();
    public static List<ListElement> listaElemento = new ArrayList<>();
    Button buttonEnviarPaladas;
    FirebaseFirestore database;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    Intent intent = getIntent();
    double contadorPaladas = 0;
    TextView textViewLlevas, textViewQuedan, textViewToneladas, textViewNombreOper;
    EditText editTextRegistrarPaladas;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo_en_curso);



        // recibirIntent(intent);

        database = FirebaseFirestore.getInstance();

        textViewLlevas = findViewById(R.id.textViewLlevas);
        textViewQuedan = findViewById(R.id.textViewQuedan);
        textViewToneladas = findViewById(R.id.textViewToneladas);
        textViewNombreOper = findViewById(R.id.textViewNombreOper);
        buttonEnviarPaladas = findViewById(R.id.buttonEnviarPaladas);

        editTextRegistrarPaladas = findViewById(R.id.editTextRegistrarPaladas);

        buttonEnviarPaladas.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                textViewNombreOper.setText("Bienvenido " + listaElemento.get(0).getNombre_operario());

                Log.d(TAG, "listaElemento: " + listaElemento);


                String minimoPaladas = listaElemento.get(0).getMinimo_paladas();
                String paladas = editTextRegistrarPaladas.getText().toString();

                double paladasDouble = Double.parseDouble(paladas);
                double minimoPaladasDouble = Double.parseDouble(minimoPaladas);

                contadorPaladas += paladasDouble;

//                contadorPaladas = Integer.parseInt(paladas)  + contadorPaladas;
//                textViewLlevas.setText(contadorPaladas);

                if (contadorPaladas >= minimoPaladasDouble) {

                    LocalTime horaActual = LocalTime.now();
                    LocalDate diaActual = LocalDate.now();

                    int hora = horaActual.getHour();

                    String dia = String.valueOf(diaActual.getDayOfMonth());
                    String mes = String.valueOf(diaActual.getMonthValue());
                    String agno = String.valueOf(diaActual.getYear());


                    textViewQuedan.setText("Paladas completadas...");
                    Toast.makeText(getApplicationContext(), "has llegado amigo", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "contadorPaladas " + contadorPaladas);

                    double resultadoOperacion = contadorPaladas * 2;

                    Map<String, String> datosOperarios = new HashMap<>();
                    datosOperarios.put("cantidad_material", String.valueOf(resultadoOperacion));
                    datosOperarios.put("fecha_fin", dia + "/" + mes + "/" + agno);
                    datosOperarios.put("fecha_inicio", dia + "/" + mes + "/" + agno);
                    datosOperarios.put("nombre_maquina", listaElemento.get(0).getNombre_maquina());
                    datosOperarios.put("nombre_operario", listaElemento.get(0).getNombre_operario());
                    datosOperarios.put("numero_cargas", String.valueOf(contadorPaladas));
                    if (hora > 21 || hora < 6 ) {
                        datosOperarios.put("turno", "Noche");
                    } else {
                        datosOperarios.put("turno", "Día");
                    }


                    database.collection("Turnos").add(datosOperarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "registrado correctamente", Toast.LENGTH_LONG).show();

                            CollectionReference maquinasRef = database.collection("trabajosOperarios");

                            maquinasRef.whereEqualTo("nombre_maquina", listaElemento.get(0).getNombre_maquina())
                                    .get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                // Obtén el ID del documento encontrado
                                                String documentId = document.getId();

                                                // Actualiza el campo estado en el documento
                                                database.collection("trabajosOperarios").document(documentId)
                                                        .update("estado_trabajo", "Cerrada")
                                                        .addOnSuccessListener(aVoid -> {
                                                            // La actualización se realizó correctamente
                                                            Log.d("Firestore", "Documento actualizado con éxito");
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            // Ocurrió un error al actualizar el documento
                                                            Log.e("Firestore", "Error al actualizar el documento", e);
                                                        });
                                            }
                                        } else {
                                            // Ocurrió un error al realizar la consulta
                                            Log.e("Firestore", "Error al realizar la consulta", task.getException());
                                        }
                                    });





                            finish();
                            startActivity(new Intent(trabajoEnCurso.this, InicioUsuario.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                        }
                    });



                } else {
                    textViewQuedan.setText("Necesitas hacer mínimo " + (minimoPaladasDouble - contadorPaladas) + " más");
                    String nombre_operario = listaElemento.get(0).getNombre_operario();

                }

            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void recibirIntent(Intent intent) {

        String color = intent.getStringExtra("color");
        String estado_trabajo = intent.getStringExtra("estado_trabajo");
        String minimo_paladas = intent.getStringExtra("minimo_paladas");
        Date fecha_trabajo;
        String nombre_maquina = intent.getStringExtra("nombre_maquina");
        String nombre_operario = intent.getStringExtra("nombre_operario");

        List<String> listaStrings = new ArrayList<>();
        listaStrings.add(color);
        listaStrings.add(estado_trabajo);
        listaStrings.add(minimo_paladas);
        listaStrings.add(nombre_maquina);
        listaStrings.add(nombre_operario);

        listaElemento.add(new ListElement(color, estado_trabajo, new Date(1901, 12, 12) ,minimo_paladas, nombre_maquina, nombre_operario));
        Log.d(TAG, "hola listaElemento" + listaElemento);
        // Utilizar el dato recibido
        Log.d(TAG, "recibirIntent: " + listaStrings);
    }
}