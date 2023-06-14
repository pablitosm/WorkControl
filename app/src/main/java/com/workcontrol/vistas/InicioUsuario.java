package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.workcontrol.R;
import com.workcontrol.modelo.MaquinariaModelo;
import com.workcontrol.modelo.UsuarioModelo;

import java.nio.file.ClosedDirectoryStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InicioUsuario extends AppCompatActivity {

    List<ListElement> elements;
    List<ListElement> elements1;
    public List<ListElement> trabajosMapa = new ArrayList<>();
    public List<UsuarioModelo> usuariosMapa = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);
        recuperarDatosDBPrueba();

    }

    public void recuperarDatosDBPrueba() {

        String collectionPath = "Users";
        String documentoId = "9MeKeUuoQhOsivPtbsZkZGTpbX12";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentRef = db.collection(collectionPath).document(documentoId);

        documentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UsuarioModelo miObjeto = document.toObject(UsuarioModelo.class);

                    if (miObjeto != null) {
                        usuariosMapa.add(new UsuarioModelo(miObjeto.getDni(), miObjeto.getNombre(), miObjeto.getApellido(),
                                miObjeto.getCorreo(), miObjeto.getContrasegna(), ""));
                    }

                    Log.d(TAG, "usuariosMapa: " + usuariosMapa.get(0).getNombre() + " " + usuariosMapa.get(0).getApellido());
                    init();
                  
                } else {
                    System.out.println("No se encontró el documento con ID: " + documentoId);
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



                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void init() {
        elements = new ArrayList<>();

        String nombreApellidos = usuariosMapa.get(0).getNombre() + " " + usuariosMapa.get(0).getApellido();


        for (int i = 0; i < trabajosMapa.size(); i++) {

            elements.add(new ListElement("#eea441", trabajosMapa.get(i).getEstado_trabajo(), trabajosMapa.get(i).getFecha_trabajo(),
                    trabajosMapa.get(i).getMinimo_paladas(), trabajosMapa.get(i).getNombre_maquina(), trabajosMapa.get(i).getNombre_operario()));

        }


        ArrayList<ListElement> segundoArray = new ArrayList<>();

// Recorrer el primer array y agregar al segundo array solo los elementos con nombre "Pablo"
        for (ListElement element : elements) {
            if (element.getNombre_operario().equals(nombreApellidos)) {
                segundoArray.add(element);
            }
        }

        Log.d(TAG, "init: " + segundoArray.get(0));


        ListAdapter listAdapter = new ListAdapter(segundoArray, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTrabajos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);



    }
}