package com.example.myapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class BD_helper extends SQLiteOpenHelper {

    private static final int BD_VERSION = 9;
    private static final String BD_NOMBRE = "TT.db";

    public String tabla_categoria_query = "CREATE TABLE `categoria` (" +
                            "  `idcategoria` INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  `nombre` TEXT NOT NULL," +
                            "  `imagen` TEXT NOT NULL," +
                            "  `color_R` INTEGER NOT NULL," +
                            "  `color_G` INTEGER NOT NULL," +
                            "  `color_B` INTEGER NOT NULL," +
                            "  `idtipo_cat` INT NOT NULL);";

    public String tabla_datos_query = "CREATE TABLE 'datos' (" +
                                    "'iddatos' INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "'nombre_usuario' TEXT NOT NULL," +
                                    "'edad_usuario' INTEGER NOT NULL," +
                                    "'correo_usuario' TEXT NOT NULL," +
                                    "'seguridad_huella' INTEGER NOT NULL," +
                                    "'seguridad_pin' INTEGER NOT NULL);";

    public String tabla_movimiento_query =
                "CREATE TABLE 'movimiento' (" +
                "'idmovimiento' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'monto_mov' REAL NOT NULL," +
                "'desc_mov' TEXT NOT NULL," +
                "'idcat_mov' INTEGER NOT NULL," +
                "'tipo_mov' INTEGER NOT NULL," +
                "'año_mov' INTEGER NOT NULL," +
                "'mes_mov' INTEGER NOT NULL," +
                "'dia_mov' INTEGER NOT NULL," +
                "'hora_mov' INTEGER NOT NULL," +
                "'minuto_mov' INTEGER NOT NULL);";

    public String categoriasDefault_query =
                "INSERT INTO `categoria` ('nombre', 'imagen', 'color_R', 'color_G', 'color_B', 'idtipo_cat')" +
                "VALUES  ('Comida y Bebida'," + Integer.toString(R.drawable.ic_money) + ",255,79,55, 0)," +
                        "('Transporte'," + Integer.toString(R.drawable.ic_money) + ",52,73,94, 0)," +
                        "('Vivienda'," + Integer.toString(R.drawable.ic_money) + ", 211,84,0, 0)," +
                        "('Compras'," + Integer.toString(R.drawable.ic_money) + ",155,89,182, 0)," +
                        "('Ahorros'," + Integer.toString(R.drawable.ic_money) + ",52,152,219, 2)," +
                        "('Ingresos'," + Integer.toString(R.drawable.ic_money) + ",39,174,96, 1);";


    public String movimientosDefault_query =
                "INSERT INTO 'movimiento' ('monto_mov', 'desc_mov', 'idcat_mov', 'tipo_mov', 'año_mov', 'mes_mov', 'dia_mov', 'hora_mov', 'minuto_mov')" +
                "VALUES (10.5, 'Papitas', 1, 1, 2023, 01, 08, 12, 35)," +
                       "(150, 'Hamburguesas', 1, 1, 2023, 01, 21, 18, 45)," +
                       "(27.5, 'Refresco', 1, 1, 2023, 02, 07, 19, 0);";

    public BD_helper(@Nullable Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tabla_categoria_query);
        sqLiteDatabase.execSQL(tabla_datos_query);
        sqLiteDatabase.execSQL(tabla_movimiento_query);
        sqLiteDatabase.execSQL(categoriasDefault_query);
        sqLiteDatabase.execSQL(movimientosDefault_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'categoria'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'datos'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'movimiento'");
        onCreate(sqLiteDatabase);
    }
}
