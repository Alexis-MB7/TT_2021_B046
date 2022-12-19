package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.adapters.PresupuestoAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presupuesto50Fragment extends Fragment {
    ListView listViewMovimientos;
    List<Movimiento> movimientoList;

    List<String> group_items;
    int[] montoList;
    List<Movimiento>  movimientoExpandableList;
    Map<String, List<Movimiento> > expandableList_proyecto;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Categoria cat = new Categoria(1,R.drawable.ic_money,"Comida y Bebida",255,79,55,0);

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
        return inflater.inflate(R.layout.fragment_presupuesto_50, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Presupuesto");

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewProyZero);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fillData());
        listViewMovimientos.setAdapter(adapter);

        createGroup();
        createGroupedData();
        expandableListView = getActivity().findViewById(R.id.exp_list_proy_zero);
        expandableListAdapter = new PresupuestoAdapter(getActivity(),group_items, montoList, expandableList_proyecto);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

    }

    private void createGroupedData() {
        String[] necesidades = {"Categoria 1", "Categoria 2", "Categoria 3"};
        String[] deseos = {"Categoria 4", "Categoria 5", "Categoria 6"};
        String[] ahorros = {"Categoria 7", "Categoria 8", "Categoria 9"};

        expandableList_proyecto = new HashMap<String, List<Movimiento> >();
        for (String group: group_items){
            if(group.equals("Necesidades")){
                loadChilds(necesidades);
            }else if(group.equals("Deseos")){
                loadChilds(deseos);
            }else if(group.equals("Ahorros")){
                loadChilds(ahorros);
            }
            expandableList_proyecto.put(group,movimientoExpandableList);
        }
    }

    private void loadChilds(String[] items) {
        movimientoExpandableList = new ArrayList<>();
        for(String str:items){
            movimientoExpandableList.add(new Movimiento(1,10.50f,str,cat));
        }
    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Necesidades");
        group_items.add("Deseos ");
        group_items.add("Ahorros");

        montoList = new int[]{150, 200, 450};

    }

    private List<Movimiento> fillData() {
        movimientoList = new ArrayList<>();

        movimientoList.add(new Movimiento(1,10.50f,"Papitas", cat));
        movimientoList.add(new Movimiento(1,30.0f,"Refresco", cat));
        movimientoList.add(new Movimiento(1,46.50f,"Quesadillas", cat));

        return movimientoList;
    }

}
