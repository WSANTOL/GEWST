package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Pattern;

public class ActividadAddCita extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;

    EditText editTextNombre;
    EditText editTextTelefono;
    EditText editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_add_cita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rellene los datos solicitados y obtenga su cita/Fill in the requested information and get your appointment", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Spinner de servicios
        Spinner lista_servicios=(Spinner) findViewById(R.id.lista_servicios);
        String[] servicios={getString(R.string.servicio1),getString(R.string.servicio2),getString(R.string.servicio3),getString(R.string.servicio4)};
        lista_servicios.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,servicios));

        //Spinner de empleados
        Spinner lista_empleados=(Spinner) findViewById(R.id.lista_peluqueros);
        String[] peluqueros={"María","Juana","Pepe","Juan"};
        lista_empleados.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,peluqueros));

        DatePicker fecha;
        fecha=(DatePicker) findViewById(R.id.fecha);

        TimePicker timePicker1;
        timePicker1 = (TimePicker) findViewById(R.id.hora);

        final Button btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setEnabled(false);

        Button btnValidar= (Button) findViewById(R.id.btnValidacion);
        editTextNombre=(EditText) findViewById(R.id.nombre_cliente);
        editTextTelefono=(EditText) findViewById(R.id.telefono_cliente);
        editTextEmail=(EditText) findViewById(R.id.email_cliente);

        btnValidar.setOnClickListener(new View.OnClickListener(){
            boolean d=false;

            @Override
            public void onClick(View v) {

                do{
                    editTextNombre.setError(null);
                    editTextTelefono.setError(null);
                    editTextEmail.setError(null);



                    String nombre=editTextNombre.getText().toString();
                    String telefono=editTextTelefono.getText().toString();
                    String email=editTextEmail.getText().toString();

                    if(TextUtils.isEmpty(nombre)){
                        editTextNombre.setError(getString(R.string.error_campo_obligatorio));
                        editTextNombre.requestFocus();
                        return;
                    }

                    Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
                    if (!patron.matcher(nombre).matches() || nombre.length() > 40) {
                        editTextNombre.setError(getString(R.string.error_nombre));
                        editTextNombre.requestFocus();
                        return;
                    }



                    if(TextUtils.isEmpty(telefono)){
                        editTextTelefono.setError(getString(R.string.error_campo_obligatorio));
                        editTextTelefono.requestFocus();
                        return;
                    }

                    Pattern pattern = Patterns.PHONE;
                    if(!pattern.matcher(editTextTelefono.getText().toString()).matches()){
                        editTextTelefono.setError(getString(R.string.error_longitudtlf));
                        return;
                    }


                    if(TextUtils.isEmpty(email)){
                        editTextEmail.setError(getString(R.string.error_campo_obligatorio));
                        editTextEmail.requestFocus();
                        return;
                    }

                    Pattern pattern1 = Patterns.EMAIL_ADDRESS;
                    if(!pattern1.matcher(editTextEmail.getText().toString()).matches()){
                        editTextEmail.setError(getString(R.string.error_invalid_email));
                        return;
                    }


                    Toast.makeText(getApplicationContext(),R.string.mensaje1,Toast.LENGTH_SHORT).show();
                    d=true;

                }while(d!=true);

                btnAdd.setEnabled(true);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.mensaje5,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),ActividadNavigator.class);
                startActivity(intent1);
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
                Intent intent=new Intent(this,ActividadAyuda.class);
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

        //Recargar solo los datos de interes
        //actualizarViews();

        Intent intent=new Intent(this,ActividadAddCita.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }



    public void showDatePickerDialog(View v) {
        DatePickerFragment nuevo=new DatePickerFragment();
        nuevo.show( getSupportFragmentManager(),"datepicker" );

    }

}
