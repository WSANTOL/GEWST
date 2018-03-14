package com.example.wenceslao.gestionempresa.cita;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wenceslao.gestionempresa.R;


public class ActividadCita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.actividad_cita);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        CitaListFragment citaListFragment = new CitaListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add( R.id.fragment_cita, citaListFragment);
        transaction.commit();

        //Funcionalidad del Floating Button
        FloatingActionButton fab=(FloatingActionButton) findViewById( R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CitaInsertarActivity.class);
                startActivity(intent);
            }
        });
    }
}
