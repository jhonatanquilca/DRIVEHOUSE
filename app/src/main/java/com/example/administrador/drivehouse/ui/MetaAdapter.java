package com.example.administrador.drivehouse.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.models.Cliente;

import java.util.List;


/**
 * Created by Administrador on 12/09/2015.
 */
public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.ClienteViewHolder>
        implements ItemClickListener {
    /**
     * Lista de objetos Meta que representan la fuente de datos
     * de inflado
     */
    private List<Cliente> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;


    public MetaAdapter(List<Cliente> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.clientes_item_list, viewGroup, false);
        return new ClienteViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ClienteViewHolder viewHolder, int i) {
        viewHolder.nombre_completo.setText(items.get(i).getNombre_completo());
        viewHolder.documento.setText(items.get(i).getDocumento());
        viewHolder.deuda.setText("Deuda: $"+items.get(i).getDeuda());
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText((Activity) context,items.get(position).getId(),Toast.LENGTH_SHORT).show();
//        DetailActivity.launch(
//                (Activity) context, items.get(position).getIdMeta());
    }


    public static class ClienteViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre_completo;
        public TextView documento;
        public TextView deuda;
        public ItemClickListener listener;

        public ClienteViewHolder(View v, ItemClickListener listener) {
            super(v);
            nombre_completo = (TextView) v.findViewById(R.id.nombre_completo);
            documento = (TextView) v.findViewById(R.id.documento);
            deuda = (TextView) v.findViewById(R.id.deuda);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getPosition());
        }
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}

