package com.example.administrador.drivehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.ui.actividaes.ClienteActivity;
import com.example.administrador.drivehouse.web.VolleySingleton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // controles
    public ImageButton botonPrueba;
    public EditText campoTexto;

    //    https://groups.google.com/forum/#!msg/desarrolladores-android/v0URomisIcs/5RN7QVmG7QYJ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_form);
//        botonPrueba = (ImageButton) findViewById(R.id.imageButton);
//        campoTexto = (EditText) findViewById(R.id.editText);

//        botonPrueba.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Realizar petición GET_BY_ID
//                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
//                        new JsonObjectRequest(
//                                Request.Method.GET,
//                                Constantes.VIEW,
//                                (String) null,
//                                new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        // Procesar respuesta Json
////                                        procesarRespuesta(response);
//                                        campoTexto.setText(response.toString());
//
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(getApplicationContext(),"Error Volley: " + error.getMessage(),Toast.LENGTH_LONG).show();
////                                        Log.d(TAG, "Error Volley: " + error.getMessage());
//                                    }
//                                }
//                        )
//                );
//
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_cliente:
                Intent i=new Intent(this, ClienteActivity.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
