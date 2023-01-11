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

    public void insertFijo(Context context, Movimiento movimiento, int period, int reps){
        BD_handler handler = new BD_handler(context);

        long id = handler.createMovimientoFijo(movimiento, period, reps);
        if(id > 0){
            ArrayList<Movimiento> lista = getLista_fijos().getValue();
            lista.add(movimiento);
            setLista_fijos(lista);
            Toast.makeText(context, "Se guardo el movimiento recurrente", Toast.LENGTH_SHORT).show();
            System.out.println("Se guardo correctamente el movimiento recurrente");
        }else {
            System.out.println("Error al guardar el movimiento recurrente");
        }

    }

}
