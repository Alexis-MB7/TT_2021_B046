package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class InicioFragment extends Fragment {
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = view.findViewById(R.id.chart_inicio);
        setupPieChart();
        loadPieChartData();

    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.GRAY);
        pieChart.setCenterText("Gastos por categoria");
        pieChart.setCenterTextSize(20);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);



    }

    private void loadPieChartData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f,"Comida y Bebida"));
        entries.add(new PieEntry(0.3f,"Transporte"));
        entries.add(new PieEntry(0.15f,"Vivienda"));
        entries.add(new PieEntry(0.18f,"Compras"));
        entries.add(new PieEntry(0.4f,"Ahorros"));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries,"Gastos");
        dataSet.setColors(colors);

        PieData pie_data = new PieData(dataSet);
        pie_data.setDrawValues(true);
        pie_data.setValueFormatter(new PercentFormatter(pieChart));
        pie_data.setValueTextSize(12f);
        pie_data.setValueTextColor(Color.GRAY);

        pieChart.setData(pie_data);
        pieChart.invalidate();

        pieChart.animateY(1000, Easing.EaseInOutQuad);

    }
}
