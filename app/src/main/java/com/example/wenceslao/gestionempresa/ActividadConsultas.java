package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.cita.CitaConsulta1;
import com.example.wenceslao.gestionempresa.cita.CitaConsulta2;
import com.example.wenceslao.gestionempresa.cita.CitaConsulta3;

import java.util.Locale;

public class ActividadConsultas extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.actividad_consultas);
        Intent intent=this.getIntent();

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(toolbar);

        //para que vaya atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Elija la opcion que mas se adecua a sus necesidades", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnConsulta3=(Button) findViewById( R.id.button_consulta_citas_usuario );


        btnConsulta3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getApplicationContext(),CitaConsulta3.class);
                startActivity(intent3);

            }
        });

        Button btnConsulta2=(Button) findViewById( R.id.button_consulta_citas_empleado );


        btnConsulta2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(),CitaConsulta2.class);
                startActivity(intent2);

            }
        });

        Button btnConsulta1=(Button) findViewById( R.id.button_consulta_cita_fecha );


        btnConsulta1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),CitaConsulta1.class);
                startActivity(intent1);

            }
        });




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_actividad_principal, menu);
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
                Toast.makeText(getApplicationContext(), R.string.cierre_app,Toast.LENGTH_SHORT).show();
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

        //Recargar solo los datos de interes
        //actualizarViews();

        Intent intent=new Intent(this,ActividadConsultas.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }


}
