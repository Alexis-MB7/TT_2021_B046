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
import com.example.myapplication.fragments.ProyectoFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                break;
            case R.id.nav_categorias:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriasFragment()).commit();
                break;
            case R.id.nav_estadisticas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EstadisticasFragment()).commit();
                break;
            case R.id.nav_movimientos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovimientosFragment()).commit();
                break;
            case R.id.nav_cuenta:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CuentaFragment()).commit();
                break;
            case R.id.nav_Predicciones:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrediccionesFragment()).commit();
                break;
            case R.id.nav_proyectos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProyectoFragment()).commit();
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