package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.fragments.CategoriasFragment;
import com.example.myapplication.fragments.CuentaFragment;
import com.example.myapplication.fragments.EstadisticasFragment;
import com.example.myapplication.fragments.InicioFragment;
import com.example.myapplication.fragments.MovimientosFragment;
import com.example.myapplication.fragments.PrediccionesFragment;
import com.example.myapplication.fragments.PresupuestoNuevoFragment;
import com.example.myapplication.fragments.ProyectoNuevoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
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
        } else {
            super.onBackPressed();
        }

    }
}