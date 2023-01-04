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

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.R;
import com.example.myapplication.view_models.MovimientoFijoViewModel;

import java.util.ArrayList;
import java.util.List;

public class CuentaFragment extends Fragment {
    ListView listViewMovimientos;
    MovimientoFijoViewModel fijo_vm;
    List<Movimiento> fijosList;


    @Override
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

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Cuenta");

        fijo_vm = new ViewModelProvider(requireActivity()).get(MovimientoFijoViewModel.class);
        fijosList = fijo_vm.getLista_fijos().getValue();

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewCuenta);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fijosList);
        listViewMovimientos.setAdapter(adapter);
    }
}
