package com.example.myapplication;

import java.util.Calendar;

public class Movimiento {
    public int id;
    public float monto;
    public String descripcion;
    public Categoria cat;

    public Calendar fechaCompleta;
    public String tipo;



    public Movimiento(int id, float monto, String descripcion, Categoria cat) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cat = cat;

    }

    public Movimiento(int id, float monto, String tipo, String descripcion, Categoria cat, int horas, int minutos, int año, int mes, int dia) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cat = cat;
        this.tipo = tipo;
        fechaCompleta.set(año,mes,dia,horas,minutos);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }
}