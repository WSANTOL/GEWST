package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.constantes.Utilidades;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

public class ActividadContacto extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;

    EditText editTextNombre;
    EditText editTextTelefono;
    EditText editTextEmail;
    EditText editTextMensaje;

    final int PETICION_SACAR_FOTO=1;
    final int PETICION_GALERIA=2;
    ImageView imageViewImagen;
    Bitmap foto=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.actividad_contacto);
        Intent intent=this.getIntent();

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(toolbar);

        //para que vaya atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rellene el formulario y contacte con nosotros/Fill out the form and contact us", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageViewImagen=(ImageView) findViewById( R.id.image_view_imagen);

        ImageButton imageButtonCamara=(ImageButton) findViewById( R.id.image_button_camara);
        imageButtonCamara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        ImageButton imageButtonGaleria=(ImageButton) findViewById( R.id.image_button_galeria);
        imageButtonGaleria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                elegirDeGaleria();
            }
        });

        final Button btnEnviar= (Button) findViewById( R.id.btnEnviar);
        btnEnviar.setEnabled(false);
        TextView txtInfo2=(TextView) findViewById( R.id.txtInfo2);
        txtInfo2.setText(Html.fromHtml(getString( R.string.info2)));
        Button btnValidar= (Button) findViewById( R.id.btnValid);
        editTextNombre=(EditText) findViewById( R.id.editTxtNombreCompleto);
        editTextTelefono=(EditText) findViewById( R.id.editTxtTlfContacto);
        editTextEmail=(EditText) findViewById( R.id.email_contacto);
        editTextMensaje=(EditText) findViewById( R.id.editTxtMensaje);

        btnValidar.setOnClickListener(new View.OnClickListener(){
            boolean d=false;

            @Override
            public void onClick(View v) {

                do{
                    editTextNombre.setError(null);
                    editTextTelefono.setError(null);
                    editTextEmail.setError(null);
                    editTextMensaje.setError(null);



                    String nombre=editTextNombre.getText().toString();
                    String telefono=editTextTelefono.getText().toString();
                    String email=editTextEmail.getText().toString();
                    String mensaje=editTextMensaje.getText().toString();

                    if(TextUtils.isEmpty(nombre)){
                        editTextNombre.setError(getString( R.string.error_campo_obligatorio));
                        editTextNombre.requestFocus();
                        return;
                    }

                    Pattern patron = Pattern.compile("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$");
                    if (!patron.matcher(nombre).matches() || nombre.length() > 40) {
                        editTextNombre.setError(getString( R.string.error_nombre));
                        editTextNombre.requestFocus();
                        return;
                    }



                    if(TextUtils.isEmpty(telefono)){
                        editTextTelefono.setError(getString( R.string.error_campo_obligatorio));
                        editTextTelefono.requestFocus();
                        return;
                    }

                    Pattern pattern = Patterns.PHONE;
                    if(!pattern.matcher(editTextTelefono.getText().toString()).matches()){
                        editTextTelefono.setError(getString( R.string.error_longitudtlf));
                        return;
                    }


                    if(TextUtils.isEmpty(email)){
                        editTextEmail.setError(getString( R.string.error_campo_obligatorio));
                        editTextEmail.requestFocus();
                        return;
                    }

                    Pattern pattern1 = Patterns.EMAIL_ADDRESS;
                    if(!pattern1.matcher(editTextEmail.getText().toString()).matches()){
                        editTextEmail.setError(getString( R.string.error_invalid_email));
                        return;
                    }

                    if(TextUtils.isEmpty(mensaje)){
                        editTextMensaje.setError(getString( R.string.error_campo_obligatorio));
                        editTextMensaje.requestFocus();
                        return;
                    }
                    Toast.makeText(getApplicationContext(), R.string.mensaje1,Toast.LENGTH_SHORT).show();
                    d=true;

                }while(d!=true);

                btnEnviar.setEnabled(true);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.mensaje4,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),ActividadNavigator.class);
                startActivity(intent1);
            }
        });

        final CheckBox checkBoxPolitica=(CheckBox) findViewById( R.id.checkboxPolitica);
        checkBoxPolitica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.selec_politica,Toast.LENGTH_SHORT).show();
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
                    imageViewImagen.setImageBitmap(foto);
                    boolean exitome=false;
                    try {
                        Utilidades.storeImage(foto,this,"imagen.jpg");
                        Toast.makeText(getApplicationContext(),"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();


                        Toast.makeText(getApplicationContext(),"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                        boolean sdDisponible=false;
                        boolean sdAccesoEscritura=false;
                        //Comprobamos el estado de la memoria externa
                        String estado= Environment.getExternalStorageState();
                        if(estado.equals( Environment.MEDIA_MOUNTED)){
                            sdDisponible=true;
                            sdAccesoEscritura=true;
                            exitome=Utilidades.guardarMemoriaExterna(foto,this,"img_contacto.jpg",getContentResolver());
                            if(exitome){
                                Toast.makeText(getApplicationContext(),"Imagen guardada con exito en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                            }
                        }else if(estado.equals( Environment.MEDIA_MOUNTED_READ_ONLY)){
                            sdDisponible=true;
                            sdAccesoEscritura=false;
                            Toast.makeText(getApplicationContext(),"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();

                        }else{
                            sdDisponible=false;
                            sdAccesoEscritura=false;
                            Toast.makeText(getApplicationContext(),"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();

                        }


                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"ERROR: No se pudo guardar la imagen en MEMORIA INTERNA ",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la captura de la imagen ",Toast.LENGTH_LONG).show();

                }
                break;
            case PETICION_GALERIA:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    imageViewImagen.setImageURI(uri);
                    foto=((BitmapDrawable) imageViewImagen.getDrawable()).getBitmap();
                }else{
                    Toast.makeText(getApplicationContext(),"Se ha cancelado la subida de la imagen ",Toast.LENGTH_LONG).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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

        Intent intent=new Intent(this,ActividadContacto.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }


}
