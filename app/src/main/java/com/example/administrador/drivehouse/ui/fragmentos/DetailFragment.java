package com.example.administrador.drivehouse.ui.fragmentos;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.models.Cliente;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    /*
  Etiqueta de valor extra
   */
    private static final String EXTRA_ID = "IDCLIENTE";

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailFragment.class.getSimpleName();

    /*
   Instancias de Views
    */
//    private ImageView cabecera;
    private CollapsingToolbarLayout collapser;
    private TextView nombre;
    private TextView apellido;
    private TextView documento;
    private TextView telefono;
    private TextView celular;
    private TextView email;
    private TextView usuario_creacion;
    private TextView fecha_creacion;
    private com.software.shell.fab.FloatingActionButton editButton;

    private String extra;
    private Gson gson = new Gson();


    public DetailFragment() {
    }

    public static DetailFragment createInstance(String idCliente) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, idCliente);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        AppCompatActivity suportBar = ((AppCompatActivity) getActivity());
        setToolbar(v, suportBar);//crea un nuevo toolbar
        if (suportBar.getSupportActionBar() != null) {
            // Setear ícono "X" como Up button
            suportBar.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_action_white_remove);
            suportBar.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        collapser = (CollapsingToolbarLayout) v.findViewById(R.id.collapser);
        // Obtención de views
        nombre = (TextView) v.findViewById(R.id.nombre);
        apellido = (TextView) v.findViewById(R.id.apellido);
        documento = (TextView) v.findViewById(R.id.documento);
        telefono = (TextView) v.findViewById(R.id.telefono);
        celular = (TextView) v.findViewById(R.id.celular);
        email = (TextView) v.findViewById(R.id.email);
        usuario_creacion = (TextView) v.findViewById(R.id.usuario_creacion);
        fecha_creacion = (TextView) v.findViewById(R.id.fecha_creacion);
        //boton para ir a la activiadad de edicion
        editButton = (com.software.shell.fab.FloatingActionButton) v.findViewById(R.id.fab);

//        collapser.setOnScrollChangeListener(new CollapsingToolbarLayout.OnScrollChangeListener(){
//
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });
        // Setear escucha para el fab
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Toast.makeText(getActivity(), "Update ", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getActivity(), UpdateActivity.class);
////                        i.putExtra(EXTRA_ID, extra);
//                        i.putExtra(Constantes.EXTRA_ID, extra);
//                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);

                    }
                }
        );

        // Obtener extra del intent de envío
        extra = getArguments().getString(EXTRA_ID);

        cargarDatos();
        return v;
    }

    private void setToolbar(View v, AppCompatActivity suportBar) {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        Toast.makeText(getApplicationContext(),toolbar.getId()+" "+toolbar.get)
        suportBar.setSupportActionBar(toolbar);
    }


    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service

        String newURL = Constantes.VIEW + extra;

//        Toast.makeText(getContext(), "Url:" + newURL, Toast.LENGTH_SHORT).show();
        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        (String) null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {

            Boolean success = Boolean.valueOf(response.getString("success"));
            if (success) {
                // Obtener objeto "meta"
                JSONObject object = response.getJSONObject("data");

                //Parsear objeto
                Cliente cl = gson.fromJson(object.toString(), Cliente.class);

                // Seteando valores en los views
                collapser.setTitle(cl.getNombre_completo());

                nombre.setText(cl.getNombre());
                apellido.setText(cl.getApellido());
                documento.setText(cl.getDocumento());
                telefono.setText(cl.getTelefono());
                celular.setText(cl.getCelular());
                String emailStr = cl.getEmail_1() + "";
                if (!emailStr.equals("")) {
                    emailStr = cl.getEmail_1();
                } else {
                    emailStr = cl.getEmail_2();
                }
                email.setText(emailStr);
                usuario_creacion.setText(cl.getUsuario_creacion_id());
                fecha_creacion.setText(cl.getFecha_creacion());
            } else {
                Toast.makeText(
                        getActivity(),
                        "Error al cargar Información",
                        Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
