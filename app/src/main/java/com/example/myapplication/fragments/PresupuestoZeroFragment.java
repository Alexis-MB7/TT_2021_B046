package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.R;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.adapters.PresupuestoZeroAdapter;
import com.example.myapplication.bd.BD_handler;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.example.myapplication.view_models.MovimientoFijoViewModel;
import com.example.myapplication.view_models.MovimientoViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresupuestoZeroFragment extends Fragment {
    CategoriaViewModel cats_vm;
    MovimientoFijoViewModel fijo_vm;
    MovimientoViewModel movs_vm;

    List<Movimiento> movimientoList;
    List<Movimiento> fijosList;
    ListView listViewMovimientos;
    ImageButton showFijos;

    List<String> group_items;
    ExpandableListView expandableListView;
    PresupuestoZeroAdapter expandableListAdapter;
    List<Movimiento> cat_monto_1;
    List<Movimiento> cat_monto_2;
    Map<String, List<Movimiento>> expandableList_zero;
    float[] montoList;
    float total;

    List<Categoria> categoriaList;
    List<Categoria> catGastosList;
    List<Categoria> catAhorrosList;

    List<Movimiento> gastosList;
    List<Movimiento> ahorrosList;

    boolean x = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.agregar);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.aceptar);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.guardar);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.editar);
        item4.setVisible(true);

        item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_presupuesto_zero, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Presupuesto");

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();

        fijo_vm = new ViewModelProvider(requireActivity()).get(MovimientoFijoViewModel.class);
        fijosList = fijo_vm.getLista_fijos().getValue();

        movs_vm = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        movimientoList = movs_vm.getLista_mov().getValue();

        for(Movimiento mov : fijosList){
            total += mov.getMonto();
        }

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewProyZero);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fijosList);
        listViewMovimientos.setAdapter(adapter);

        catGastosList = new ArrayList<>();
        catAhorrosList = new ArrayList<>();

        showFijos = view.findViewById(R.id.fijos_show_button);
        showFijos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x){
                    listViewMovimientos.setVisibility(View.GONE);
                    showFijos.setImageResource(R.drawable.ic_agregar);
                    x = false;
                }else{
                    listViewMovimientos.setVisibility(View.VISIBLE);
                    showFijos.setImageResource(R.drawable.ic_close);
                    x = true;
                }
            }
        });

        createGroup();
        createGroupData();
        expandableListView = getActivity().findViewById(R.id.exp_list_proy_zero);
        expandableListAdapter = new PresupuestoZeroAdapter(getActivity(),group_items, categoriaList, montoList, total, expandableList_zero);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

        TextView resumen = getActivity().findViewById(R.id.resumen);

        float fijos_total = 0;
        for (Movimiento m :fijosList){
            fijos_total += m.getMonto();
        }

        float color_flag = montoList[0] + montoList[1];
        resumen.setText(String.valueOf(fijos_total)  + " = " + String.valueOf(montoList[0]) + " + " + String.valueOf(montoList[1]));
        if(color_flag >= fijos_total){
            resumen.setTextColor(Color.rgb(192,87,70));
        }else if(color_flag >= fijos_total*.75){
            resumen.setTextColor(Color.rgb(221,164, 72));
        }else{
            resumen.setTextColor(Color.rgb(153,209,123));
        }





    }

    private void createGroupData() {
        gastosList = new ArrayList<>();
        ahorrosList = new ArrayList<>();
        float gastos = 0, ahorros = 0;

        categoriaList.forEach(categoria -> {
            switch(categoria.getTipo_cat()){
                case 0:
                    catGastosList.add(categoria);
                    break;
                case 2:
                    catAhorrosList.add(categoria);
                    break;

            }
        });

        expandableList_zero = new HashMap<String, List<Movimiento>>();
        cat_monto_1 = new ArrayList<>();

        for(Categoria cat: catGastosList){
            for(Movimiento mov: movimientoList){
                if(cat.getId() == mov.getCat().getId()){
                    gastos += mov.getMonto();
                }
            }
            cat_monto_1.add(new Movimiento(0,gastos,"",cat));
        }

        expandableList_zero.put("Gastos", cat_monto_1);
        cat_monto_2 = new ArrayList<>();

        for(Categoria cat: catAhorrosList){
            for(Movimiento mov: movimientoList){
                if(cat.getId() == mov.getCat().getId()){
                    ahorros += mov.getMonto();
                }
            }
            cat_monto_2.add(new Movimiento(0,ahorros,"",cat));
        }

        expandableList_zero.put("Ahorros", cat_monto_2);
        montoList = new float[]{gastos * -1, ahorros * -1};
    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Gastos");
        group_items.add("Ahorros");

    }
}
