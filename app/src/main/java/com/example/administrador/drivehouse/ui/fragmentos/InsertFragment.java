package com.example.administrador.drivehouse.ui.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.tools.MyUtil;
import com.example.administrador.drivehouse.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment {
    /**
     * Etiqueta para depuración
     */
    private static final String TAG = InsertFragment.class.getSimpleName();
    /*
    controles
     */

    EditText input_nombre;
    EditText input_apellido;
    EditText input_docuemento;
    EditText input_telefomo;
    EditText input_celular;
    EditText input_email_1;
    EditText input_email_2;

    public InsertFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
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
        menu.removeItem(R.id.action_delete); // elimina la accion del menu oculto

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: // boton menu guardar (ok)
                saveCliente();
                return true;
            case R.id.action_discart: // boton menu guardar (ok)
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Guarda los cambios de un Cliente editada.
     * <p/>
     * Si está en modo inserción, entonces crea un nuevo
     * Cliente en la base de datos
     */
    private void saveCliente() {
        final String nombre = input_nombre.getText().toString();
        final String apellido = input_apellido.getText().toString();
        final String docuemento = input_docuemento.getText().toString();
        final String telefomo = input_telefomo.getText().toString();
        final String celular = input_celular.getText().toString();
        final String email_1 = input_email_1.getText().toString();
        final String email_2 = input_email_2.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo pra convertir a jason
        map.put("nombre", nombre);
        map.put("apellido", apellido);
        map.put("documento", docuemento);
        map.put("telefono", telefomo);
        map.put("celular", celular);
        map.put("email_1", email_1);
        map.put("email_2", email_2);


        // Crear nuevo objeto Json basado en el mapa
        JSONObject jsonCliente = new JSONObject(map);


        // Depurando objeto Json...
//        Log.d(TAG, jsonCliente.toString());


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT,
                        jsonCliente,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }


    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {
        try {

            //obtener la psocion success del json
            Boolean success = Boolean.valueOf(response.getString("success"));

            if (success) {
// Mostrar mensaje
                Toast.makeText(
                        getActivity(),
                        "Cliente Creado con exito",
                        Toast.LENGTH_LONG).show();
                getActivity().setResult(Activity.RESULT_OK);
                // Terminar actividad
                getActivity().finish();
            } else {
                try {

                    Map<String, String> maping = new MyUtil().toMap(response.getJSONObject("errors"));

                    for (String key : maping.keySet()) {
//                        Toast.makeText(
//                                getActivity(),
//                                 key+"=>" + maping.get(key).replace("\"","").replace("[","").replace("]",""),
//                                Toast.LENGTH_LONG).show();
                        EditText campo = null;
                        switch (key) {
                            case "nombre":
                                campo = (EditText) getView().findViewById(R.id.input_nombre);
                                campo.setHint(maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", ""));
                                campo.setHintTextColor(getResources().getColor(R.color.danger));
                                break;
                            case "apellido":
                                campo = (EditText) getView().findViewById(R.id.input_apellido);
                                campo.setHint(maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", ""));
                                campo.setHintTextColor(getResources().getColor(R.color.danger));
                                campo.setSingleLine();
                                break;
                            case "nombre_apellido":
                                Toast.makeText(getContext(), maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", ""), Toast.LENGTH_LONG).show();
                                break;
                            case "email_1":
                                campo = (EditText) getView().findViewById(R.id.input_email_1);
                                campo.setTextColor(getResources().getColor(R.color.danger));
                                Toast.makeText(getContext(), maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", ""), Toast.LENGTH_LONG).show();
                                break;
                            case "email_2":
                                campo = (EditText) getView().findViewById(R.id.input_email_2);
                                campo.setTextColor(getResources().getColor(R.color.danger));
                                Toast.makeText(getContext(), maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", ""), Toast.LENGTH_LONG).show();
                                break;
                        }

                    }


                } catch (JSONException e1) {
                    Toast.makeText(
                            getActivity(),
                            "Erros: " + response.getString("errors"),
                            Toast.LENGTH_LONG).show();
                }

            }
        } catch (
                JSONException e
                )

        {
            Toast.makeText(
                    getActivity(),
                    "Error de Codificacion " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }


}
