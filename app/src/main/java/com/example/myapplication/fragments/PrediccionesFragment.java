package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.myapplication.view_models.MovimientoViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
        lineChart.setScaleEnabled(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawBorders(true);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setHighlightPerDragEnabled(false);

    }

    private void loadLineChartData(int x){
        Calendar actual = Calendar.getInstance();
        float xInterval = 0f;

        switch (x){
            case 0:
                xInterval = 7f;
                break;
            case 1:
                xInterval = 30f;
                break;
            case 2:
                xInterval = 365f;
                break;
        }

        for(Movimiento m: movimientoList){
            if(actual.compareTo(m.getFechaCompleta()) >= 0){
            }
        }

        LineDataSet datos = new LineDataSet(entriesHistogram(movimientoList, x),"Gastos");
        datos.setFillAlpha(110);
        datos.setColor(Color.rgb(98, 0, 238));
        datos.setDrawValues(true);
        datos.setCircleColor(Color.rgb(98, 0, 238));
        datos.setCircleHoleColor(Color.rgb(98, 0, 238));
        datos.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String str;
                if(value < 0){
                    str = "  -$" + String.valueOf(value);
                }else{
                    str = "  $" + String.valueOf(value);
                }
                return str;
            }
        });
        datos.setLineWidth(2);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setSpaceMax(2.5f);
        xAxis.setSpaceMin(1f);
        xAxis.setDrawGridLines(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(datos);

        LineData lineData1  = new LineData(dataSets);
        lineChart.setData(lineData1);
        lineChart.invalidate();
        lineChart.animateY(1000, Easing.EaseInBack);
        lineChart.highlightValue(5f,lineChart.getLineData().getEntryCount());

        float xMin = (float)Math.floor((datos.getXMin()-xInterval)/xInterval)*xInterval;
        float xMax = (float)Math.ceil((datos.getXMax()+xInterval)/xInterval)*xInterval;

        xAxis.setAxisMinimum(xMin);
        xAxis.setAxisMaximum(xMax);


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
                sizeHistogram = ((lista.get(lista.size()-1).getFechaCompleta().get(Calendar.YEAR)*52) + lista.get(lista.size()-1).getFechaCompleta().get(Calendar.WEEK_OF_YEAR)) - ((lista.get(0).getFechaCompleta().get(Calendar.YEAR)*52) + lista.get(0).getFechaCompleta().get(Calendar.WEEK_OF_YEAR)) + 1;
                System.out.println(sizeHistogram);
                monto = new float[sizeHistogram][2];

                int j = 0;
                for(int i = 0; i < sizeHistogram; i++){
                    temp = 0;
                    int fecha_comp =  lista.get(j).getFechaCompleta().get(Calendar.WEEK_OF_YEAR) + (lista.get(j).getFechaCompleta().get(Calendar.YEAR)*52);
                    for(Movimiento m: lista){
                        int fecha = m.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) + (m.getFechaCompleta().get(Calendar.YEAR)*52);

                        if(fecha <= fecha_comp){
                            temp += m.getMonto();
                        }else{
                            j++;
                            break;
                        }
                    }
                    monto[i] = new float[]{(i+1)*7,temp};
                    entries.add(new Entry((i+1)*7,temp));
                }
                System.out.println(Arrays.deepToString(monto));
                break;
            case 1:
                sizeHistogram = ((lista.get(lista.size()-1).getFechaCompleta().get(Calendar.YEAR)*12) + lista.get(lista.size()-1).getFechaCompleta().get(Calendar.MONTH) + 1) - ((lista.get(0).getFechaCompleta().get(Calendar.YEAR)*12) + lista.get(0).getFechaCompleta().get(Calendar.MONTH)+1) + 1;
                System.out.println(sizeHistogram);
                monto = new float[sizeHistogram][2];

                j = 0;
                for(int i = 0; i < sizeHistogram; i++){
                    temp = 0;
                    int fecha_comp =  (lista.get(j).getFechaCompleta().get(Calendar.MONTH) + 1) + (lista.get(j).getFechaCompleta().get(Calendar.YEAR)*12);
                    for(Movimiento m: lista){
                        int fecha = (m.getFechaCompleta().get(Calendar.MONTH)+1) + (m.getFechaCompleta().get(Calendar.YEAR)*12);

                        if(fecha <= fecha_comp){
                            temp += m.getMonto();
                        }else{
                            j++;
                            break;
                        }
                    }
                    monto[i] = new float[]{(i+1)*30,temp};
                    entries.add(new Entry((i+1)*30,temp));
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
                    monto[i] = new float[]{(i+1)*365,temp};
                    entries.add(new Entry((i+1)*365,temp));
                }
                System.out.println(Arrays.deepToString(monto));
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + option_periodo);
        }
        entries.add(nuevaPrediccion(monto));
        return entries;


    }

    private Entry nuevaPrediccion(float[][] monto){
        Entry entry;
        float pred_y = 0;
        float pred_x;
        double f1;
        double f2 = 0;
        double f3;

        if(monto.length == 1){
            entry = new Entry(monto[0][0],monto[0][1]);
            return entry;
        }
        pred_x = monto[monto.length-1][0] + (monto[monto.length-1][0] - monto[monto.length-2][0]);

        int n_mayus = monto.length;
        for(int n = 1; n < n_mayus; n++){
            f1 = n/((Math.pow(n_mayus,2) + n_mayus)/2);
            if(monto[n][1] == monto[n-1][1]){
                //Nothing;
            }else{
                f2 = (monto[n][1] - monto[n-1][1])/(Math.abs(monto[n][1] - monto[n-1][1]));
            }

            f3 = Math.sqrt(Math.pow(monto[n][1] - monto[n-1][1],2) + Math.pow(monto[n][0] - monto[n-1][0],2));
            pred_y = (float) (pred_y + (f1 * f2 * f3));
        }
        pred_y = pred_y + monto[monto.length-1][1];
        pred_y = Math.round(pred_y*100);
        pred_y = pred_y/100;
        entry = new Entry(pred_x,pred_y);

        return entry;
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
