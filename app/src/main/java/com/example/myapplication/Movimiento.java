package com.example.myapplication;

public class Movimiento {
    public int id;
    public int iconoCategoria;
    public float monto;
    public String categoria;
    public String descripcion;
    public int ColorR;
    public int ColorG;
    public int ColorB;

    public Movimiento(int id, int iconoCategoria, float monto, String categoria, String descripcion) {
        this.id = id;
        this.iconoCategoria = iconoCategoria;
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Movimiento(int id, int iconoCategoria, float monto, String categoria, String descripcion, int colorR, int colorG, int colorB) {
        this.id = id;
        this.iconoCategoria = iconoCategoria;
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
        ColorR = colorR;
        ColorG = colorG;
        ColorB = colorB;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconoCategoria() {
        return iconoCategoria;
    }

    public void setIconoCategoria(int iconoCategoria) {
        this.iconoCategoria = iconoCategoria;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getColorR() {
        return ColorR;
    }

    public void setColorR(int colorR) {
        ColorR = colorR;
    }

    public int getColorG() {
        return ColorG;
    }

    public void setColorG(int colorG) {
        ColorG = colorG;
    }

    public int getColorB() {
        return ColorB;
    }

    public void setColorB(int colorB) {
        ColorB = colorB;
    }
}
