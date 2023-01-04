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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.FactorPredictivo;
import com.example.myapplication.Movimiento;
import com.example.myapplication.SortMovimientos;
import com.example.myapplication.adapters.PrediccionesAdapter;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.example.myapplication.view_models.MovimientoFijoViewModel;
import com.example.myapplication.view_models.MovimientoViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrediccionesFragment extends Fragment {
    List<String> group_items;
    Map<String, List<FactorPredictivo>> list_pred;
    ExpandableListView expandableListView;
    PrediccionesAdapter expandableListAdapter;
    private LineChart lineChart;

    CategoriaViewModel cats_vm;
    MovimientoViewModel movs_vm;
    List<Categoria> categoriaList;
    List<Categoria> gastosCats;
    List<Categoria> ingresosCats;
    List<Categoria> ahorrosCats;
    List<Movimiento> movimientoList;
    List<Movimiento> mov_list;

    RadioGroup radioGroup;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;

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
        return inflater.inflate(R.layout.fragment_predicciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Predicciones");

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();

        movs_vm = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        movimientoList = movs_vm.getLista_mov().getValue();
        mov_list = movimientoList;

        radioGroup = (RadioGroup) view.findViewById(R.id.daily_weekly_button_view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                cambiarPeriodo(radioGroup.indexOfChild(view.findViewById(radioGroup.getCheckedRadioButtonId())));
                loadLineChartData(radioGroup.indexOfChild(view.findViewById(radioGroup.getCheckedRadioButtonId())));
            }
        });

        createGroup();
        createGroupedData();
        expandableListView = getActivity().findViewById(R.id.exp_list_pred);
        expandableListAdapter = new PrediccionesAdapter(getActivity(),group_items,list_pred);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

        lineChart = getActivity().findViewById(R.id.lineChartPrediccion);
        setupLineChart();
        loadLineChartData(0);
    }

    private void cambiarPeriodo(int indexOfChild) {
        List<Movimiento> list = new ArrayList<>();
        Calendar actual = Calendar.getInstance();
        actual.getTime();

        switch (indexOfChild){
            case 0:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) + " con " + actual.get(Calendar.WEEK_OF_YEAR));
                    if(movimiento.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) == actual.get(Calendar.WEEK_OF_YEAR)){
                        list.add(movimiento);
                    }
                });
                break;
            case 1:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.MONTH) + " con " + actual.get(Calendar.MONTH));
                    if(movimiento.getFechaCompleta().get(Calendar.MONTH) == actual.get(Calendar.MONTH)){
                        list.add(movimiento);
                    }
                });
                break;
            case 2:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.YEAR) + " con " + actual.get(Calendar.YEAR));
                    if(movimiento.getFechaCompleta().get(Calendar.YEAR) == actual.get(Calendar.YEAR)){
                        list.add(movimiento);
                    }
                });
                break;
        }
        mov_list = list;
        createGroupedData();
        expandableListAdapter.setData(list_pred);
        expandableListAdapter.notifyDataSetChanged();
    }

    private void setupLineChart() {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawBorders(true);
    }

    private void loadLineChartData(int x){
        Calendar actual = Calendar.getInstance();

        for(Movimiento m: movimientoList){
            if(actual.compareTo(m.getFechaCompleta()) >= 0){
            }
        }

        LineDataSet set1 = new LineDataSet(entriesHistogram(movimientoList, x),"Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData lineData1  = new LineData(dataSets);
        lineChart.setData(lineData1);
        lineChart.invalidate();
        lineChart.animateY(1000, Easing.EaseInBack);

    }

    private List<Entry> entriesHistogram(List<Movimiento> lista, int option_periodo){
        float monto[][];
        float temp = 0;
        int sizeHistogram;
        ArrayList<Entry> entries = new ArrayList<>();
        Collections.sort(lista, new SortMovimientos());

        for(Movimiento m: lista){
            System.out.print(m.getFechaCompleta().getTime());
            System.out.println(" -> " + m.getMonto());

        }

        entries.add(new Entry(0,0));

        switch(option_periodo){
            case 0:
                sizeHistogram = lista.get(lista.size()-1).getFechaCompleta().get(Calendar.WEEK_OF_YEAR) - lista.get(0).getFechaCompleta().get(Calendar.WEEK_OF_YEAR) + 1;
                System.out.println(sizeHistogram);
                monto = new float[sizeHistogram][2];

                for(int i = 0; i < sizeHistogram; i++){
                    temp = 0;
                    for(Movimiento m: lista){
                        if(m.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) <= i+1){
                            temp += m.getMonto();
                        }else{
                            break;
                        }
                    }
                    monto[i] = new float[]{(i+1)*7,temp*-1};
                    entries.add(new Entry((i+1)*7,temp*-1));
                }
                System.out.println(Arrays.deepToString(monto));
                break;
            case 1:
                sizeHistogram = lista.get(lista.size()-1).getFechaCompleta().get(Calendar.MONTH) - lista.get(0).getFechaCompleta().get(Calendar.MONTH) + 1;
                System.out.println(sizeHistogram);
                monto = new float[sizeHistogram][2];

                for(int i = 0; i < sizeHistogram; i++){
                    temp = 0;
                    for(Movimiento m: lista){
                        if(m.getFechaCompleta().get(Calendar.MONTH) <= i+1){
                            temp += m.getMonto();
                        }else{
                            break;
                        }
                    }
                    monto[i] = new float[]{(i+1)*7,temp*-1};
                    entries.add(new Entry((i+1)*7,temp*-1));
                }
                System.out.println(Arrays.deepToString(monto));
                break;
            case 2:
                sizeHistogram = lista.get(lista.size()-1).getFechaCompleta().get(Calendar.YEAR) - lista.get(0).getFechaCompleta().get(Calendar.YEAR) + 1;
                System.out.println(sizeHistogram);
                monto = new float[sizeHistogram][2];

                for(int i = 0; i < sizeHistogram; i++){
                    temp = 0;
                    for(Movimiento m: lista){
                        if(lista.get(lista.size()-1).getFechaCompleta().get(Calendar.YEAR) - m.getFechaCompleta().get(Calendar.YEAR) <= i+1){
                            temp += m.getMonto();
                        }else{
                            break;
                        }
                    }
                    monto[i] = new float[]{(i+1)*7,temp*-1};
                    entries.add(new Entry((i+1)*7,temp*-1));
                }
                System.out.println(Arrays.deepToString(monto));
                break;

        }

        return entries;


    }

    private void createGroupedData() {
        ahorrosCats = new ArrayList<>();
        gastosCats = new ArrayList<>();
        ingresosCats = new ArrayList<>();

        categoriaList.forEach(categoria -> {
            switch (categoria.getTipo_cat()){
                case 0: //Gastos
                    gastosCats.add(categoria);
                    break;
                case 1: //Ingresos
                    ingresosCats.add(categoria);
                    break;
                case 2: //Ahorro
                    ahorrosCats.add(categoria);
                    break;
            }
        });

        List<FactorPredictivo> gastos_fp = new ArrayList<>();
        gastosCats.forEach(categoria -> {
            float monto = 0;

            for(Movimiento mov : mov_list){
                if(mov.getCat().getId() == categoria.getId())
                    monto += mov.getMonto();
            }

            FactorPredictivo fp = new FactorPredictivo(categoria, monto);
            gastos_fp.add(fp);
        });

        List<FactorPredictivo> ingresos_fp = new ArrayList<>();
        ingresosCats.forEach(categoria -> {
            float monto = 0;

            for(Movimiento mov : mov_list){
                if(mov.getCat().getId() == categoria.getId())
                    monto += mov.getMonto();
            }

            FactorPredictivo fp = new FactorPredictivo(categoria, monto);
            ingresos_fp.add(fp);
        });

        List<FactorPredictivo> ahorros_fp = new ArrayList<>();
        ahorrosCats.forEach(categoria -> {
            float monto = 0;

            for(Movimiento mov : mov_list){
                if(mov.getCat().getId() == categoria.getId())
                    monto += mov.getMonto();
            }

            FactorPredictivo fp = new FactorPredictivo(categoria, monto);
            ahorros_fp.add(fp);
        });

        list_pred = new HashMap<String, List<FactorPredictivo>>();
        for (String group: group_items){
            if(group.equals("Gastos")){
                list_pred.put(group,gastos_fp);
            }else if(group.equals("Ingresos")){
                list_pred.put(group,ingresos_fp);
            }else if(group.equals("Ahorros")){
                list_pred.put(group,ahorros_fp);
            }
        }
    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Gastos");
        group_items.add("Ingresos");
        group_items.add("Ahorros");

    }
}
