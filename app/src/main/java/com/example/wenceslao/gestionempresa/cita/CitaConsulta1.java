package com.example.wenceslao.gestionempresa.cita;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.wenceslao.gestionempresa.R;
import com.example.wenceslao.gestionempresa.constantes.G;
import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.pojos.Cita;
import com.example.wenceslao.gestionempresa.proveedor.CitaProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCita;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class CitaConsulta1 extends AppCompatActivity {
    //CitaListFragment.CitaCursorAdapter mAdapter = new CitaListFragment.CitaCursorAdapter();
    Cursor cursor;
    ListView lista;
    SimpleCursorAdapter mAdapter;
    String[] mListColumns={
            //ContratoCita.Cita._ID,
            ContratoCita.Cita.DIA,
            ContratoCita.Cita.MES,
            ContratoCita.Cita.ANHO,
            ContratoCita.Cita.HORA,
            ContratoCita.Cita.MINUTO,
            ContratoCita.Cita.SERVICIO,
            ContratoCita.Cita.COD_CLIENTE,
            ContratoCita.Cita.COD_EMPLEADO
    };

    int[] mListItems={R.id.textview_cita_list_item_dia,R.id.textview_cita_list_item_mes,R.id.textview_cita_list_item_anho,R.id.textview_cita_list_item_hora,R.id.textview_cita_list_item_minuto,R.id.textview_cita_list_item_servicio,R.id.textview_cita_list_item_codcliente,R.id.textview_cita_list_item_codempleado};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_1);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_detalle_activity_3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lista=(ListView) findViewById( R.id.lista_dia );
        Cursor c= CitaProveedor.todo( getContentResolver() );
        mAdapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.cita_list_item_1,c,mListColumns,mListItems,0 );


        lista.setAdapter( mAdapter );

        ImageButton btn=(ImageButton) findViewById( R.id.btnconsulta );
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                attemptGuardar();
            }
        });

    }

    void attemptGuardar(){
        EditText editTextCitaDia=(EditText) findViewById(R.id.editTextCitaDia);
        EditText editTextCitaMes=(EditText) findViewById(R.id.editTextCitaMes);
        EditText editTextCitaAnho=(EditText) findViewById(R.id.editTextCitaAnho);


        editTextCitaDia.setError(null);
        editTextCitaMes.setError(null);
        editTextCitaAnho.setError(null);



        String dia=editTextCitaDia.getText().toString();
        String mes=editTextCitaMes.getText().toString();
        String anho=editTextCitaAnho.getText().toString();


        if(TextUtils.isEmpty(dia)){
            editTextCitaDia.setError(getString(R.string.error_campo_obligatorio));
            editTextCitaDia.requestFocus();
            return;
        }

        int dia_int=Integer.parseInt(dia);
        if (dia_int<1 || dia_int > 30) {
            editTextCitaDia.setError(getString(R.string.error_fecha));
            editTextCitaDia.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(mes)){
            editTextCitaMes.setError(getString(R.string.error_campo_obligatorio));
            editTextCitaMes.requestFocus();
            return;
        }

        int mes_int=Integer.parseInt(mes);
        if (mes_int<1 || mes_int > 12) {
            editTextCitaMes.setError(getString(R.string.error_fecha));
            editTextCitaMes.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(anho)){
            editTextCitaAnho.setError(getString(R.string.error_campo_obligatorio));
            editTextCitaAnho.requestFocus();
            return;
        }

        int anho_int=Integer.parseInt(anho);
        if (anho_int<2017 || anho_int > 2018) {
            editTextCitaAnho.setError(getString(R.string.error_fecha));
            editTextCitaAnho.requestFocus();
            return;
        }







        //Cita cita=new Cita(G.SIN_VALOR_INT,Integer.parseInt(dia),Integer.parseInt(mes),Integer.parseInt(anho),Integer.parseInt(hora),Integer.parseInt(minuto),servicio,Integer.parseInt(cod_cliente),Integer.parseInt(cod_empleado),foto);
        Cursor c= CitaProveedor.consulta1( getContentResolver(),Integer.parseInt( dia ) ,Integer.parseInt(mes),Integer.parseInt(anho));
        mAdapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.cita_list_item_1,c,mListColumns,mListItems,0 );
        lista.setAdapter( mAdapter );


        //finish();


    }
}
