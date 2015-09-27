package com.example.administrador.drivehouse.ui.fragmentos;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.example.administrador.drivehouse.models.Cliente;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.tools.MyUtil;
import com.example.administrador.drivehouse.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

    /*
 Etiqueta de depuración
  */
    private static final String TAG = UpdateFragment.class.getSimpleName();

    /*
    Etiqueta de valor extra para modo edición
     */
    private static final String EXTRA_ID = "IDCLIENTE";

    /*
Valor del argumento extra
 */
    private String idCliente;

    /**
     * Es la Cliente obtenida como respuesta de la petición HTTP
     */
    private Cliente clienteOriginal;

    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();

    /*controles*/
    private EditText input_nombre;
    private EditText input_apellido;
    private EditText input_docuemento;
    private EditText input_telefomo;
    private EditText input_celular;
    private EditText input_email_1;
    private EditText input_email_2;


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
        UpdateFragment detailFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, extra);
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

        // Obtener valor extra
        idCliente = getArguments().getString(EXTRA_ID);
//        Toast.makeText(getContext(), EXTRA_ID + " " + idCliente, Toast.LENGTH_LONG).show();
        if (idCliente != null) {
            cargarDatos();
        }

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
                saveCliente();
                return true;
            case R.id.action_discart: // boton menu guardar (ok)
                mostrarDialogo(R.string.dialog_descartar_msg);
                return true;
            case R.id.action_delete: // boton menu guardar (ok)
                mostrarDialogo(R.string.dialog_eliminar_msg);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo idCliente como parámetro a la URL
        String newURL = Constantes.UPDATE + idCliente;

        // Consultar el detalle del Cliente
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        newURL,
                        (String) null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                // Procesa la respuesta GET_BY_ID
                                procesarRespuestaGet(response);
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
     * Procesa la respuesta de obtención obtenida desde el sevidor     *
     */
    private void procesarRespuestaGet(JSONObject response) {

        try {
            Boolean success = Boolean.valueOf(response.getString("success"));
            if (success) {
                // Obtener objeto
                JSONObject object = response.getJSONObject("data");
                // Guardar instancia
                clienteOriginal = gson.fromJson(object.toString(), Cliente.class);
                // Setear valores del Cliente
                cargarViews(clienteOriginal);

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


    /**
     * Carga los datos iniciales del formulario con los
     * atributos de un objeto {@link Cliente}
     *
     * @param cliente Instancia
     */
    private void cargarViews(Cliente cliente) {
        // Seteando valores de la respuesta
        input_nombre.setText(cliente.getNombre());
        input_apellido.setText(cliente.getApellido());
        input_docuemento.setText(cliente.getDocumento());
        input_telefomo.setText(cliente.getTelefono());
        input_celular.setText(cliente.getCelular());
        input_email_1.setText(cliente.getEmail_1());
        input_email_2.setText(cliente.getEmail_2());
    }


    /**
     * Guarda los cambios de un Cliente editada.
     * <p/>
     * Si está en modo inserción, entonces crea una nueva
     * Cliente en la base de datos
     */
    private void saveCliente() {
        String newURL = Constantes.UPDATE + idCliente;
        final String nombre = input_nombre.getText().toString();
        final String apellido = input_apellido.getText().toString();
        final String docuemento = input_docuemento.getText().toString();
        final String telefomo = input_telefomo.getText().toString();
        final String celular = input_celular.getText().toString();
        final String email_1 = input_email_1.getText().toString();
        final String email_2 = input_email_2.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo pra convertir a jason
        map.put("id", idCliente);
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
                        newURL,
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
//        Toast.makeText(getContext(),"respuesta:"+response.toString(),Toast.LENGTH_LONG).show();
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
        } catch (JSONException e) {
            Toast.makeText(
                    getActivity(),
                    "Error de Codificacion " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Muestra un diálogo de confirmación, cuyo mensaje esta
     * basado en el parámetro identificador de Strings
     *
     * @param id Parámetro
     */
    private void mostrarDialogo(int id) {
        DialogFragment dialogo = ConfirmDialogFragment.
                createInstance(
                        getResources().
                                getString(id));
        dialogo.show(getFragmentManager(), "ConfirmDialog");
    }

    /**
     * Procesa todos las tareas para eliminar
     * un Cliente en la aplicación. Este método solo se usa
     * en la edición
     */
    public void eliminarCliente() {

        String newUrl = Constantes.DELETE + idCliente;
        // Eliminar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newUrl,
                        (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta
                                procesarRespuestaEliminar(response);

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
     * Procesa la respuesta de eliminación obtenida desde el sevidor
     */
    private void procesarRespuestaEliminar(JSONObject response) {

        try {
            Boolean success = Boolean.valueOf(response.getString("success"));
            if (success) {
                Toast.makeText(
                        getActivity(),
                        "Eliminación exitosa",
                        Toast.LENGTH_LONG).show();
                // Enviar código de éxito
                getActivity().setResult(203);
                // Terminar actividad
                getActivity().finish();

            } else {

                // Mostrar mensaje
                Toast.makeText(
                        getActivity(),
                        "Eliminación fallida",
                        Toast.LENGTH_LONG).show();
                // Enviar código de falla
                getActivity().setResult(Activity.RESULT_CANCELED);
                // Terminar actividad
                getActivity().finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}