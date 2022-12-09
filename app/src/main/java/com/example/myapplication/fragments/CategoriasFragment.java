package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.CategoriaAdapter;
import com.example.myapplication.Categoria;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends Fragment {
    ListView listViewCategorias;
    List<Categoria> categoriaList;
    CategoriaViewModel cats_vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.agregar);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriasAddFragment()).commit();
                return true;
            }
        });

        MenuItem item2 = menu.findItem(R.id.aceptar);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.guardar);
        item3.setVisible(false);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categorias");

        listViewCategorias = (ListView) view.findViewById(R.id.listViewCategorias);
        CategoriaAdapter adapter = new CategoriaAdapter(getActivity(), categoriaList);
        listViewCategorias.setAdapter(adapter);

    }

    private List<Categoria> fillData() {
        categoriaList = new ArrayList<>();



        categoriaList.add(new Categoria(1,R.drawable.ic_money,"Comida y Bebida","2 Subcat",255,79,55));
        categoriaList.add(new Categoria(2,R.drawable.ic_money,"Transporte","4 Subcat",52,73,94));
        categoriaList.add(new Categoria(3,R.drawable.ic_money,"Vivienda","3 Subcat", 211,84,0));
        categoriaList.add(new Categoria(4,R.drawable.ic_money,"Compras","5 Subcat",155,89,182));
        categoriaList.add(new Categoria(5,R.drawable.ic_money,"Ahorros","1 Subcat",52,152,219));
        categoriaList.add(new Categoria(6,R.drawable.ic_money,"Ingresos","2 Subcat",39,174,96));


        return categoriaList;
    }
}
