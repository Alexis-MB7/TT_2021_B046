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
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.adapters.PresupuestoAdapter;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.example.myapplication.view_models.MovimientoViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presupuesto50Fragment extends Fragment {
    ListView listViewMovimientos;
    List<Movimiento> movimientoList;

    List<String> group_items;
    float[] montoList;
    List<Movimiento>  movimientoExpandableList;
    Map<String, List<Movimiento> > expandableList_proyecto;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    CategoriaViewModel cats_vm;
    List<Categoria> categoriaList;
    String[] tipos;

    MovimientoViewModel movs_vm;
    List<Movimiento> necesidadesList;
    List<Movimiento> deseosList;
    List<Movimiento> ahorrosList;


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

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);

        movs_vm = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        movimientoList = movs_vm.getLista_mov().getValue();

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewProyZero);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), movimientoList);
        listViewMovimientos.setAdapter(adapter);

        createGroup();
        createGroupedData();
        expandableListView = getActivity().findViewById(R.id.exp_list_proy_zero);
        expandableListAdapter = new PresupuestoAdapter(getActivity(),group_items, montoList, expandableList_proyecto);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

    }

    private void createGroupedData() {
        necesidadesList = new ArrayList<>();
        deseosList = new ArrayList<>();
        ahorrosList = new ArrayList<>();
        float necesidades = 0, deseos = 0, ahorros = 0;

        movimientoList.forEach(movimiento -> {
            switch (movimiento.getTipo()){
                case 0: //Necesidad
                    necesidadesList.add(movimiento);
                    break;
                case 1: //Deseo
                    deseosList.add(movimiento);
                    break;
                case 3: //Ahorro
                    ahorrosList.add(movimiento);
                    break;

                default:
                    break;

            }
        });


        for(Movimiento mov : necesidadesList)
            necesidades += mov.getMonto();

        for(Movimiento mov : deseosList)
            deseos += mov.getMonto();

        for(Movimiento mov : ahorrosList)
            ahorros += mov.getMonto();

        expandableList_proyecto = new HashMap<String, List<Movimiento> >();
        /*for (String group: group_items){
            if(group.equals("Necesidades")){
                loadChilds(necesidades);
            }else if(group.equals("Deseos")){
                loadChilds(deseos);
            }else if(group.equals("Ahorros")){
                loadChilds(ahorros);
            }
            expandableList_proyecto.put(group,movimientoExpandableList);
        }*/
        for (String group: group_items){
            if(group.equals("Necesidades")){
                expandableList_proyecto.put(group,necesidadesList);
            }else if(group.equals("Deseos")){
                expandableList_proyecto.put(group,deseosList);
            }else if(group.equals("Ahorros")){
                expandableList_proyecto.put(group,ahorrosList);
            }
        }
        montoList = new float[]{necesidades,deseos,ahorros};

    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Necesidades");
        group_items.add("Deseos");
        group_items.add("Ahorros");

    }

}
