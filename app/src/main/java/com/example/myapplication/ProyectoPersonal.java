package com.example.myapplication;

import java.util.Calendar;

public class ProyectoPersonal {
    private String nombre_proy;
    private Calendar fecha_inicio;
    private float monto_obj;

    public ProyectoPersonal(String nombre_proy, Calendar fecha_inicio, float monto_obj, float monto_inicial, int num_periodo) {
        this.nombre_proy = nombre_proy;
        this.fecha_inicio = fecha_inicio;
        this.monto_obj = monto_obj;
        this.monto_inicial = monto_inicial;
        this.num_periodo = num_periodo;
    }

    private float monto_inicial;
    private int num_periodo;

    public String getNombre_proy() {
        return nombre_proy;
    }

    public void setNombre_proy(String nombre_proy) {
        this.nombre_proy = nombre_proy;
    }

    public Calendar getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Calendar fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public float getMonto_obj() {
        return monto_obj;
    }

    public void setMonto_obj(float monto_obj) {
        this.monto_obj = monto_obj;
    }

    public float getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(float monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public int getNum_periodo() {
        return num_periodo;
    }

    public void setNum_periodo(int num_periodo) {
        this.num_periodo = num_periodo;
    }

}
