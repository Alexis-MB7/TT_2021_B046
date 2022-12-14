package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static FloatingActionButton fab;
    int mov_flag = 0;

    //ViewModel
    private CategoriaViewModel cat_vm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Categoria> lista_default = new ArrayList<>();
        lista_default.add(new Categoria(1, R.drawable.ic_money,"Comida y Bebida","2 Subcat",255,79,55));
        lista_default.add(new Categoria(2,R.drawable.ic_money,"Transporte","4 Subcat",52,73,94));
        lista_default.add(new Categoria(3,R.drawable.ic_money,"Vivienda","3 Subcat", 211,84,0));
        lista_default.add(new Categoria(4,R.drawable.ic_money,"Compras","5 Subcat",155,89,182));
        lista_default.add(new Categoria(5,R.drawable.ic_money,"Ahorros","1 Subcat",52,152,219));
        lista_default.add(new Categoria(6,R.drawable.ic_money,"Ingresos","2 Subcat",39,174,96));

        cat_vm = new ViewModelProvider(this).get(CategoriaViewModel.class);
        cat_vm.setLista_cat(lista_default);
        cat_vm.getLista_cat().observe(this,cats ->{

            System.out.println("Modificado");
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