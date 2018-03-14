package com.example.wenceslao.gestionempresa.cita;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wenceslao.gestionempresa.R;
import com.example.wenceslao.gestionempresa.proveedor.CitaProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCita;

public class CitaConsulta3 extends AppCompatActivity {
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

    int[] mListItems={R.id.textview_cita_list_item_dia, R.id.textview_cita_list_item_mes, R.id.textview_cita_list_item_anho, R.id.textview_cita_list_item_hora, R.id.textview_cita_list_item_minuto, R.id.textview_cita_list_item_servicio, R.id.textview_cita_list_item_codcliente, R.id.textview_cita_list_item_codempleado};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_consulta_3);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById( R.id.toolbar_detalle_activity_3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lista=(ListView) findViewById( R.id.lista_dia3 );
        Cursor c= CitaProveedor.todo( getContentResolver() );
        mAdapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.cita_list_item_3,c,mListColumns,mListItems,0 );


        lista.setAdapter( mAdapter );

        ImageButton btn=(ImageButton) findViewById( R.id.btnconsulta3 );
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                attemptGuardar();
            }
        });

    }

    void attemptGuardar(){
        EditText editTextCitaCodCliente=(EditText) findViewById( R.id.editTextCodCliente);


        editTextCitaCodCliente.setError(null);



        String cod_cliente=editTextCitaCodCliente.getText().toString();

        if(TextUtils.isEmpty(cod_cliente)){
            editTextCitaCodCliente.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaCodCliente.requestFocus();
            return;
        }

        int cliente_int=Integer.parseInt(cod_cliente);
        if (cliente_int<1 || cliente_int > 4) {
            editTextCitaCodCliente.setError(getString( R.string.error_codigo));
            editTextCitaCodCliente.requestFocus();
            return;
        }

        //Cita cita=new Cita(G.SIN_VALOR_INT,Integer.parseInt(dia),Integer.parseInt(mes),Integer.parseInt(anho),Integer.parseInt(hora),Integer.parseInt(minuto),servicio,Integer.parseInt(cod_cliente),Integer.parseInt(cod_empleado),foto);
        Cursor c= CitaProveedor.consulta3( getContentResolver(),Integer.parseInt(cod_cliente));
        mAdapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.cita_list_item_3,c,mListColumns,mListItems,0 );
        lista.setAdapter( mAdapter );


        //finish();


    }
}
