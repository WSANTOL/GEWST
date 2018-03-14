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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Pattern;

public class ActividadRegistro extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;

    String tamaño="Pequeña(1-25)";

    EditText editTextNombreEmpresa;
    EditText editTextTelefono;
    EditText editTextNombreAdm;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro);
        Intent intent=this.getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //para que vaya atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rellene el formulario para crear su cuenta/Fill out the form to create your account", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button btnRegistrar= (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setEnabled(false);
        Button btnValidar= (Button) findViewById(R.id.btnValidar);
        editTextNombreEmpresa=(EditText) findViewById(R.id.editTextNombreEmpresa);
        editTextTelefono=(EditText) findViewById(R.id.editTextTelefono);
        editTextNombreAdm=(EditText) findViewById(R.id.editTextNombreAdm);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);

        btnValidar.setOnClickListener(new View.OnClickListener(){
            boolean d=false;

            @Override
            public void onClick(View v) {

                do{
                    editTextNombreEmpresa.setError(null);
                    editTextTelefono.setError(null);
                    editTextNombreAdm.setError(null);
                    editTextEmail.setError(null);
                    editTextPassword.setError(null);


                    String nombreEmp=editTextNombreEmpresa.getText().toString();
                    String telefono=editTextTelefono.getText().toString();
                    String administrador=editTextNombreAdm.getText().toString();
                    String email=editTextEmail.getText().toString();
                    String password=editTextPassword.getText().toString();

                    if(TextUtils.isEmpty(nombreEmp)){
                        editTextNombreEmpresa.setError(getString(R.string.error_campo_obligatorio));
                        editTextNombreEmpresa.requestFocus();
                        return;
                    }

                    Pattern patron = Pattern.compile("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+$");
                    if (!patron.matcher(nombreEmp).matches() || nombreEmp.length() > 20) {
                        editTextNombreEmpresa.setError(getString(R.string.error_nombre_2));
                        editTextNombreEmpresa.requestFocus();
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

                    if(TextUtils.isEmpty(administrador)){
                        editTextNombreAdm.setError(getString(R.string.error_campo_obligatorio));
                        editTextNombreAdm.requestFocus();
                        return;
                    }

                    if (!patron.matcher(administrador).matches() || administrador.length() > 20) {
                        editTextNombreAdm.setError(getString(R.string.error_nombre_2));
                        editTextNombreAdm.requestFocus();
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

                    if(TextUtils.isEmpty(password)){
                        editTextPassword.setError(getString(R.string.error_campo_obligatorio));
                        editTextPassword.requestFocus();
                        return;
                    }

                    if(password.length()>20 ||password.length()<6){
                        editTextPassword.setError(getString(R.string.error_password));
                        editTextPassword.requestFocus();
                        return;
                    }
                    Toast.makeText(getApplicationContext(),R.string.mensaje1,Toast.LENGTH_SHORT).show();
                    d=true;

                }while(d!=true);

                btnRegistrar.setEnabled(true);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nombreEmp=editTextNombreEmpresa.getText().toString();
                //de momento
                Toast.makeText(getApplicationContext(),R.string.mensaje6,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),ActividadNavigator.class);
                startActivity(intent1);
            }
        });

        RadioGroup radioGroupTamaño=(RadioGroup) findViewById(R.id.tamaño);
        radioGroupTamaño.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int checkedId) {
                switch (checkedId){
                    case R.id.radioPequeña:
                        Toast.makeText(getApplicationContext(),R.string.tam_pequena,Toast.LENGTH_SHORT).show();
                        tamaño="Pequeña(1-25)";
                        break;
                    case R.id.radioMediana:
                        Toast.makeText(getApplicationContext(),R.string.tam_mediana,Toast.LENGTH_SHORT).show();
                        tamaño="Mediana(26-50)";
                        break;
                }

            }
        });

        final CheckBox checkBoxSector1=(CheckBox) findViewById(R.id.checkboxSector1);
        checkBoxSector1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.selec_sector1,Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox checkBoxSector2=(CheckBox) findViewById(R.id.checkboxSector2);
        checkBoxSector2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.selec_sector2,Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox checkBoxSector3=(CheckBox) findViewById(R.id.checkboxSector3);
        checkBoxSector3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.selec_sector3,Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(this,ActividadAyuda2.class);
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

        Intent intent=new Intent(this,ActividadRegistro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }

    /*void actualizarViews(){
        EditText editTextNombreEmp=(EditText) findViewById(R.id.editTextNombreEmpresa);
        editTextNombreEmp.setText(getString(R.string.nombreEmpresa));

        EditText editTextTelefono=(EditText) findViewById(R.id.editTextTelefono);
        editTextTelefono.setText(getString(R.string.telefono));

        EditText editTextNombre=(EditText) findViewById(R.id.editTextNombreAdm);
        editTextNombre.setText(getString(R.string.nombreAdministrador));

        EditText editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextEmail.setText(getString(R.string.email));

        EditText editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        editTextPassword.setText(getString(R.string.password));

        RadioButton pequeña=(RadioButton) findViewById(R.id.radioPequeña);
        pequeña.setText(getString(R.string.pequeña));

        RadioButton mediana=(RadioButton) findViewById(R.id.radioMediana);
        mediana.setText(getString(R.string.mediana));

        CheckBox sector1= (CheckBox) findViewById(R.id.checkboxSector1);
        sector1.setText(getString(R.string.sector1));

        CheckBox sector2=(CheckBox) findViewById(R.id.checkboxSector2);
        sector2.setText(getString(R.string.sector2));

        CheckBox sector3=(CheckBox) findViewById(R.id.checkboxSector3);
        sector3.setText(getString(R.string.sector3));

        Button btnRegistrar=(Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setText(R.string.registrar);

        Button btnValidar=(Button) findViewById(R.id.btnValidar);
        btnValidar.setText(R.string.validar);

    }*/

}
