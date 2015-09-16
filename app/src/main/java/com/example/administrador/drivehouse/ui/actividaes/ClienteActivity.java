package com.example.administrador.drivehouse.ui.actividaes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrador.drivehouse.MainActivity;
import com.example.administrador.drivehouse.R;
import com.example.administrador.drivehouse.ui.fragmentos.ClientesFragment;

public class ClienteActivity extends AppCompatActivity {

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
                    .add(R.id.contenedor_cliente, new ClientesFragment(), "ClientesFragment").commit();

        }
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_action_white_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
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
                Toast.makeText(getApplicationContext(), "buscar", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}
