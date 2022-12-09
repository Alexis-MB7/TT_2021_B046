package com.example.myapplication.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Categoria;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CategoriaViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Categoria>> lista_cat = new MutableLiveData<ArrayList<Categoria>>();


    public void setLista_cat(ArrayList<Categoria> cats){
        lista_cat.setValue(cats);
    }

    public LiveData<ArrayList<Categoria>> getLista_cat(){
        return  lista_cat;
    }
}
