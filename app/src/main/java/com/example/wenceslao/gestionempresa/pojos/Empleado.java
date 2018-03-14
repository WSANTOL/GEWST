package com.example.wenceslao.gestionempresa.pojos;

import android.graphics.Bitmap;

import com.example.wenceslao.gestionempresa.constantes.G;

/**
 * Created by wenceslao on 31/10/2017.
 */

public class Empleado {

    private int ID;
    private String nombre_completo;
    private String formacion;
    private String email;
    private String telefono;
    private Bitmap imagen;

    public Empleado(int ID, String nombre_completo, String formacion, String email, String telefono, Bitmap imagen) {
        this.ID = ID;
        this.nombre_completo = nombre_completo;
        this.formacion = formacion;
        this.email = email;
        this.telefono = telefono;
        this.imagen=imagen;

    }

    public Empleado() {
        this.ID = G.SIN_VALOR_INT;
        this.nombre_completo = G.SIN_VALOR_STRING;
        this.formacion = G.SIN_VALOR_STRING;
        this.email = G.SIN_VALOR_STRING;
        this.telefono = G.SIN_VALOR_STRING;
        this.setImagen(null);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
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
