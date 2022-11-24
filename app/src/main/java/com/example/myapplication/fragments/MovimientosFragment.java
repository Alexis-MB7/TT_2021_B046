package com.example.myapplication.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class MovimientosFragment extends Fragment {
    ListView listViewMovimientos;
    ListView listViewMovimientos2;
    List<Movimiento> movimientoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        movimientoList = new ArrayList<>();

        movimientoList.add(new Movimiento(1,R.drawable.ic_money,10.50f,"Comida y Bebida","Papitas", 255,79,55));
        movimientoList.add(new Movimiento(2,R.drawable.ic_money,30.00f,"Comida y Bebida","Refreesco", 255,79,55));
        movimientoList.add(new Movimiento(3,R.drawable.ic_money,46.50f,"Comida y Bebida","Quesadillas", 255,79,55));

        return movimientoList;
    }
}
