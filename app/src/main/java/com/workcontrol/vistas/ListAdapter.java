package com.workcontrol.vistas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workcontrol.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    private View.OnClickListener listener;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_view, null);
        view.setOnClickListener(this);
        return new ListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement> items) {
        mData = items;
    }

    @Override
    public int getItemCount () {
        return mData.size();
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView estado, fecha, minimo, nombreM, nombreO;

        ViewHolder (View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);

            estado = itemView.findViewById(R.id.textViewEstado);
            fecha = itemView.findViewById(R.id.textViewFecha);
            minimo = itemView.findViewById(R.id.textViewPaladas);
            nombreM = itemView.findViewById(R.id.textViewNombreMaquina);
            nombreO = itemView.findViewById(R.id.textViewNombreOperario);
        }

        void bindData(final ListElement item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            estado.setText(item.getEstado_trabajo());
            fecha.setText(item.getFecha_trabajo().toString());
            minimo.setText(item.getMinimo_paladas());
            nombreM.setText(item.getNombre_maquina());
            nombreO.setText(item.getNombre_operario());
        }

    }
}
