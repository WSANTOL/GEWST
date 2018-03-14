package com.example.wenceslao.gestionempresa.empleado;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.example.wenceslao.gestionempresa.pojos.Empleado;
import com.example.wenceslao.gestionempresa.proveedor.ContratoEmpleado;
import com.example.wenceslao.gestionempresa.proveedor.EmpleadoProveedor;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class EmpleadoModificarActivity extends AppCompatActivity {

    EditText editTextEmpleadoNombre;
    EditText editTextEmpleadoFormacion;
    EditText editTextEmpleadoEmail;
    EditText editTextEmpleadoTelefono;

    int empleadoId;

    final int PETICION_SACAR_FOTO=1;
    final int PETICION_GALERIA=2;
    ImageView imageViewEmpleado;
    Bitmap foto=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_empleado_detalle);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById( R.id.toolbar_detalle_activity_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmpleadoNombre=(EditText) findViewById( R.id.editTextEmpleadoNombre);
        editTextEmpleadoFormacion=(EditText) findViewById( R.id.editTextEmpleadoFormacion);
        editTextEmpleadoEmail=(EditText) findViewById( R.id.editTextEmpleadoEmail);
        editTextEmpleadoTelefono=(EditText) findViewById( R.id.editTextEmpleadoTelefono);

        empleadoId=this.getIntent().getExtras().getInt(ContratoEmpleado.Empleado._ID);
        Empleado empleado=EmpleadoProveedor.readRecord(getContentResolver(),empleadoId);

        editTextEmpleadoNombre.setText(empleado.getNombre_completo());
        editTextEmpleadoFormacion.setText(empleado.getFormacion());
        editTextEmpleadoEmail.setText(empleado.getEmail());
        editTextEmpleadoTelefono.setText(empleado.getTelefono());

        imageViewEmpleado=(ImageView) findViewById( R.id.image_view_empleado);

        //leer la imagen
        try {
            Utilidades.loadImageFromStorage(this,"img_empleado"+empleadoId+".jpg",imageViewEmpleado);
            foto=((BitmapDrawable) imageViewEmpleado.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"ERROR: No existe la imagen  ",Toast.LENGTH_LONG).show();
        }

        ImageButton imageButtonCamara=(ImageButton) findViewById( R.id.image_button_camara_empleado);
        imageButtonCamara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        ImageButton imageButtonGaleria=(ImageButton) findViewById( R.id.image_button_galeria_empleado);
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
                    imageViewEmpleado.setImageBitmap(foto);
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la captura de la imagen ",Toast.LENGTH_LONG).show();

                }
                break;
            case PETICION_GALERIA:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    imageViewEmpleado.setImageURI(uri);
                    foto=((BitmapDrawable) imageViewEmpleado.getDrawable()).getBitmap();
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

        editTextEmpleadoNombre.setError(null);
        editTextEmpleadoFormacion.setError(null);
        editTextEmpleadoEmail.setError(null);
        editTextEmpleadoTelefono.setError(null);

        String nombre=editTextEmpleadoNombre.getText().toString();
        String formaacion=editTextEmpleadoFormacion.getText().toString();
        String email=editTextEmpleadoEmail.getText().toString();
        String telefono=editTextEmpleadoTelefono.getText().toString();

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

        if(TextUtils.isEmpty(formaacion)){
            editTextEmpleadoFormacion.setError(getString( R.string.error_campo_obligatorio));
            editTextEmpleadoFormacion.requestFocus();
            return;
        }

        if (!patron.matcher(formaacion).matches() || formaacion.length() > 20) {
            editTextEmpleadoFormacion.setError(getString( R.string.error_nombre_2));
            editTextEmpleadoFormacion.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            editTextEmpleadoEmail.setError(getString( R.string.error_campo_obligatorio));
            editTextEmpleadoEmail.requestFocus();
            return;
        }

        Pattern pattern1 = Patterns.EMAIL_ADDRESS;
        if(!pattern1.matcher(editTextEmpleadoEmail.getText().toString()).matches()){
            editTextEmpleadoEmail.setError(getString( R.string.error_invalid_email));
            return;
        }

        if(TextUtils.isEmpty(telefono)){
            editTextEmpleadoTelefono.setError(getString( R.string.error_campo_obligatorio));
            editTextEmpleadoTelefono.requestFocus();
            return;
        }


        Pattern pattern = Patterns.PHONE;
        if(!pattern.matcher(editTextEmpleadoTelefono.getText().toString()).matches()){
            editTextEmpleadoTelefono.setError(getString( R.string.error_longitudtlf));
            return;
        }

        Empleado empleado=new Empleado(empleadoId,nombre,formaacion,email,telefono,foto);
        EmpleadoProveedor.update(getContentResolver(),empleado,this);
        finish();


    }
}
