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

import java.util.regex.Pattern;

public class CitaConsulta4 extends AppCompatActivity {
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
        setContentView( R.layout.activity_consulta_4);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById( R.id.toolbar_detalle_activity_3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lista=(ListView) findViewById( R.id.lista_dia4 );
        Cursor c= CitaProveedor.todo( getContentResolver() );
        mAdapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.cita_list_item_4,c,mListColumns,mListItems,0 );


        lista.setAdapter( mAdapter );

        ImageButton btn=(ImageButton) findViewById( R.id.btnconsulta4 );
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                attemptGuardar();
            }
        });

    }

    void attemptGuardar(){
        /*
        EditText editTextCitaCodEmpleado=(EditText) findViewById(R.id.editTextCodEmpleado);


        editTextCitaCodEmpleado.setError(null);



        String cod_empleado=editTextCitaCodEmpleado.getText().toString();

        if(TextUtils.isEmpty(cod_empleado)){
            editTextCitaCodEmpleado.setError(getString(R.string.error_campo_obligatorio));
            editTextCitaCodEmpleado.requestFocus();
            return;
        }

        int empleado_int=Integer.parseInt(cod_empleado);
        if (empleado_int<1 || empleado_int > 6) {
            editTextCitaCodEmpleado.setError(getString(R.string.error_codigo));
            editTextCitaCodEmpleado.requestFocus();
            return;
        }
        */

        EditText editTextEmpleadoNombre=(EditText) findViewById( R.id.editTextEmpleadoNombre);



        editTextEmpleadoNombre.setError(null);


        String nombre=editTextEmpleadoNombre.getText().toString();


        if(TextUtils.isEmpty(nombre)){
            editTextEmpleadoNombre.setError(getString( R.string.error_campo_obligatorio));
            editTextEmpleadoNombre.requestFocus();
            return;
        }

        Pattern patron = Pattern.compile("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 20) {
            editTextEmpleadoNombre.setError(getString( R.string.error_nombre_2));
            editTextEmpleadoNombre.requestFocus();
            return;
        }

        //Cita cita=new Cita(G.SIN_VALOR_INT,Integer.parseInt(dia),Integer.parseInt(mes),Integer.parseInt(anho),Integer.parseInt(hora),Integer.parseInt(minuto),servicio,Integer.parseInt(cod_cliente),Integer.parseInt(cod_empleado),foto);
        //Cursor c= CitaProveedor.consulta2( getContentResolver(),Integer.parseInt(cod_empleado));
        Cursor c= CitaProveedor.consultaMultiple(nombre);
        mAdapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.cita_list_item_4,c,mListColumns,mListItems,0 );
        lista.setAdapter( mAdapter );


        //finish();


    }
}
