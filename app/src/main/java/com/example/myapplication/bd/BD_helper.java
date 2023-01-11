package com.example.myapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class BD_helper extends SQLiteOpenHelper {

    private static final int BD_VERSION = 31;
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
                                    "'seguridad_pin' INTEGER NOT NULL," +
                                    "'proyecto' INTEGER NOT NULL);";

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

    public String tabla_movimiento_fijo_query =
                "CREATE TABLE `movimientos_fijos` (" +
                "`idmovimientos_fijos` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "`monto` REAL NOT NULL," +
                "`idcat` INTEGER NOT NULL," +
                "'año_mov' INTEGER NOT NULL," +
                "'mes_mov' INTEGER NOT NULL," +
                "'dia_mov' INTEGER NOT NULL," +
                "`desc` TEXT NOT NULL," +
                "`periodo` INTEGER NOT NULL," +
                "'num_repeticiones' INTEGER NOT NULL);";

    public String tabla_presupuesto_zero_query =
                "CREATE TABLE 'presupuesto_zero' (" +
                "'idzero' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'monto_zero' REAL NOT NULL," +
                "'idcategoria' INTEGER NOT NULL);";

    public String tabla_proyecto_personal_query =
            "CREATE TABLE 'proyecto_personal' (" +
                    "'idproyecto' INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "'nombre_proy' TEXT NOT NULL," +
                    "'monto_proy' REAL NOT NULL," +
                    "'inicial_proy' REAL NOT NULL," +
                    "'tiempo_proy' INTEGER NOT NULL," +
                    "'año_proy' INTEGER NOT NULL," +
                    "'mes_proy' INTEGER NOT NULL," +
                    "'dia_proy' INTEGER NOT NULL);";

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
                "VALUES (-185, 'Gasolina', 2, 0, 2023, 01, 08, 12, 35)," +
                       "(-150, 'Hamburguesas', 1, 1, 2023, 01, 21, 18, 45)," +
                       "(-27.5, 'Refresco', 1, 1, 2023, 02, 07, 19, 0)," +
                       "(-240, 'Ahorro Enero', 5, 3, 2023, 01, 01, 12, 0)," +
                        "(-240, 'Ahorro Septiembre', 5, 3, 2022, 09, 03, 12, 0)," +
                        "(-865.5, 'Restaurante', 1, 1, 2022, 10, 07, 17, 50)," +
                        "(-1460, 'Regalos Navideños', 4, 1, 2022, 12, 02, 14, 30)," +
                        "(-200, 'Estacionamiento', 2, 1, 2022, 12, 17, 22, 15)," +
                        "(-842, 'Restaurante', 1, 1, 2022, 10, 14, 21, 0)," +
                        "(7500, 'Sueldo', 6, 2, 2022, 12, 15, 12, 0)," +
                        "(10000, 'Sueldo y aguinaldo', 6, 2, 2022, 12, 30, 12, 0)," +
                        "(-6000, 'Renta', 3, 0, 2022, 11, 20, 13, 30)," +
                        "(-6000, 'Renta', 3, 0, 2022, 12, 20, 16, 40);";

    public String movimientosFijosDefault_query =
                "INSERT INTO 'movimientos_fijos' ('monto', 'idcat', 'año_mov', 'mes_mov', 'dia_mov', 'desc', 'periodo','num_repeticiones')" +
                "VALUES (5000, 6, 2023, 01, 05, '1ra Quincena', 30, 6)," +
                       "(5000, 6, 2023, 01, 20, '2da Quincena', 30, 6);";

    public String presupuestoZeroDefault_query =
                "INSERT INTO 'presupuesto_zero' ('monto_zero','idcategoria')" +
                "VALUES (500,0), (200,1), (5000,2), (950,3), (1200,4); ";

    public BD_helper(@Nullable Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tabla_categoria_query);
        sqLiteDatabase.execSQL(tabla_datos_query);
        sqLiteDatabase.execSQL(tabla_movimiento_query);
        sqLiteDatabase.execSQL(tabla_movimiento_fijo_query);
        sqLiteDatabase.execSQL(tabla_presupuesto_zero_query);
        sqLiteDatabase.execSQL(tabla_proyecto_personal_query);
        sqLiteDatabase.execSQL(categoriasDefault_query);
        sqLiteDatabase.execSQL(movimientosDefault_query);
        sqLiteDatabase.execSQL(movimientosFijosDefault_query);
        sqLiteDatabase.execSQL(presupuestoZeroDefault_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'categoria'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'datos'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'movimiento'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'movimientos_fijos'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'proyecto_personal'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'presupuesto_zero'");
        onCreate(sqLiteDatabase);
    }
}
