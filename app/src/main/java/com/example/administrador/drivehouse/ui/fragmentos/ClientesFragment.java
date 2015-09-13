package com.example.administrador.drivehouse.ui.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.models.Cliente;
import com.example.administrador.drivehouse.ui.MetaAdapter;
import com.example.administrador.drivehouse.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ClientesFragment extends Fragment {
    /*
   Etiqueta de depuracion
    */
    private static final String TAG = ClientesFragment.class.getSimpleName();
    /*
        Adaptador del recycler view
         */
    private MetaAdapter adapter;

    /*
    Instancia global del recycler view
     */
    private RecyclerView lista;

    /*
    instancia global del administrador
     */
    private RecyclerView.LayoutManager lManager;

    /*
    Instancia global del FAB
     */
    com.software.shell.fab.FloatingActionButton fab;

    //    instanacion para el json
    private Gson gson = new Gson();

    public ClientesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Toast.makeText(
//                getActivity(),
//                "Fragment",
//                Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);

        lista = (RecyclerView) v.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);

//        usar un administrador para un linear layout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        cargarAdaptador();

        fab = (com.software.shell.fab.FloatingActionButton) v.findViewById(R.id.fab);
//        Asignar escucha al FAB
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Toast.makeText(v.getContext(), "Insert", Toast.LENGTH_SHORT).show();
                                       // Iniciar actividad de inserción
//                                       getActivity().startActivityForResult(
//                                               new Intent(getActivity(), InsertActivity.class), 3);
                                   }
                               }
        );

        return v;
    }

    private void cargarAdaptador() {
//        Toast.makeText(
//                getActivity(),
//                "Carga",
//                Toast.LENGTH_LONG).show();
        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        "http://192.168.1.61/driveworkhouse/cliente/clienteWs/admin",
                        (String) null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
//                                        procesarRespuesta(response);
                                // Procesar la respuesta Json
                                procesarRespuesta(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error Volley: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {

            //obtener la psocion success del json
            Boolean success = Boolean.valueOf(response.getString("success"));

            if (success) {
                JSONArray mensaje = response.getJSONArray("data");
                // Parsear con Gson
                Cliente[] clientes = gson.fromJson(mensaje.toString(), Cliente[].class);
                // Inicializar adaptador
                adapter = new MetaAdapter(Arrays.asList(clientes), getActivity());
                // Setear adaptador a la lista
                lista.setAdapter(adapter);
//                Toast.makeText(
//                        getActivity(),
//                        "Datos",
//                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        getActivity(),
                        "No Hay Datos",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Toast.makeText(
                    getActivity(),
                    "Error de Codificacion",
                    Toast.LENGTH_LONG).show();
        }

    }


}
