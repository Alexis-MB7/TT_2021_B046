package com.example.myapplication.view_models;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.bd.BD_handler;

import java.util.ArrayList;

public class MovimientoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movimiento>> lista_mov = new MutableLiveData<ArrayList<Movimiento>>();

    public void setLista_mov(ArrayList<Movimiento> movs){
        lista_mov.setValue(movs);
    }

    public LiveData<ArrayList<Movimiento>> getLista_mov(){
        return lista_mov;
    }

    public void initLista_mov(Context context){
        BD_handler handler = new BD_handler(context);
        setLista_mov(handler.readMovimientos());
    }

    public void insertMov(Context context, Movimiento movimiento){
        BD_handler handler = new BD_handler(context);

        long id = handler.createMovimiento(movimiento);
        if(id > 0){
            ArrayList<Movimiento> lista = getLista_mov().getValue();
            lista.add(movimiento);
            setLista_mov(lista);
            Toast.makeText(context, "Se guardo el movimiento", Toast.LENGTH_SHORT).show();
            System.out.println("Se guardo correctamente el movimiento");
        }else {
            System.out.println("Error al guardar el movimiento");
        }

    }
}
