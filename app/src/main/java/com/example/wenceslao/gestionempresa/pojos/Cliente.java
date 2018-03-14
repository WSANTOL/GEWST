package com.example.wenceslao.gestionempresa.pojos;

import android.graphics.Bitmap;

import com.example.wenceslao.gestionempresa.constantes.G;

/**
 * Created by wenceslao on 30/10/2017.
 */

public class Cliente {
    private int ID;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private Bitmap imagen;

    public Cliente() {
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.apellidos = G.SIN_VALOR_STRING;
        this.email = G.SIN_VALOR_STRING;
        this.telefono = G.SIN_VALOR_STRING;
        this.setImagen(null);
    }

    public Cliente(int ID, String nombre, String apellidos, String email, String telefono, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.imagen=imagen;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
