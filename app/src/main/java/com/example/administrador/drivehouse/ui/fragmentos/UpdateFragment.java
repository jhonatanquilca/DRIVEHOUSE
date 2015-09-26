package com.example.administrador.drivehouse.ui.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.models.Cliente;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

    private static final String EXTRA_ID = "IDCLIENTE";

    /*controles*/
    private EditText input_nombre;
    private EditText input_apellido;
    private EditText input_docuemento;
    private EditText input_telefomo;
    private EditText input_celular;
    private EditText input_email_1;
    private EditText input_email_2;

    /*
    Valor del argumento extra
     */
    private String idMeta;

    private Cliente clienteOriginal;


    public UpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Crea un nuevo fragmento basado en un argumento
     *
     * @param extra Argumento de entrada
     * @return Nuevo fragmento
     */
    public static Fragment createInstance(String extra) {
        UpdateFragment detailFragment= new UpdateFragment();
        Bundle bundle= new Bundle();
        bundle.putString(EXTRA_ID,extra);
        detailFragment.setArguments(bundle);
        return detailFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_form, container, false);

        input_nombre = (EditText) v.findViewById(R.id.input_nombre);
        input_apellido = (EditText) v.findViewById(R.id.input_apellido);
        input_docuemento = (EditText) v.findViewById(R.id.input_documento);
        input_telefomo = (EditText) v.findViewById(R.id.input_telefono);
        input_celular = (EditText) v.findViewById(R.id.input_celular);
        input_email_1 = (EditText) v.findViewById(R.id.input_email_1);
        input_email_2 = (EditText) v.findViewById(R.id.input_email_2);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//menu barra
//        menu.removeItem(R.id.action_delete); // elimina la accion del menu oculto

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: // boton menu guardar (ok)
//                saveCliente();
                return true;
            case R.id.action_discart: // boton menu guardar (ok)
                getActivity().finish();
                return true;
            case R.id.action_delete: // boton menu guardar (ok)
                Toast.makeText(getContext(), "Se eliminara: ", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
