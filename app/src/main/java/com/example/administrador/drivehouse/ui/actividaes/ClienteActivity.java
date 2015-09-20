package com.example.administrador.drivehouse.ui.actividaes;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.ui.fragmentos.ClientesFragment;
import com.example.administrador.drivehouse.web.VolleySingleton;

import org.json.JSONObject;

public class ClienteActivity extends AppCompatActivity {
    ClientesFragment cf = new ClientesFragment();
    SearchView campoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
//        Toast.makeText(
//                getApplicationContext(),
//                "Inicion" + getSupportActionBar().toString(),
//                Toast.LENGTH_LONG).show();
//        inicializacion del fregmento principal

//        if (getSupportActionBar() != null)


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_cliente, cf, "ClientesFragment").commit();

        }
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_action_white_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

        MenuItem searchItem = menu.findItem(R.id.buscar);
        campoBusqueda = (SearchView) MenuItemCompat.getActionView(searchItem);
        campoBusqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!s.isEmpty()) {
                    cargarBusquedaAdaptador(s);
                } else {
//                    Toast.makeText(getApplicationContext(), "Nada que buscar..." + s, Toast.LENGTH_SHORT).show();
                    cf.cargarAdaptador();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    cargarBusquedaAdaptador(s);
                } else {
                    cf.cargarAdaptador();

//                    Toast.makeText(getApplicationContext(), "Nada que buscar..." + s, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.buscar:
//                Toast.makeText(getApplicationContext(), "buscar", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void cargarBusquedaAdaptador(String parametro) {
        String url = Constantes.SEARCH + parametro;
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        (String) null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
//                                        procesarRespuesta(response);
                                cf.actualizarRespuesta(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error Volley: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }
}
