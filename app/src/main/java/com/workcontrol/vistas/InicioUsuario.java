package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.R;
import com.workcontrol.modelo.UsuarioModelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InicioUsuario extends AppCompatActivity {

    List<ListElement> elements;
    List<ListElement> elements1;
    public List<ListElement> trabajosMapa = new ArrayList<>();
    public List<UsuarioModelo> usuariosMapa = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);
        recuperarDatosDB();

    }

    public void recuperarDatosDB() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        String collectionPath = "Users";

        Log.d(TAG, "recuperarDatosDBPrueba: " + currentUser);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentRef = db.collection(collectionPath).document("9MeKeUuoQhOsivPtbsZkZGTpbX12");


        documentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UsuarioModelo miObjeto = document.toObject(UsuarioModelo.class);

                    if (miObjeto != null) {
                        usuariosMapa.add(new UsuarioModelo(miObjeto.getDni(), miObjeto.getNombre(), miObjeto.getApellido(),
                                miObjeto.getCorreo(), miObjeto.getContrasegna(), ""));

                    }

                    elements = new ArrayList<>();

                    db.collection("trabajosOperarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {



                                    ListElement miObjeto = document.toObject(ListElement.class);

                                    if (miObjeto != null) {
                                        trabajosMapa.add(new ListElement("#FFFFFF", miObjeto.getEstado_trabajo(), miObjeto.getFecha_trabajo(),
                                                miObjeto.getMinimo_paladas(), miObjeto.getNombre_maquina(), miObjeto.getNombre_operario()));
                                    }

                                }

                                init();



                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });


                    Log.d(TAG, "usuariosMapa: " + usuariosMapa.get(0).getNombre() + " " + usuariosMapa.get(0).getApellido());


                } else {
                    System.out.println("No se encontró el documento con ID: " + currentUser);
                }

            } else {
                System.out.println("Error al obtener el documento: " + task.getException());
            }
        }). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Fallo al o");
            }
        });


    }

    public void init() {

        Log.d(TAG, "recuperarDatosDB:  " + usuariosMapa);

        String nombreApellidos = usuariosMapa.get(0).getNombre() + " " + usuariosMapa.get(0).getApellido();

        Log.d(TAG, "init: " +  nombreApellidos);
        Log.d(TAG, "recuperarDatosDB:  " + usuariosMapa);
        Log.d(TAG, "trabajosMapa: " + trabajosMapa);

        for (int i = 0; i < trabajosMapa.size(); i++) {

            elements.add(new ListElement("#eea441", trabajosMapa.get(i).getEstado_trabajo(), trabajosMapa.get(i).getFecha_trabajo(),
                    trabajosMapa.get(i).getMinimo_paladas(), trabajosMapa.get(i).getNombre_maquina(), trabajosMapa.get(i).getNombre_operario()));

        }

        ArrayList<ListElement> segundoArray = new ArrayList<>();

        for (ListElement element : elements) {
            if (element.getNombre_operario().equals(nombreApellidos) && element.getEstado_trabajo().equals("En progreso")) {
                segundoArray.add(element);
            }
        }
        Log.d(TAG, "init: " + elements);
        Log.d(TAG, "SEGUNDOARRAY: " + segundoArray);
        ListAdapter listAdapter = new ListAdapter(segundoArray, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTrabajos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Seleccion: " + segundoArray
                        .get(recyclerView.getChildAdapterPosition(v)).getMinimo_paladas(), Toast.LENGTH_SHORT).show();

                String color = segundoArray.get(recyclerView.getChildAdapterPosition(v)).getColor();
                String estado_trabajo = segundoArray.get(recyclerView.getChildAdapterPosition(v)).getEstado_trabajo();
                String minimo_paladas = segundoArray.get(recyclerView.getChildAdapterPosition(v)).getMinimo_paladas();
                String nombre_maquina = segundoArray.get(recyclerView.getChildAdapterPosition(v)).getNombre_maquina();
                String nombre_operario = segundoArray.get(recyclerView.getChildAdapterPosition(v)).getNombre_operario();

                Intent intent = new Intent();
                intent.putExtra("color", color);
                intent.putExtra("estado_trabajo", estado_trabajo);
                intent.putExtra("minimo_paladas", minimo_paladas);
                intent.putExtra("nombre_maquina", nombre_maquina);
                intent.putExtra("nombre_operario", nombre_operario);

                // Llamar a la otra clase y pasar el intent
                trabajoEnCurso miClaseDestino = new trabajoEnCurso();
                miClaseDestino.recibirIntent(intent);

                startActivity(new Intent(InicioUsuario.this, trabajoEnCurso.class));
            }
        });
    }
}