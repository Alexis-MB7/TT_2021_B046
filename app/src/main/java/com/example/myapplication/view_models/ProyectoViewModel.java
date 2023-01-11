package com.example.myapplication.view_models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ProyectoPersonal;
import com.example.myapplication.bd.BD_handler;

import java.util.Calendar;

public class ProyectoViewModel extends ViewModel{
    private MutableLiveData<String> nom_proy = new MutableLiveData<String>();
    private MutableLiveData<Float> obj_proy = new MutableLiveData<Float>();
    private MutableLiveData<Float> ini_proy = new MutableLiveData<Float>();
    private MutableLiveData<Integer> tiempo_proy = new MutableLiveData<Integer>();
    private MutableLiveData<Calendar> fecha_proy = new MutableLiveData<Calendar>();

    public void setData(ProyectoPersonal pp){
        if(pp != null){
            nom_proy.setValue(pp.getNombre_proy());
            obj_proy.setValue(pp.getMonto_obj());
            ini_proy.setValue(pp.getMonto_inicial());
            tiempo_proy.setValue(pp.getNum_periodo());
            fecha_proy.setValue(pp.getFecha_inicio());
        }
    }

    public LiveData<String> getNom_proy() {
        return nom_proy;
    }

    public LiveData<Float> getObj_proy() {
        return obj_proy;
    }

    public LiveData<Float> getIni_proy() {
        return ini_proy;
    }

    public LiveData<Integer> getTiempo_proy() {
        return tiempo_proy;
    }

    public LiveData<Calendar> getFecha_proy() {
        return fecha_proy;
    }

    public void initProyecto(Context context){
        BD_handler handler = new BD_handler(context);
        setData(handler.readProyecto());
    }
}
