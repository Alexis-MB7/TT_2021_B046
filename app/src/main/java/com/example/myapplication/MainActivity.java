package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.bd.BD_helper;
import com.example.myapplication.fragments.CategoriasFragment;
import com.example.myapplication.fragments.CuentaFragment;
import com.example.myapplication.fragments.EstadisticasFragment;
import com.example.myapplication.fragments.InicioFragment;
import com.example.myapplication.fragments.MovimientoAddFragment;
import com.example.myapplication.fragments.MovimientosFragment;
import com.example.myapplication.fragments.PrediccionesFragment;
import com.example.myapplication.fragments.PresupuestoNuevoFragment;
import com.example.myapplication.fragments.ProyectoNuevoFragment;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.example.myapplication.view_models.MovimientoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static FloatingActionButton fab;
    int mov_flag = 0;
    String[] tipos;

    //ViewModel
    private CategoriaViewModel cat_vm;
    private MovimientoViewModel mov_vm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipos = getResources().getStringArray(R.array.tipos);

        BD_helper bd_helper = new BD_helper(this);
        SQLiteDatabase bd = bd_helper.getWritableDatabase();
        if(bd != null){
            System.out.println("Se creo la BD");
        }else{
            System.out.println("Error al crear la BD");
        }



        cat_vm = new ViewModelProvider(this).get(CategoriaViewModel.class);
        //cat_vm.setLista_cat(lista_default);
        cat_vm.initLista_cat(this);
        cat_vm.getLista_cat().observe(this,cats ->{
            System.out.println("Categorias modificadas");
        });

        ArrayList<Categoria> lista_default = cat_vm.getLista_cat().getValue();

        ArrayList<Movimiento> movs_default = new ArrayList<>();
        movs_default.add(new Movimiento(1,10.5f,"Papitas",lista_default.get(0),12,35,2022,12,10));
        movs_default.add(new Movimiento(2,150.00f,"Hamburguesas",lista_default.get(0),14,15,2022,12,10));
        movs_default.add(new Movimiento(3,27.5f,"Refresco",lista_default.get(0),12,15,2022,12,10));

        mov_vm = new ViewModelProvider(this).get(MovimientoViewModel.class);
        mov_vm.setLista_mov(movs_default);
        mov_vm.getLista_mov().observe(this, movs ->{
            System.out.println("Movimientos modificados");
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_inicio);
        }
        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                mov_flag = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovimientoAddFragment()).commit();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_inicio:
                    fab.show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                    break;
                case R.id.nav_categorias:
                    fab.show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriasFragment()).commit();
                    break;
                case R.id.nav_estadisticas:
                    fab.show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EstadisticasFragment()).commit();
                    break;
                case R.id.nav_movimientos:
                    fab.show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovimientosFragment()).commit();
                    break;
                case R.id.nav_cuenta:
                    fab.hide();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CuentaFragment()).commit();
                    break;
                case R.id.nav_Predicciones:
                    fab.show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrediccionesFragment()).commit();
                    break;
                case R.id.nav_proyectos:
                    fab.hide();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProyectoNuevoFragment()).commit();
                    break;
                case R.id.nav_presupuesto:
                    fab.hide();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PresupuestoNuevoFragment()).commit();
                    break;

            }
            drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }

    }

    public static void showFab(){
        fab.show();;

    }

}