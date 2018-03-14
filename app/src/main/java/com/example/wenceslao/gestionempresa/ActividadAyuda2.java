package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Pattern;

public class ActividadAyuda2 extends AppCompatActivity {
    final int SPANISH=1;
    final int ENGLISH=2;

    String idioma="Español";

    TextView txtayuda1;
    TextView txtayuda2;
    TextView txtayuda3;
    TextView txtayuda4;
    RadioButton español;
    RadioButton ingles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_ayuda);
        final Intent intent=this.getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aquí podrás encontrar diferentes temas de interés/Here you can find different interesting topics", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtayuda1=(TextView) findViewById(R.id.ayuda1);
        txtayuda2=(TextView) findViewById(R.id.ayuda2);
        txtayuda3=(TextView) findViewById(R.id.ayuda3);
        txtayuda4=(TextView) findViewById(R.id.txt_entrada);
        txtayuda1.setText( Html.fromHtml(getString(R.string.sobre_app)));
        txtayuda2.setText( Html.fromHtml(getString(R.string.software)));
        txtayuda3.setText( Html.fromHtml(getString(R.string.manuales)));
        txtayuda4.setText( Html.fromHtml(getString(R.string.txt_entrada)));

        español=(RadioButton) findViewById(R.id.español);
        ingles=(RadioButton) findViewById(R.id.ingles);
        español.setText(getString(R.string.español));
        ingles.setText(getString(R.string.ingles));



        RadioGroup radioGroupidioma=(RadioGroup) findViewById(R.id.idioma);
        radioGroupidioma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int checkedId) {

                switch (checkedId){
                    case R.id.español:
                        txtayuda1.setText( Html.fromHtml(getString(R.string.sobre_app)));
                        txtayuda2.setText( Html.fromHtml(getString(R.string.software)));
                        txtayuda3.setText( Html.fromHtml(getString(R.string.manuales)));
                        txtayuda4.setText( Html.fromHtml(getString(R.string.txt_entrada)));
                        español.setText(getString(R.string.español));
                        ingles.setText(getString(R.string.ingles));
                        idioma="Español";
                        break;
                    case R.id.ingles:
                        txtayuda1.setText( Html.fromHtml(getString(R.string.sobre_app_en)));
                        txtayuda2.setText( Html.fromHtml(getString(R.string.software_en)));
                        txtayuda3.setText( Html.fromHtml(getString(R.string.manuales_en)));
                        txtayuda4.setText( Html.fromHtml(getString(R.string.txt_entrada2)));
                        español.setText(getString(R.string.español2));
                        ingles.setText(getString(R.string.ingles2));
                        idioma="Ingles";
                        break;
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
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

        Intent intent=new Intent(this,ActividadAyuda2.class);
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
