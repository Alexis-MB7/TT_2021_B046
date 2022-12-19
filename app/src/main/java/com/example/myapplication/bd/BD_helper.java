package com.example.myapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class BD_helper extends SQLiteOpenHelper {

    private static final int BD_VERSION = 4;
    private static final String BD_NOMBRE = "TT.db";
    public String query_1 = "CREATE TABLE `categoria` (" +
                            "  `idcategoria` INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  `nombre` TEXT NOT NULL," +
                            "  `imagen` TEXT NOT NULL," +
                            "  `color_R` INTEGER NOT NULL," +
                            "  `color_G` INTEGER NOT NULL," +
                            "  `color_B` INTEGER NOT NULL," +
                            "  `idtipo_cat` INT NOT NULL);";

    public String query_2 = "INSERT INTO `categoria` ('nombre', 'imagen', 'color_R', 'color_G', 'color_B', 'idtipo_cat')" +
                            "VALUES  ('Comida y Bebida'," + Integer.toString(R.drawable.ic_money) + ",255,79,55, 0)," +
                                    "('Transporte'," + Integer.toString(R.drawable.ic_money) + ",52,73,94, 0)," +
                                    "('Vivienda'," + Integer.toString(R.drawable.ic_money) + ", 211,84,0, 0)," +
                                    "('Compras'," + Integer.toString(R.drawable.ic_money) + ",155,89,182, 0)," +
                                    "('Ahorros'," + Integer.toString(R.drawable.ic_money) + ",52,152,219, 2)," +
                                    "('Ingresos'," + Integer.toString(R.drawable.ic_money) + ",39,174,96, 1);";


    public BD_helper(@Nullable Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query_1);
        sqLiteDatabase.execSQL(query_2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE 'categoria'");
        onCreate(sqLiteDatabase);
    }
}
