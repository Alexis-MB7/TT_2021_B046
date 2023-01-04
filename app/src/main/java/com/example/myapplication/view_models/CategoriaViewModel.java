package com.example.myapplication.view_models;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Categoria;
import com.example.myapplication.bd.BD_handler;

import java.util.ArrayList;

public class CategoriaViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Categoria>> lista_cat = new MutableLiveData<ArrayList<Categoria>>();

    public void setLista_cat(ArrayList<Categoria> cats){
        lista_cat.setValue(cats);
    }

    public LiveData<ArrayList<Categoria>> getLista_cat(){
        return  lista_cat;
    }

    public void initLista_cat(Context context){
        BD_handler handler = new BD_handler(context);
        setLista_cat(handler.readCategorias());
    }

    public void insertCat(Context context, Categoria cat){
        BD_handler handler = new BD_handler(context);

        long id = handler.createCategoria(cat);
        if(id > 0){
            ArrayList<Categoria> lista = getLista_cat().getValue();
            lista.add(cat);
            setLista_cat(lista);
            Toast.makeText(context, "Se guardo la categoria", Toast.LENGTH_SHORT).show();
            System.out.println("Se guardo correctamente la categoria");
        }else {
            System.out.println("Error al guardar la categoria");
        }
    }

    public void updateCat(Context context, Categoria cat, int id_cat){
        BD_handler handler = new BD_handler(context);

        if(handler.updateCategoria(id_cat,cat)){
            ArrayList<Categoria> lista = handler.readCategorias();
            setLista_cat(lista);
            Toast.makeText(context, "Se actualizo la categoria", Toast.LENGTH_SHORT).show();
            System.out.println("Se actualizo correctamente la categoria");
        }else {
            System.out.println("Error al actualizar la categoria");
        }
    }

    public void deleteCat(Context context, int id_cat){
        BD_handler handler = new BD_handler(context);

        if(handler.deleteCategoria(id_cat)){
            ArrayList<Categoria> lista = handler.readCategorias();
            setLista_cat(lista);
            Toast.makeText(context, "Se actualizo la categoria", Toast.LENGTH_SHORT).show();
            System.out.println("Se actualizo correctamente la categoria");
        }else {
            System.out.println("Error al actualizar la categoria");
        }
    }
}
