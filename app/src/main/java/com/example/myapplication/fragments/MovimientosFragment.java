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

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MovimientosFragment extends Fragment {
    ListView listViewMovimientos;
    ListView listViewMovimientos2;
    List<Movimiento> movimientoList;

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
        MenuItem item3 = menu.findItem(R.id.guardar);
        item3.setVisible(false);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_movimientos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Movimientos");

        listViewMovimientos = (ListView) view.findViewById(R.id.mov_list_1);
        listViewMovimientos2 = (ListView) view.findViewById(R.id.mov_list_2);

        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fillData());

        listViewMovimientos.setAdapter(adapter);
        listViewMovimientos2.setAdapter(adapter);


    }

    private List<Movimiento> fillData() {
        Categoria cat = new Categoria(1,R.drawable.ic_money,"Comida y Bebida",255,79,55, 0);

        movimientoList = new ArrayList<>();

        movimientoList.add(new Movimiento(1,10.50f,"Papitas", cat));
        movimientoList.add(new Movimiento(1,30.0f,"Refresco", cat));
        movimientoList.add(new Movimiento(1,46.50f,"Quesadillas", cat));

        return movimientoList;
    }
}
