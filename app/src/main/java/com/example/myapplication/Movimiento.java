package com.example.myapplication;

public class Movimiento {
    public int id;
    public float monto;
    public String descripcion;
    public Categoria cat;

    public Movimiento(int id, float monto, String descripcion, Categoria cat) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cat = cat;

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