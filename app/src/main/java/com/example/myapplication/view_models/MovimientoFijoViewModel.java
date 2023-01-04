package com.example.myapplication.view_models;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Movimiento;
import com.example.myapplication.bd.BD_handler;

import java.util.ArrayList;

public class MovimientoFijoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movimiento>> lista_mov = new MutableLiveData<ArrayList<Movimiento>>();

    public void setLista_fijos(ArrayList<Movimiento> movs){
        lista_mov.setValue(movs);
    }

    public LiveData<ArrayList<Movimiento>> getLista_fijos(){
        return lista_mov;
    }

    public void initLista_fijos(Context context){
        BD_handler handler = new BD_handler(context);
        setLista_fijos(handler.readMovimientosFijos());
    }

}
