package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Movimiento;
import com.example.myapplication.PrediccionesAdapter;
import com.example.myapplication.ProyectoAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProyectoFragment extends Fragment {
    List<String> group_items;
    List<Movimiento>  movimientoList;
    Map<String, List<Movimiento> > list_proyecto;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

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
        MenuItem item3 = menu.findItem(R.id.guardar);
        item3.setVisible(false);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_proyecto_nuevo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Proyecto");

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinnerNuevoProy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.periodos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        createGroup();
        createGroupedData();
        expandableListView = getActivity().findViewById(R.id.exp_list_proy_new);
        expandableListAdapter = new ProyectoAdapter(getActivity(),group_items, list_proyecto);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

    }

    private void createGroupedData() {
        String[] abril = {"Categoria 1", "Categoria 2"};
        String[] mayo = {"Categoria 4", "Categoria 5", "Categoria 6"};

        list_proyecto = new HashMap<String, List<Movimiento> >();
        for (String group: group_items){
            if(group.equals("Abril")){
                loadChilds(abril);
            }else if(group.equals("Mayo")) {
                loadChilds(mayo);
            }
            list_proyecto.put(group, movimientoList);
        }
    }


    private void loadChilds(String[] items) {
        movimientoList = new ArrayList<>();
        for(String str:items){
            movimientoList.add(new Movimiento(1,R.drawable.ic_money,10.50f,str,"Ejemplo", 255,79,55));
        }
    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Abril");
        group_items.add("Mayo");
    }
}
