package com.example.myapplication;

public class FactorPredictivo {
    private Categoria categoria;
    private float monto;

    public FactorPredictivo(Categoria categoria, float monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
