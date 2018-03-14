package com.example.wenceslao.gestionempresa;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.cita.*;
import com.example.wenceslao.gestionempresa.cita.CitaListFragment2;
import com.example.wenceslao.gestionempresa.cliente.ActividadCliente;
import com.example.wenceslao.gestionempresa.cliente.ClienteInsertarActivity2;
import com.example.wenceslao.gestionempresa.cliente.ClienteListFragment2;
import com.example.wenceslao.gestionempresa.empleado.ActividadEmpleado;
import com.example.wenceslao.gestionempresa.empleado.EmpleadoInsertarActivity2;
import com.example.wenceslao.gestionempresa.empleado.EmpleadoListFragment2;


import java.util.Locale;

public class ActividadNavigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int SPANISH=1;
    final int ENGLISH=2;

    private FragmentTabHost tabHost;
    private ViewPager mViewPager;
    private ActividadNavigator.SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_navigator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Elige la opción adecuada/Choose the right option", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActividadCita.class);
                startActivity(intent);
            }
        });

        TextView txtInfo=(TextView) findViewById(R.id.txtInfo);
        txtInfo.setText(Html.fromHtml(getString(R.string.info)));
        */

/*
        ImageButton cliente=(ImageButton) findViewById( R.id.imageCliente );
        cliente.
        cliente.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActividadCliente.class);
                startActivity(intent);
            }
        });

        ImageButton empleado=(ImageButton) findViewById( R.id.imageEmpleado );
        empleado.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActividadEmpleado.class);
                startActivity(intent);
            }
        });

        ImageButton cita=(ImageButton) findViewById( R.id.imageCita );
        cita.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActividadCita.class);
                startActivity(intent);
            }
        });



         */

        mSectionsPagerAdapter = new ActividadNavigator.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayoutHome=(TabLayout) findViewById(R.id.tabs_home);
        tabLayoutHome.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ActividadNavigator.PlaceholderFragment newInstance(int sectionNumber) {
            ActividadNavigator.PlaceholderFragment fragment = new ActividadNavigator.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );


        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView =null;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.actividad_cliente2, container, false);

                    ClienteListFragment2 cliente=new ClienteListFragment2();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_cliente2, cliente);
                    transaction.commit();
                    break;

                case 2:
                    rootView = inflater.inflate(R.layout.actividad_empleado2, container, false);

                    EmpleadoListFragment2 empleado=new EmpleadoListFragment2();

                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.add(R.id.fragment_empleado2, empleado);
                    transaction1.commit();
                    break;

                case 3:
                    rootView = inflater.inflate(R.layout.actividad_cita2, container, false);

                    CitaListFragment2 cita=new CitaListFragment2();

                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.add(R.id.fragment_cita2, cita);
                    transaction2.commit();
                    break;


            }

            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ActividadNavigator.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tabla_cliente) ;
                case 1:
                    return getString(R.string.tabla_empleado) ;
                case 2:
                    return getString(R.string.tabla_cita) ;
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.actividad_navigator_drawer, menu);
        getMenuInflater().inflate(R.menu.menu_actividad_navigator, menu);
        return true;
    }

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
                Intent intent=new Intent(ActividadNavigator.this,ActividadAyuda2.class);
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
            case R.id.action_nuevo_cliente:
                Intent intent2=new Intent(ActividadNavigator.this,ClienteInsertarActivity2.class);
                startActivity(intent2);
                break;
            case R.id.action_nuevo_empleado:
                Intent intent3=new Intent(ActividadNavigator.this,EmpleadoInsertarActivity2.class);
                startActivity(intent3);
                break;
            case R.id.action_nueva_cita:
                Intent intent4=new Intent(ActividadNavigator.this,CitaInsertarActivity2.class);
                startActivity(intent4);
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
        Intent intent=new Intent(this,ActividadNavigator.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //para refrescar toda la actividad
        startActivity(intent);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_servicios) {
            Intent intent=new Intent(getApplicationContext(),ActividadServicios.class);
            startActivity(intent);

        } else if (id == R.id.nav_addcita) {
            Intent intent=new Intent(ActividadNavigator.this,ActividadCita.class);
            startActivity(intent);

        } else if (id == R.id.nav_contacto) {
            Intent intent=new Intent(getApplicationContext(),ActividadContacto.class);
            startActivity(intent);


        } else if (id == R.id.nav_ayuda) {
            Intent intent=new Intent(getApplicationContext(),ActividadAyuda2.class);
            startActivity(intent);

        }else if (id == R.id.nav_cierre) {
            Toast.makeText(getApplicationContext(),R.string.cierre_app,Toast.LENGTH_SHORT).show();
            finish();
            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.addCategory(Intent.CATEGORY_HOME);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }else if (id == R.id.nav_clientes) {
            Intent intent=new Intent(ActividadNavigator.this,ActividadCliente.class);
            startActivity(intent);

        }else if (id == R.id.nav_empleados) {
            Intent intent=new Intent(ActividadNavigator.this,ActividadEmpleado.class);
            startActivity(intent);

        }else if (id == R.id.nav_consulta) {
            Intent intent = new Intent( ActividadNavigator.this, ActividadConsultas.class );
            startActivity( intent );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
