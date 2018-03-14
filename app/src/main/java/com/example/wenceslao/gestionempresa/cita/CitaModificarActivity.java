package com.example.wenceslao.gestionempresa.cita;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.R;
import com.example.wenceslao.gestionempresa.constantes.G;
import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.pojos.Cita;
import com.example.wenceslao.gestionempresa.proveedor.CitaProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCita;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class CitaModificarActivity extends AppCompatActivity {

    EditText editTextCitaDia;
    EditText editTextCitaMes;
    EditText editTextCitaAnho;
    EditText editTextCitaHora;
    EditText editTextCitaMinuto;
    EditText editTextCitaServicio;
    EditText editTextCitaCodCliente;
    EditText editTextCitaCodEmpleado;

    int citaId;

    final int PETICION_SACAR_FOTO=1;
    final int PETICION_GALERIA=2;
    ImageView imageViewCita;
    Bitmap foto=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_cita_detalle);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById( R.id.toolbar_detalle_activity_3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextCitaDia=(EditText) findViewById( R.id.editTextCitaDia);
        editTextCitaMes=(EditText) findViewById( R.id.editTextCitaMes);
        editTextCitaAnho=(EditText) findViewById( R.id.editTextCitaAnho);
        editTextCitaHora=(EditText) findViewById( R.id.editTextCitaHora);
        editTextCitaMinuto=(EditText) findViewById( R.id.editTextCitaMinuto);
        editTextCitaServicio=(EditText) findViewById( R.id.editTextCitaServicio);
        editTextCitaCodCliente=(EditText) findViewById( R.id.editTextCitaCliente);
        editTextCitaCodEmpleado=(EditText) findViewById( R.id.editTextCitaEmpleado);


        citaId=this.getIntent().getExtras().getInt(ContratoCita.Cita._ID);
        Cita cita=CitaProveedor.readRecord(getContentResolver(),citaId);

        editTextCitaDia.setText(String.valueOf(cita.getDia()));
        editTextCitaMes.setText(String.valueOf(cita.getMes()));
        editTextCitaAnho.setText(String.valueOf(cita.getAnho()));
        editTextCitaHora.setText(String.valueOf(cita.getHora()));
        editTextCitaMinuto.setText(String.valueOf(cita.getMinutos()));
        editTextCitaServicio.setText(cita.getServicio());
        editTextCitaCodCliente.setText(String.valueOf(cita.getCod_cliente()));
        editTextCitaCodEmpleado.setText(String.valueOf(cita.getCod_empleado()));

        imageViewCita=(ImageView) findViewById( R.id.image_view_cita);

        //leer la imagen
        try {
            Utilidades.loadImageFromStorage(this,"img_cita"+citaId+".jpg",imageViewCita);
            foto=((BitmapDrawable) imageViewCita.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"ERROR: No existe la imagen  ",Toast.LENGTH_LONG).show();
        }

        ImageButton imageButtonCamara=(ImageButton) findViewById( R.id.image_button_camara_cita);
        imageButtonCamara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        ImageButton imageButtonGaleria=(ImageButton) findViewById( R.id.image_button_galeria_cita);
        imageButtonGaleria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                elegirDeGaleria();
            }
        });
    }

    void elegirDeGaleria(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }

    void sacarFoto(){
        Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
        //se pueden acceder a varias actividades
    }

    //cuando le de a que no quiero la foto sacada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PETICION_SACAR_FOTO:
                if(resultCode==RESULT_OK){
                    foto=(Bitmap) data.getExtras().get("data");
                    imageViewCita.setImageBitmap(foto);
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la captura de la imagen ",Toast.LENGTH_LONG).show();

                }
                break;
            case PETICION_GALERIA:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    imageViewCita.setImageURI(uri);
                    foto=((BitmapDrawable) imageViewCita.getDrawable()).getBitmap();
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la subida de la imagen ",Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.add(Menu.NONE, G.GUARDAR,Menu.NONE,"Guardar");
        menuItem.setIcon( R.drawable.ic_action_guardar);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.GUARDAR:
                attemptGuardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar(){

        editTextCitaDia.setError(null);
        editTextCitaMes.setError(null);
        editTextCitaAnho.setError(null);
        editTextCitaHora.setError(null);
        editTextCitaMinuto.setError(null);
        editTextCitaServicio.setError(null);
        editTextCitaCodCliente.setError(null);
        editTextCitaCodEmpleado.setError(null);


        String dia=editTextCitaDia.getText().toString();
        String mes=editTextCitaMes.getText().toString();
        String anho=editTextCitaAnho.getText().toString();
        String hora=editTextCitaHora.getText().toString();
        String minuto=editTextCitaMinuto.getText().toString();
        String servicio=editTextCitaServicio.getText().toString();
        String cod_cliente=editTextCitaCodCliente.getText().toString();
        String cod_empleado=editTextCitaCodEmpleado.getText().toString();

        if(TextUtils.isEmpty(dia)){
            editTextCitaDia.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaDia.requestFocus();
            return;
        }

        int dia_int=Integer.parseInt(dia);
        if (dia_int<1 || dia_int > 30) {
            editTextCitaDia.setError(getString( R.string.error_fecha));
            editTextCitaDia.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(mes)){
            editTextCitaMes.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaMes.requestFocus();
            return;
        }

        int mes_int=Integer.parseInt(mes);
        if (mes_int<1 || mes_int > 12) {
            editTextCitaMes.setError(getString( R.string.error_fecha));
            editTextCitaMes.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(anho)){
            editTextCitaAnho.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaAnho.requestFocus();
            return;
        }

        int anho_int=Integer.parseInt(anho);
        if (anho_int<2017 || anho_int > 2018) {
            editTextCitaAnho.setError(getString( R.string.error_fecha));
            editTextCitaAnho.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(hora)){
            editTextCitaHora.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaHora.requestFocus();
            return;
        }

        int hora_int=Integer.parseInt(hora);
        if (hora_int<9 || hora_int > 20) {
            editTextCitaHora.setError(getString( R.string.error_fecha));
            editTextCitaHora.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(minuto)){
            editTextCitaMinuto.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaMinuto.requestFocus();
            return;
        }

        int minuto_int=Integer.parseInt(minuto);
        int[] disponibles={0, 15, 30, 45};
        if (Arrays.asList(disponibles).contains(minuto_int)) {
            editTextCitaMinuto.setError(getString( R.string.error_fecha));
            editTextCitaMinuto.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(servicio)){
            editTextCitaServicio.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaServicio.requestFocus();
            return;
        }

        String[] serv_dispo={"Corte", "Peinado", "Tinte", "Estetica"};
        if (!Arrays.asList(serv_dispo).contains(servicio)) {
            editTextCitaServicio.setError(getString( R.string.error_fecha));
            editTextCitaServicio.requestFocus();
            return;
        }

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

        if(TextUtils.isEmpty(cod_empleado)){
            editTextCitaCodEmpleado.setError(getString( R.string.error_campo_obligatorio));
            editTextCitaCodEmpleado.requestFocus();
            return;
        }

        int empleado_int=Integer.parseInt(cod_empleado);
        if (empleado_int<1 || empleado_int > 6) {
            editTextCitaCodEmpleado.setError(getString( R.string.error_codigo));
            editTextCitaCodEmpleado.requestFocus();
            return;
        }

        Cita cita=new Cita(citaId,Integer.parseInt(dia),Integer.parseInt(mes),Integer.parseInt(anho),Integer.parseInt(hora),Integer.parseInt(minuto),servicio,Integer.parseInt(cod_cliente),Integer.parseInt(cod_empleado),foto);
        CitaProveedor.update(getContentResolver(),cita,this);
        finish();


    }
}
