package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.cliente.ActividadCliente;

import java.util.Locale;

public class ActividadPrincipal extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Bienvenido a esta aplicacion /Welcome to this application ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final Button btnInicio=(Button) findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //comprobar que el usuario y contraseña estan almacenados en la BBDD
                Intent intent=new Intent(getApplicationContext(),ActividadLogin.class);
                startActivity(intent);
            }
        });

        final Button btnRegistro=(Button) findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActividadRegistro.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_english:
                cambiarIdioma(ENGLISH);
                break;
            case R.id.action_spain:
                cambiarIdioma(SPANISH);
                break;
            case R.id.action_help:
                Intent intent=new Intent(this,ActividadAyuda2.class);
                startActivity(intent);
                break;
            case R.id.action_quit:
                Toast.makeText(getApplicationContext(),R.string.cierre_app,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void cambiarIdioma(int idioma){
        String language=null;
        switch (idioma){
            case SPANISH:
                language="es";
                break;
            case ENGLISH:
                language="en";
                break;

        }
        Locale locale=new Locale(language);
        Locale.setDefault(locale);

        Resources resources=this.getResources();

        Configuration configuration=resources.getConfiguration();
        configuration.locale=locale;

        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        //Recargar los datos
        Intent intent=new Intent(this,ActividadPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }


}
