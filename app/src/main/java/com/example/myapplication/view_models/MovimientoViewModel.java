package com.example.myapplication.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Movimiento;

import java.util.ArrayList;

public class MovimientoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movimiento>> lista_mov = new MutableLiveData<ArrayList<Movimiento>>();

    public void setLista_mov(ArrayList<Movimiento> movs){
        lista_mov.setValue(movs);

    }

    public LiveData<ArrayList<Movimiento>> getLista_mov(){
        return lista_mov;
    }
}
