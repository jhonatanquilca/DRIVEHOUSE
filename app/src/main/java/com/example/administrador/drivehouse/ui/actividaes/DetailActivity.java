package com.example.administrador.drivehouse.ui.actividaes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.ui.fragmentos.DetailFragment;

public class DetailActivity extends AppCompatActivity {
    /**
     * Instancia global del CLiente a detallar
     */
    private String idCliente;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity  Contexto desde donde se lanzará
     * @param idCliente Identificador del CLiente a detallar
     */
    public static void launch(Activity activity, String idCliente) {
        Intent intent = getLaunchIntent(activity, idCliente);
        activity.startActivityForResult(intent, Constantes.CODIGO_DETALLE);
    }


    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context   Contexto donde se inicia
     * @param idCliente Identificador del Cliente
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context, String idCliente) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constantes.EXTRA_ID, idCliente);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);



        // Retener instancia
        if (getIntent().getStringExtra(Constantes.EXTRA_ID) != null) {

            idCliente = getIntent().getStringExtra(Constantes.EXTRA_ID);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_cliente, DetailFragment.createInstance(idCliente), "DetailFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CODIGO_ACTUALIZACION) {
            if (resultCode == RESULT_OK) {
                DetailFragment fragment = (DetailFragment) getSupportFragmentManager().
                        findFragmentByTag("DetailFragment");
                fragment.cargarDatos();

                setResult(RESULT_OK); // Propagar código para actualizar
            } else if (resultCode == 203) {
                setResult(203);
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        }
    }
}
