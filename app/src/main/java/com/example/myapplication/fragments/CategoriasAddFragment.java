package com.example.myapplication.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoriasAddFragment extends Fragment {
    MenuItem item3;
    ArrayList<Categoria> categoriaList;
    CategoriaViewModel cats_vm;
    TextInputLayout textInputLayout;
    EditText text;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.agregar);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.aceptar);
        item2.setVisible(false);
        item3 = menu.findItem(R.id.guardar);

        textInputLayout = getActivity().findViewById(R.id.textInputLayout_cat_nombre);
        text = textInputLayout.getEditText();


        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(TextUtils.isEmpty(text.getText())){
                    Toast.makeText(getActivity(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                }else{
                    Categoria new_cat = new Categoria(categoriaList.size()+1,R.drawable.ic_money, text.getText().toString() , null, 100, 100,100);
                    guardarCategoria(new_cat);

                }


                return false;
            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_categorias_add, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Nueva Categoria");

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();


    }

    public void guardarCategoria(Categoria categoria){
        System.out.println("Se guardoooo");

        categoriaList.add(categoria);
        cats_vm.setLista_cat(categoriaList);
        Toast.makeText(getActivity(), "Se guardo la categoria", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();

    }

}
