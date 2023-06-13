package com.workcontrol.vistas;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.workcontrol.R;

public class eliminarMaquina extends AppCompatActivity {

    Button buttonAtras1, buttonEliminarMaquina;
    TextView editTextMatriculaBorrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_maquina);

        buttonEliminarMaquina = findViewById(R.id.buttonEliminarMaquina);
        editTextMatriculaBorrar = findViewById(R.id.editTextMatriculaBorrar);

        buttonAtras1 = findViewById(R.id.buttonAtras1);

        String collectionPath = "Maquinaria";
        String campoBusqueda = "matricula";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collectionPath);

        buttonEliminarMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String valorBusqueda = editTextMatriculaBorrar.getText().toString();

                Query query = collectionRef.whereEqualTo(campoBusqueda, valorBusqueda);
                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();
                            // Hacer algo con el ID del documento
                            Log.d(TAG, "Documento encontrado con ID: " + documentId);
                            collectionRef.document(documentId).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "Documento eliminado con ID: " + documentId);
                                        startActivity(new Intent(eliminarMaquina.this, maquinaria.class));
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.d(TAG, "Error al eliminar documento con ID: " + documentId, e);
                                    });
                        }
                    } else {
                        Log.d(TAG, "Error al obtener documentos: ", task.getException());
                    }
                });



            }
        });

        buttonAtras1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(eliminarMaquina.this, maquinaria.class));
            }
        });
    }
}