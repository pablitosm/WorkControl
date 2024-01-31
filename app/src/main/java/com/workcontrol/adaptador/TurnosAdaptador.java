package com.workcontrol.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workcontrol.R;
import com.workcontrol.modelo.TurnosModelo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TurnosAdaptador extends RecyclerView.Adapter<TurnosAdaptador.ViewHolder> {

    Context context;
    List<TurnosModelo> lista_turnos;

    public TurnosAdaptador (Context context, List<TurnosModelo> lista_turnos) {
        this.context = context;
        this.lista_turnos = lista_turnos;
    }


    @NonNull
    @Override
    public TurnosAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosAdaptador.ViewHolder holder, int position) {
        if (lista_turnos != null && lista_turnos.size() > 0) {
            TurnosModelo turnoss = lista_turnos.get(position);
            holder.Num_cargas.setText(turnoss.getNumero_cargas());
            holder.Maquinaria.setText(turnoss.getNombre_maquina());
            holder.Operario.setText(turnoss.getNombre_operario());
            holder.Fecha_inicio.setText(turnoss.getFecha_inicio());
            holder.Fecha_fin.setText(turnoss.getFecha_fin());
            holder.Turno.setText(turnoss.getTurno());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return lista_turnos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Num_cargas, Maquinaria, Operario, Fecha_inicio, Fecha_fin, Turno;
        public ViewHolder (@NotNull View itemView) {
            super(itemView);

            Num_cargas = itemView.findViewById(R.id.Num_cargas);
            Maquinaria = itemView.findViewById(R.id.Maquinaria);
            Operario = itemView.findViewById(R.id.Operario);
            Fecha_inicio = itemView.findViewById(R.id.Fecha_inicio);
            Fecha_fin = itemView.findViewById(R.id.Fecha_fin);
            Turno = itemView.findViewById(R.id.Turno);
        }
    }
}
