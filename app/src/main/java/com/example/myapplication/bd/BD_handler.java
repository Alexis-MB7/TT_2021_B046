package com.example.myapplication.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.Categoria;

import java.util.ArrayList;

public class BD_handler extends BD_helper{
    Context context;

    public BD_handler(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Categoria> readCategorias(){
        BD_helper helper = new BD_helper(context);
        SQLiteDatabase bd = helper.getWritableDatabase();

        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria cat;
        Cursor cursor = null;

        int id, r, g, b, tipo_cat;
        String imagen, nombre;

        cursor = bd.rawQuery("SELECT * FROM `categoria`",null);

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
                nombre = cursor.getString(1);
                imagen = cursor.getString(2);
                r = cursor.getInt(3);
                g = cursor.getInt(4);
                b = cursor.getInt(5);
                tipo_cat = cursor.getInt(6);
                cat = new Categoria(id,Integer.parseInt(imagen),nombre,r,g,b,tipo_cat);
                lista.add(cat);
                System.out.println("Categoria leida" + id);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return lista;
    }

    public long insertCategoria(Categoria cat){
        long id = 0;

        try {
            BD_helper helper = new BD_helper(context);
            SQLiteDatabase bd = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", cat.getNombre());
            values.put("imagen", Integer.toString(cat.getImage()));
            values.put("color_R", cat.getColorR());
            values.put("color_G", cat.getColorG());
            values.put("color_B", cat.getColorB());
            values.put("idtipo_cat", cat.getTipo_cat());

            id = bd.insert("categoria", null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean updateCategoria(int id, Categoria cat){
        boolean l;
        BD_helper helper = new BD_helper(context);
        SQLiteDatabase bd = helper.getWritableDatabase();

        try {
            bd.execSQL("UPDATE `categoria` SET " +
                    "nombre = '" + cat.getNombre() + "', " +
                    "imagen = '" + Integer.toString(cat.getImage()) + "'," +
                    "color_R = '" + cat.getColorR() + "'," +
                    "color_G = '" + cat.getColorG() + "'," +
                    "color_B = '" + cat.getColorB() + "'," +
                    "idtipo_cat = '" + cat.getTipo_cat() + "'" +
                    "WHERE idcategoria = '" + id + "'");
            l = true;
        }catch (Exception ex){
            l = false;
            System.out.println("Error al actualizar la categoria");
            ex.toString();
        } finally {
            bd.close();
        }

        return l;
    }

    public boolean deleteCategoria(int id){
        boolean l;
        BD_helper helper = new BD_helper(context);
        SQLiteDatabase bd = helper.getWritableDatabase();

        try {
            bd.execSQL("DELETE FROM `categoria` WHERE idcategoria = '" + id + "'");
            l = true;
        }catch (Exception ex){
            l = false;
            ex.toString();
        } finally {
            bd.close();
        }

        return l;
    }

}
