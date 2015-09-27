package com.example.administrador.drivehouse.ui.actividaes;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.tools.Constantes;
import com.example.administrador.drivehouse.ui.fragmentos.ConfirmDialogFragment;
import com.example.administrador.drivehouse.ui.fragmentos.UpdateFragment;

public class UpdateActivity extends AppCompatActivity implements ConfirmDialogFragment.ConfirmDialogListener {
    /**
     * Etiqueta del valor extra del dialogo
     */
    private static final String EXTRA_NOMBRE = "NOMBRE";

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.transition.zoom_back_in, R.transition.zoom_back_out);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        String extra = getIntent().getStringExtra(Constantes.EXTRA_ID);
        //        Toast.makeText(getApplicationContext(),(getSupportActionBar() != null)+"",Toast.LENGTH_SHORT).show();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_action_white_ok);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        // Creación del fragmento de inserción
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_cliente, UpdateFragment.createInstance(extra), "UpdateFragment")
                    .commit();


        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.zoom_back_in, R.transition.zoom_back_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UpdateFragment fragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");
        if (fragment != null) {
            String extra = dialog.getArguments().getString(EXTRA_NOMBRE);
            String msg = getResources().getString(R.string.dialog_eliminar_msg);
            if (extra.compareTo(msg) == 0) {
                fragment.eliminarCliente(); // Eliminar la tarea
            } else {
                finish();
            }

        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        UpdateFragment fragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");
        if (fragment != null) {
            // Nada por el momento
        }

    }
}
