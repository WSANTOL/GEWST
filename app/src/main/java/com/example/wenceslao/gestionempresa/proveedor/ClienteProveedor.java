package com.example.wenceslao.gestionempresa.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.pojos.Cliente;

import java.io.IOException;

/**
 * Created by wenceslao on 29/10/2017.
 */

public class ClienteProveedor {

    static public void insert(ContentResolver resolver, Cliente cliente,Context contexto){
        Uri uri=ContratoCliente.Cliente.CONTENT_URI;

        ContentValues values=new ContentValues();
        values.put(ContratoCliente.Cliente.NOMBRE,cliente.getNombre());
        values.put(ContratoCliente.Cliente.APELLIDOS,cliente.getApellidos());
        values.put(ContratoCliente.Cliente.EMAIL,cliente.getEmail());
        values.put(ContratoCliente.Cliente.TELEFONO,cliente.getTelefono());

        //para llamar al proveedor de contenidos
        Uri uriResultado=resolver.insert(uri,values);
        //obtener identificador del ciclo
        String clienteid=uriResultado.getLastPathSegment();

        //guardar la imagen
        if(cliente.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(cliente.getImagen(),contexto,"img_cliente"+clienteid+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome=Utilidades.guardarMemoriaExterna(cliente.getImagen(),contexto,"img_cliente"+clienteid+".jpg",resolver);
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

    static public void delete(ContentResolver resolver,int clienteId){
        Uri uri=Uri.parse(ContratoCliente.Cliente.CONTENT_URI + "/" + clienteId);
        //resolver.delete(uri,null,null);
        resolver.delete(uri,ContratoCliente.Cliente._ID+"="+clienteId,null);

    }

    static public void update(ContentResolver resolver,Cliente cliente, Context contexto){
        Uri uri=Uri.parse(ContratoCliente.Cliente.CONTENT_URI + "/" + cliente.getID());

        ContentValues values=new ContentValues();
        values.put(ContratoCliente.Cliente.NOMBRE,cliente.getNombre());
        values.put(ContratoCliente.Cliente.APELLIDOS,cliente.getApellidos());
        values.put(ContratoCliente.Cliente.EMAIL,cliente.getEmail());
        values.put(ContratoCliente.Cliente.TELEFONO,cliente.getTelefono());

        //resolver.update(uri,values,null,null);
        resolver.update(uri,values,ContratoCliente.Cliente._ID+"="+cliente.getID(),null);

        //guardar la imagen
        if(cliente.getImagen()!=null){
            boolean exitome=false;
            try {
                Utilidades.storeImage(cliente.getImagen(),contexto,"img_cliente"+cliente.getID()+".jpg");
                Toast.makeText(contexto,"Imagen guardada con exito en MEMORIA INTERNA", Toast.LENGTH_SHORT).show();

                Toast.makeText(contexto,"Espere un momento por favor", Toast.LENGTH_SHORT).show();

                boolean sdDisponible=false;
                boolean sdAccesoEscritura=false;
                //Comprobamos el estado de la memoria externa
                String estado= Environment.getExternalStorageState();
                if(estado.equals( Environment.MEDIA_MOUNTED)){
                    sdDisponible=true;
                    sdAccesoEscritura=true;
                    exitome=Utilidades.guardarMemoriaExterna(cliente.getImagen(),contexto,"img_cliente"+cliente.getID()+".jpg",resolver);
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

    static public Cliente readRecord(ContentResolver resolver, int clienteId){
        Uri uri=Uri.parse(ContratoCliente.Cliente.CONTENT_URI + "/" + clienteId);
        
        String[] projection={
                ContratoCliente.Cliente.NOMBRE,
                ContratoCliente.Cliente.APELLIDOS,
                ContratoCliente.Cliente.EMAIL,
                ContratoCliente.Cliente.TELEFONO
        };

        Cursor cursor=resolver.query(uri,projection,ContratoCliente.Cliente._ID+"="+clienteId,null,null);
        
        if(cursor.moveToFirst()){
            Cliente cliente=new Cliente();
            
            cliente.setID(clienteId);
            cliente.setNombre(cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.NOMBRE)));
            cliente.setApellidos(cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.APELLIDOS)));
            cliente.setEmail(cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.EMAIL)));
            cliente.setTelefono(cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.TELEFONO)));
            
            return cliente;
            
        }
        return null;

    }
}
