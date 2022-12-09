package com.example.myapplication;

public class Categoria {
    public int id;
    public int image;
    public String nombre;
    public String subcat;

    public int colorR;
    public int colorG;
    public int colorB;
    public int tipo_cat;

    public Categoria(int id, int image, String nombre, String subcat, int colorR, int colorG, int colorB) {
        this.id = id;
        this.image = image;
        this.nombre = nombre;
        this.subcat = subcat;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public Categoria(int id, int image, String nombre, String subcat, int colorR, int colorG, int colorB, int tipo_cat) {
        this.id = id;
        this.image = image;
        this.nombre = nombre;
        this.subcat = subcat;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.tipo_cat = tipo_cat;
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

    public int getColorR() {
        return colorR;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }
}