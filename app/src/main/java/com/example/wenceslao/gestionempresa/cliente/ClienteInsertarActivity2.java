package com.example.wenceslao.gestionempresa.cliente;

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
import com.example.wenceslao.gestionempresa.pojos.Cliente;
import com.example.wenceslao.gestionempresa.proveedor.ClienteProveedor;

import java.util.regex.Pattern;

public class ClienteInsertarActivity2 extends AppCompatActivity {
    final int PETICION_SACAR_FOTO=1;
    final int PETICION_GALERIA=2;
    ImageView imageViewCliente;
    Bitmap foto=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_cliente_detalle);

        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar) findViewById( R.id.toolbar_detalle_activity);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewCliente=(ImageView) findViewById( R.id.image_view_cliente);

        ImageButton imageCamaraCliente=(ImageButton) findViewById( R.id.image_button_camara_cliente);
        imageCamaraCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        ImageButton imageGaleriaCliente=(ImageButton) findViewById( R.id.image_button_galeria_cliente);
        imageGaleriaCliente.setOnClickListener(new View.OnClickListener(){
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
                    imageViewCliente.setImageBitmap(foto);
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la captura de la imagen ",Toast.LENGTH_LONG).show();

                }
                break;
            case PETICION_GALERIA:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    imageViewCliente.setImageURI(uri);
                    foto=((BitmapDrawable) imageViewCliente.getDrawable()).getBitmap();
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
        EditText editTextClienteNombre=(EditText) findViewById( R.id.editTextClienteNombre );
        EditText editTextClienteApellidos=(EditText) findViewById( R.id.editTextClienteApellidos);
        EditText editTextClienteEmail=(EditText) findViewById( R.id.editTextClienteEmail);
        EditText editTextClienteTelefono=(EditText) findViewById( R.id.editTextClienteTelefono);


        editTextClienteNombre.setError(null);
        editTextClienteApellidos.setError(null);
        editTextClienteEmail.setError(null);
        editTextClienteTelefono.setError(null);

        String nombre=editTextClienteNombre.getText().toString();
        String apellidos=editTextClienteApellidos.getText().toString();
        String email=editTextClienteEmail.getText().toString();
        String telefono=editTextClienteTelefono.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            editTextClienteNombre.setError(getString( R.string.error_campo_obligatorio));
            editTextClienteNombre.requestFocus();
            return;
        }

        Pattern patron = Pattern.compile("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 20) {
            editTextClienteNombre.setError(getString( R.string.error_nombre_2));
            editTextClienteNombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(apellidos)){
            editTextClienteApellidos.setError(getString( R.string.error_campo_obligatorio));
            editTextClienteApellidos.requestFocus();
            return;
        }

        if (!patron.matcher(apellidos).matches() || apellidos.length() > 20) {
            editTextClienteApellidos.setError(getString( R.string.error_nombre_2));
            editTextClienteApellidos.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            editTextClienteEmail.setError(getString( R.string.error_campo_obligatorio));
            editTextClienteEmail.requestFocus();
            return;
        }

        Pattern pattern1 = Patterns.EMAIL_ADDRESS;
        if(!pattern1.matcher(editTextClienteEmail.getText().toString()).matches()){
            editTextClienteEmail.setError(getString( R.string.error_invalid_email));
            return;
        }

        if(TextUtils.isEmpty(telefono)){
            editTextClienteTelefono.setError(getString( R.string.error_campo_obligatorio));
            editTextClienteTelefono.requestFocus();
            return;
        }


        Pattern pattern = Patterns.PHONE;
        if(!pattern.matcher(editTextClienteTelefono.getText().toString()).matches()){
            editTextClienteTelefono.setError(getString( R.string.error_longitudtlf));
            return;
        }



        Cliente cliente=new Cliente(G.SIN_VALOR_INT,nombre,apellidos,email,telefono,foto);
        ClienteProveedor.insert(getContentResolver(),cliente,this);
        finish();


    }
}
