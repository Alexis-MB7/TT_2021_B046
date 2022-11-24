package com.example.myapplication;

public class Categorias {
    public int id;
    public int image;
    public String nombre;
    public String subcat;

    public Categorias(int id, int image, String nombre, String subcat) {
        this.id = id;
        this.image = image;
        this.nombre = nombre;
        this.subcat = subcat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }
}
