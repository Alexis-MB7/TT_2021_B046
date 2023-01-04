package com.example.myapplication.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;

import java.util.ArrayList;
import java.util.Calendar;

public class BD_handler extends BD_helper{
    Context context;

    public BD_handler(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long createCategoria(Categoria cat){
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

    public long createMovimiento(Movimiento mov){
        long l = 0;

        try {
            BD_helper helper = new BD_helper(context);
            SQLiteDatabase bd = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("monto_mov", mov.getMonto());
            values.put("desc_mov", mov.getDescripcion());
            values.put("idcat_mov", mov.getCat().getId());
            values.put("tipo_mov", mov.getTipo());
            values.put("año_mov", mov.getFechaCompleta().get(Calendar.YEAR));
            values.put("mes_mov", mov.getFechaCompleta().get(Calendar.MONTH) + 1);
            values.put("dia_mov", mov.getFechaCompleta().get(Calendar.DAY_OF_MONTH));
            values.put("hora_mov", mov.getFechaCompleta().get(Calendar.HOUR_OF_DAY));
            values.put("minuto_mov", mov.getFechaCompleta().get(Calendar.MINUTE));

            l = bd.insert("movimiento", null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return l;
    }

    public ArrayList<Movimiento> readMovimientos(){
        BD_helper helper = new BD_helper(context);
        SQLiteDatabase bd = helper.getWritableDatabase();

        ArrayList<Categoria> cats = readCategorias();
        Categoria cat_mov =  null;
        ArrayList<Movimiento> lista = new ArrayList<>();
        Movimiento mov;
        Cursor cursor = null;

        int id, idcat, tipo_mov, año, mes, dia, hora, minuto;
        float monto;
        String desc;

        cursor = bd.rawQuery("SELECT * FROM `movimiento`",null);

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(0);
                monto = cursor.getFloat(1);
                desc = cursor.getString(2);
                idcat = cursor.getInt(3);
                tipo_mov = cursor.getInt(4);
                año = cursor.getInt(5);
                mes = cursor.getInt(6);
                dia = cursor.getInt(7);
                hora = cursor.getInt(8);
                minuto = cursor.getInt(9);

                for (Categoria element : cats){
                    if (element.getId() == idcat){
                        cat_mov = element;
                    }
                }

                mov = new Movimiento(id,monto,desc,cat_mov,hora,minuto,año,mes,dia);
                mov.setTipo(tipo_mov);
                lista.add(mov);
                System.out.println("Movimiento leido " + id);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return lista;
    }

    public long createDatosPersonales(String nombre, int edad, String correo, boolean huella, boolean pin){
        long id = 0;

        try {
            BD_helper helper = new BD_helper(context);
            SQLiteDatabase bd = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre_usuario", nombre);
            values.put("edad_usuario", edad);
            values.put("correo_usuario", correo);
            values.put("seguridad_huella", huella);
            values.put("seguridad_pin", pin);

            id = bd.insert("datos", null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

}
