package com.example.administrador.drivehouse.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.models.Cliente;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.ui.actividaes.DetailActivity;
import com.example.administrador.drivehouse.ui.actividaes.UpdateActivity;

import java.util.List;


/**
 * Created by Jhonatan Quilca
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>
        implements ItemClickListener {


    /**
     * Lista de objetos Cliente que representan la fuente de datos
     * de inflado
     */
    private List<Cliente> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;


    public ClienteAdapter(List<Cliente> items, Context context) {
        this.context = context;
        this.items = items;
    }

    public List<Cliente> getItems() {
        return items;
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
    public void onBindViewHolder(final ClienteViewHolder viewHolder, final int i) {
        viewHolder.nombre_completo.setText(items.get(i).getNombre_completo());
        viewHolder.documento.setText(items.get(i).getDocumento());
        viewHolder.deuda.setText("Deuda: $" + items.get(i).getDeuda());
        viewHolder.fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Editar " + items.get(i).getId(), Toast.LENGTH_SHORT).show();
                Activity activity = (Activity) context;
                String id = items.get(i).getId();
                Intent i = new Intent(activity, UpdateActivity.class);
//                        i.putExtra(EXTRA_ID, extra);
                i.putExtra(Constantes.EXTRA_ID, id);
                activity.startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);

            }
        });


    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText((Activity) context, items.get(position).getId(), Toast.LENGTH_SHORT).show();
        DetailActivity.launch((Activity) context, items.get(position).getId());
    }


    public static class ClienteViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre_completo;
        public TextView documento;
        public TextView deuda;
        public ItemClickListener listener;
        public com.software.shell.fab.FloatingActionButton fab_edit;

        public ClienteViewHolder(View v, final ItemClickListener listener) {
            super(v);
            nombre_completo = (TextView) v.findViewById(R.id.nombre_completo);
            documento = (TextView) v.findViewById(R.id.documento);
            deuda = (TextView) v.findViewById(R.id.deuda);
            this.listener = listener;
            fab_edit = (com.software.shell.fab.FloatingActionButton) v.findViewById(R.id.fab_edit);


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

