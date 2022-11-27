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

import com.example.myapplication.Movimiento;
import com.example.myapplication.MovimientoAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CuentaFragment extends Fragment {
    ListView listViewMovimientos;
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

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewCuenta);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fillData());
        listViewMovimientos.setAdapter(adapter);
    }

    private List<Movimiento> fillData() {
        movimientoList = new ArrayList<>();

        movimientoList.add(new Movimiento(1,R.drawable.ic_money,10.50f,"Comida y Bebida","Papitas", 255,79,55));
        movimientoList.add(new Movimiento(1,R.drawable.ic_money,30.00f,"Comida y Bebida","Refreesco", 255,79,55));
        movimientoList.add(new Movimiento(1,R.drawable.ic_money,46.50f,"Comida y Bebida","Quesadillas", 255,79,55));
        movimientoList.add(new Movimiento(1,R.drawable.ic_money,10.50f,"Comida y Bebida","Papitas", 255,79,55));
        movimientoList.add(new Movimiento(1,R.drawable.ic_money,30.00f,"Comida y Bebida","Refreesco", 255,79,55));
        movimientoList.add(new Movimiento(1,R.drawable.ic_money,46.50f,"Comida y Bebida","Quesadillas", 255,79,55));

        return movimientoList;
    }
}
