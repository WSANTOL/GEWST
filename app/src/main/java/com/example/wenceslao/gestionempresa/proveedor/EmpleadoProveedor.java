package com.example.wenceslao.gestionempresa.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.pojos.Empleado;

import java.io.IOException;

/**
 * Created by wenceslao on 29/10/2017.
 */

public class EmpleadoProveedor {

    static public void insert(ContentResolver resolver, Empleado empleado, Context contexto){
        Uri uri=ContratoEmpleado.Empleado.CONTENT_URI;

        ContentValues values=new ContentValues();
        values.put(ContratoEmpleado.Empleado.NOMBRE_COMPLETO,empleado.getNombre_completo());
        values.put(ContratoEmpleado.Empleado.FORMACION,empleado.getFormacion());
        values.put(ContratoEmpleado.Empleado.EMAIL,empleado.getEmail());
        values.put(ContratoEmpleado.Empleado.TELEFONO,empleado.getTelefono());


        //para llamar al proveedor de contenidos
        Uri uriResultado=resolver.insert(uri,values);
        //obtener identificador del ciclo
        String empleadoid=uriResultado.getLastPathSegment();

        //guardar la imagen
        if(empleado.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(empleado.getImagen(),contexto,"img_empleado"+empleadoid+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome= Utilidades.guardarMemoriaExterna(empleado.getImagen(),contexto,"img_empleado"+empleadoid+".jpg",resolver);
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

    static public void delete(ContentResolver resolver,int empleadoId){
        Uri uri=Uri.parse(ContratoEmpleado.Empleado.CONTENT_URI + "/" + empleadoId);
        resolver.delete(uri,ContratoEmpleado.Empleado._ID+"="+empleadoId,null);

    }

    static public void update(ContentResolver resolver, Empleado empleado, Context contexto){
        Uri uri=Uri.parse(ContratoEmpleado.Empleado.CONTENT_URI + "/" + empleado.getID());

        ContentValues values=new ContentValues();
        values.put(ContratoEmpleado.Empleado.NOMBRE_COMPLETO,empleado.getNombre_completo());
        values.put(ContratoEmpleado.Empleado.FORMACION,empleado.getFormacion());
        values.put(ContratoEmpleado.Empleado.EMAIL,empleado.getEmail());
        values.put(ContratoEmpleado.Empleado.TELEFONO,empleado.getTelefono());


        //resolver.update(uri,values,null,null);
        resolver.update(uri,values,ContratoEmpleado.Empleado._ID+"="+empleado.getID(),null);


        //guardar la imagen
        if(empleado.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(empleado.getImagen(),contexto,"img_empleado"+empleado.getID()+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome= Utilidades.guardarMemoriaExterna(empleado.getImagen(),contexto,"img_empleado"+empleado.getID()+".jpg",resolver);
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

    static public Empleado readRecord(ContentResolver resolver, int empleadoId){
        Uri uri=Uri.parse(ContratoEmpleado.Empleado.CONTENT_URI + "/" + empleadoId);
        
        String[] projection={
                ContratoEmpleado.Empleado.NOMBRE_COMPLETO,
                ContratoEmpleado.Empleado.FORMACION,
                ContratoEmpleado.Empleado.EMAIL,
                ContratoEmpleado.Empleado.TELEFONO
        };

        Cursor cursor=resolver.query(uri,projection,ContratoEmpleado.Empleado._ID+"="+empleadoId,null,null);
        
        if(cursor.moveToFirst()){
            Empleado empleado=new Empleado();
            
            empleado.setID(empleadoId);
            empleado.setNombre_completo(cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.NOMBRE_COMPLETO)));
            empleado.setFormacion(cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.FORMACION)));
            empleado.setEmail(cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.EMAIL)));
            empleado.setTelefono(cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.TELEFONO)));
            
            return empleado;
            
        }
        return null;

    }
}
