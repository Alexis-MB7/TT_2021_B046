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

import com.example.myapplication.CategoriaAdapter;
import com.example.myapplication.Categorias;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends Fragment {
    ListView listViewCategorias;
    List<Categorias> categoriasList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
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

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categorias");

        listViewCategorias = (ListView) view.findViewById(R.id.listViewCategorias);
        CategoriaAdapter adapter = new CategoriaAdapter(getActivity(), fillData());
        listViewCategorias.setAdapter(adapter);

    }

    private List<Categorias> fillData() {
        categoriasList = new ArrayList<>();

        categoriasList.add(new Categorias(1,R.drawable.ic_money,"Comida y Bebida","2 Subcat"));
        categoriasList.add(new Categorias(2,R.drawable.ic_money,"Transporte","4 Subcat"));
        categoriasList.add(new Categorias(3,R.drawable.ic_money,"Vivienda","3 Subcat"));
        categoriasList.add(new Categorias(4,R.drawable.ic_money,"Compras","5 Subcat"));
        categoriasList.add(new Categorias(5,R.drawable.ic_money,"Ahorros","1 Subcat"));
        categoriasList.add(new Categorias(6,R.drawable.ic_money,"Ingresos","2 Subcat"));

        return categoriasList;
    }
}
