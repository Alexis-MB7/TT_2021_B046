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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.adapters.PrediccionesAdapter;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrediccionesFragment extends Fragment {
    List<String> group_items;
    List<String> child_items;
    Map<String, List<String>> list_pred;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    private LineChart lineChart;

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

        createGroup();
        createGroupedData();
        expandableListView = getActivity().findViewById(R.id.exp_list_pred);
        expandableListAdapter = new PrediccionesAdapter(getActivity(),group_items,list_pred);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);

        lineChart = getActivity().findViewById(R.id.lineChartPrediccion);
        setupLineChart();
    }

    private void setupLineChart() {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawBorders(true);

        ArrayList<Entry> yValues1 = new ArrayList<>();
        yValues1.add(new Entry(0,10));
        yValues1.add(new Entry(1,40));
        yValues1.add(new Entry(2,30));
        yValues1.add(new Entry(3,70));
        yValues1.add(new Entry(4,50));
        yValues1.add(new Entry(5,85));

        LineDataSet set1 = new LineDataSet(yValues1,"Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setDrawValues(false);

        ArrayList<Entry> yValues2 = new ArrayList<>();

        yValues2.add(new Entry(0,40));
        yValues2.add(new Entry(1,10));
        yValues2.add(new Entry(2,70));
        yValues2.add(new Entry(3,30));
        yValues2.add(new Entry(4,85));
        yValues2.add(new Entry(5,50));

        LineDataSet set2 = new LineDataSet(yValues2,"Set 1");
        set2.setFillAlpha(110);
        set2.setColor(Color.BLACK);
        set2.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        LineData lineData1  = new LineData(dataSets);
        lineChart.setData(lineData1);


    }

    private void createGroupedData() {
        String[] necesidades = {"Categoria 1", "Categoria 2", "Categoria 3"};
        String[] deseos = {"Categoria 4", "Categoria 5", "Categoria 6"};
        String[] ahorros = {"Categoria 7", "Categoria 8", "Categoria 9"};

        list_pred = new HashMap<String, List<String>>();
        for (String group: group_items){
            if(group.equals("Necesidades")){
                loadChilds(necesidades);
            }else if(group.equals("Deseos")){
                loadChilds(deseos);
            }else if(group.equals("Ahorros")){
                loadChilds(ahorros);
            }
            list_pred.put(group,child_items);
        }
    }

    private void loadChilds(@NonNull String[] items) {
        child_items = new ArrayList<>();
        for(String str:items){
            child_items.add(str);
        }
    }

    private void createGroup() {
        group_items = new ArrayList<>();
        group_items.add("Necesidades");
        group_items.add("Deseos ");
        group_items.add("Ahorros");

    }
}
