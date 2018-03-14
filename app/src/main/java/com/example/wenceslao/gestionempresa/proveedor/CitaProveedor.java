package com.example.wenceslao.gestionempresa.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.pojos.Cita;

import java.io.IOException;

/**
 * Created by wenceslao on 29/10/2017.
 */

public class CitaProveedor {
    private static final String CLIENTE_TABLE_NAME = "Cliente";
    private static final String EMPLEADO_TABLE_NAME = "Empleado";
    private static final String CITA_TABLE_NAME = "Cita";
    private static final String EMPLEADO_CITAS= EMPLEADO_TABLE_NAME +
            "INNER JOIN  " + CITA_TABLE_NAME +
            "ON"+ ContratoEmpleado.Empleado._ID+ " = "+ContratoCita.Cita.COD_EMPLEADO;

    private static final String[] columnas = new String[]{
            EMPLEADO_TABLE_NAME + "." + ContratoEmpleado.Empleado._ID,
            ContratoCita.Cita.DIA,
            ContratoCita.Cita.MES,
            ContratoCita.Cita.ANHO,
            ContratoCita.Cita.HORA,
            ContratoCita.Cita.MINUTO,
            ContratoCita.Cita.SERVICIO,
            ContratoEmpleado.Empleado.NOMBRE_COMPLETO};
    public static ProveedorDeContenido.DatabaseHelper dbHelper;

    static public void insert(ContentResolver resolver, Cita cita, Context contexto){
        Uri uri=ContratoCita.Cita.CONTENT_URI;

        ContentValues values=new ContentValues();
        values.put(ContratoCita.Cita.DIA,cita.getDia());
        values.put(ContratoCita.Cita.MES,cita.getMes());
        values.put(ContratoCita.Cita.ANHO,cita.getAnho());
        values.put(ContratoCita.Cita.HORA,cita.getHora());
        values.put(ContratoCita.Cita.MINUTO,cita.getMinutos());
        values.put(ContratoCita.Cita.SERVICIO,cita.getServicio());
        values.put(ContratoCita.Cita.COD_CLIENTE,cita.getCod_cliente());
        values.put(ContratoCita.Cita.COD_EMPLEADO,cita.getCod_empleado());

        //para llamar al proveedor de contenidos
        Uri uriResultado=resolver.insert(uri,values);
        //obtener identificador del ciclo
        String citaid=uriResultado.getLastPathSegment();

        //guardar la imagen
        if(cita.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(cita.getImagen(),contexto,"img_cita"+citaid+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome=Utilidades.guardarMemoriaExterna(cita.getImagen(),contexto,"img_cita"+citaid+".jpg",resolver);
                    if(exitome){
                        Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                    }
                }else if(estado.equals( Environment.MEDIA_MOUNTED_READ_ONLY)){
                    sdDisponible=true;
                    sdAccesoEscritura=false;
                    Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                }else{
                    sdDisponible=false;
                    sdAccesoEscritura=false;
                    Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                Toast.makeText(contexto, "ERROR: No se pudo guardar la imagen en MEMORIA INTERNA",Toast.LENGTH_LONG).show();
            }
        }
    }

    static public void delete(ContentResolver resolver,int citaId){
        Uri uri=Uri.parse(ContratoCita.Cita.CONTENT_URI + "/" + citaId);
        //resolver.delete(uri,null,null);
        resolver.delete(uri,ContratoCita.Cita._ID+"="+citaId,null);

    }

    static public void update(ContentResolver resolver,Cita cita, Context contexto){
        Uri uri=Uri.parse(ContratoCita.Cita.CONTENT_URI + "/" + cita.getID());

        ContentValues values=new ContentValues();
        values.put(ContratoCita.Cita.DIA,cita.getDia());
        values.put(ContratoCita.Cita.MES,cita.getMes());
        values.put(ContratoCita.Cita.ANHO,cita.getAnho());
        values.put(ContratoCita.Cita.HORA,cita.getHora());
        values.put(ContratoCita.Cita.MINUTO,cita.getMinutos());
        values.put(ContratoCita.Cita.SERVICIO,cita.getServicio());
        values.put(ContratoCita.Cita.COD_CLIENTE,cita.getCod_cliente());
        values.put(ContratoCita.Cita.COD_EMPLEADO,cita.getCod_empleado());

        //resolver.update(uri,values,null,null);
        resolver.update(uri,values,ContratoCita.Cita._ID+"="+cita.getID(),null);

        //guardar la imagen
        if(cita.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(cita.getImagen(),contexto,"img_cita"+cita.getID()+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome=Utilidades.guardarMemoriaExterna(cita.getImagen(),contexto,"img_cita"+cita.getID()+".jpg",resolver);
                    if(exitome){
                        Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();
                    }
                }else if(estado.equals( Environment.MEDIA_MOUNTED_READ_ONLY)){
                    sdDisponible=true;
                    sdAccesoEscritura=false;
                    Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();

                }else{
                    sdDisponible=false;
                    sdAccesoEscritura=false;
                    Toast.makeText(contexto,"ERROR al guardar la imagen en MEMORIA EXTERNA", Toast.LENGTH_SHORT).show();

                }

            } catch (IOException e) {
                Toast.makeText(contexto, "ERROR: No se pudo guardar la imagen en MEMORIA INTERNA",Toast.LENGTH_LONG).show();
            }
        }

    }

    static public Cita readRecord(ContentResolver resolver, int citaId){
        Uri uri=Uri.parse(ContratoCita.Cita.CONTENT_URI + "/" + citaId);
        
        String[] projection={
                ContratoCita.Cita.DIA,
                ContratoCita.Cita.MES,
                ContratoCita.Cita.ANHO,
                ContratoCita.Cita.HORA,
                ContratoCita.Cita.MINUTO,
                ContratoCita.Cita.SERVICIO,
                ContratoCita.Cita.COD_CLIENTE,
                ContratoCita.Cita.COD_EMPLEADO

        };

        Cursor cursor=resolver.query(uri,projection,ContratoCita.Cita._ID+"="+citaId,null,null);
        
        if(cursor.moveToFirst()){
            Cita cita=new Cita();
            
            cita.setID(citaId);
            cita.setDia(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.DIA ) ) );
            cita.setMes(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.MES ) ) );
            cita.setAnho(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.ANHO ) ) );
            cita.setHora(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.HORA ) ) );
            cita.setMinutos(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.MINUTO ) ) );
            cita.setServicio(cursor.getString( cursor.getColumnIndex( ContratoCita.Cita.SERVICIO ) ) );
            cita.setCod_cliente(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.COD_CLIENTE ) ) );
            cita.setCod_empleado(cursor.getInt( cursor.getColumnIndex( ContratoCita.Cita.COD_EMPLEADO ) ) );

            
            return cita;
            
        }
        return null;

    }

    static public Cursor todo(ContentResolver resolver){
        Uri uri=ContratoCita.Cita.CONTENT_URI;

        String[] projection={
                ContratoCita.Cita._ID,
                ContratoCita.Cita.DIA,
                ContratoCita.Cita.MES,
                ContratoCita.Cita.ANHO,
                ContratoCita.Cita.HORA,
                ContratoCita.Cita.MINUTO,
                ContratoCita.Cita.SERVICIO,
                ContratoCita.Cita.COD_CLIENTE,
                ContratoCita.Cita.COD_EMPLEADO

        };

        Cursor cursor=resolver.query(uri,projection,null,null,null);

        return cursor;

    }

    static public Cursor consulta1(ContentResolver resolver, int dia,int mes,int año){
        Uri uri=ContratoCita.Cita.CONTENT_URI;

        String[] projection={
                ContratoCita.Cita._ID,
                ContratoCita.Cita.DIA,
                ContratoCita.Cita.MES,
                ContratoCita.Cita.ANHO,
                ContratoCita.Cita.HORA,
                ContratoCita.Cita.MINUTO,
                ContratoCita.Cita.SERVICIO,
                ContratoCita.Cita.COD_CLIENTE,
                ContratoCita.Cita.COD_EMPLEADO

        };
        String seleccion="("+ContratoCita.Cita.DIA+"="+dia+") AND ("+ContratoCita.Cita.MES+"="+mes+") AND ("+ContratoCita.Cita.ANHO+"="+año+")";

        Cursor cursor=resolver.query(uri,projection,seleccion,null,null);

        return cursor;

    }

    static public Cursor consulta2(ContentResolver resolver, int cod_empleado){
        Uri uri=ContratoCita.Cita.CONTENT_URI;

        String[] projection={
                ContratoCita.Cita._ID,
                ContratoCita.Cita.DIA,
                ContratoCita.Cita.MES,
                ContratoCita.Cita.ANHO,
                ContratoCita.Cita.HORA,
                ContratoCita.Cita.MINUTO,
                ContratoCita.Cita.SERVICIO,
                ContratoCita.Cita.COD_CLIENTE,
                ContratoCita.Cita.COD_EMPLEADO

        };
        String seleccion=ContratoCita.Cita.COD_EMPLEADO+"="+cod_empleado;

        Cursor cursor=resolver.query(uri,projection,seleccion,null,null);

        return cursor;

    }

    static public Cursor consulta3(ContentResolver resolver, int cod_cliente){
        Uri uri=ContratoCita.Cita.CONTENT_URI;

        String[] projection={
                ContratoCita.Cita._ID,
                ContratoCita.Cita.DIA,
                ContratoCita.Cita.MES,
                ContratoCita.Cita.ANHO,
                ContratoCita.Cita.HORA,
                ContratoCita.Cita.MINUTO,
                ContratoCita.Cita.SERVICIO,
                ContratoCita.Cita.COD_CLIENTE,
                ContratoCita.Cita.COD_EMPLEADO

        };
        String seleccion=ContratoCita.Cita.COD_CLIENTE+"="+cod_cliente;

        Cursor cursor=resolver.query(uri,projection,seleccion,null,null);

        return cursor;

    }

    static public Cursor consultaMultiple(String nombre){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EMPLEADO_CITAS);
        String seleccion=ContratoEmpleado.Empleado.NOMBRE_COMPLETO+"="+nombre;

        return qb.query(db,columnas,seleccion,null,null,null,null );

    }





}
