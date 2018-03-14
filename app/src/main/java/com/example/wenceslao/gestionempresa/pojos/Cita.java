package com.example.wenceslao.gestionempresa.pojos;

import android.graphics.Bitmap;

import com.example.wenceslao.gestionempresa.constantes.G;

/**
 * Created by wenceslao on 02/11/2017.
 */

public class Cita {

    private int ID;
    private int dia;
    private int mes;
    private int anho;
    private int hora;
    private int minutos;
    private String servicio;
    private int cod_cliente;
    private int cod_empleado;
    private Bitmap imagen;

    public Cita(int ID, int dia, int mes, int anho, int hora, int minutos, String servicio, int cod_cliente, int cod_empleado, Bitmap imagen) {
        this.ID = ID;
        this.dia = dia;
        this.mes = mes;
        this.anho = anho;
        this.hora = hora;
        this.minutos = minutos;
        this.servicio = servicio;
        this.cod_cliente = cod_cliente;
        this.cod_empleado = cod_empleado;
        this.imagen=imagen;
    }

    public Cita() {
        this.ID = G.SIN_VALOR_INT;
        this.dia = G.SIN_VALOR_INT;
        this.mes = G.SIN_VALOR_INT;
        this.anho = G.SIN_VALOR_INT;
        this.hora = G.SIN_VALOR_INT;
        this.minutos = G.SIN_VALOR_INT;
        this.servicio = G.SIN_VALOR_STRING;
        this.cod_cliente = G.SIN_VALOR_INT;
        this.cod_empleado = G.SIN_VALOR_INT;
        this.setImagen(null);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getCod_cliente() {
        return cod_cliente;
    }

    public void setCod_cliente(int cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    public int getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(int cod_empleado) {
        this.cod_empleado = cod_empleado;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
