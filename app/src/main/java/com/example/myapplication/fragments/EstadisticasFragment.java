package com.example.myapplication.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasFragment extends Fragment {
    private PieChart pieChart;
    ListView listViewMovimientos;
    List<Movimiento> movimientoList;
    ImageButton imageButton;
    ImageButton imageButton2;
    public int opt = 0;
    public String[] items = {"Gastos por Tipo","Gastos por Categoria"};

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
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Estadisticas");

        TextView text = (TextView) getActivity().findViewById(R.id.filtroEstadisticas);
        text.setText(items[opt]);

        pieChart = view.findViewById(R.id.chart_estadisticas);
        setupPieChart();
        loadPieChartData();

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewEstadisticas);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), fillData());
        listViewMovimientos.setAdapter(adapter);

        imageButton = (ImageButton) view.findViewById(R.id.buttonEstadisticas);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovimientosFragment()).commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_movimientos);
            }
        });

        imageButton2 = (ImageButton) view.findViewById(R.id.dropEstadisticas);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(text);

            }
        });

    }

    private void showAlertDialog(TextView text) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setSingleChoiceItems(items, opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                opt = i;
                text.setText(items[opt]);
                dialog.dismiss();
                }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private List<Movimiento> fillData() {
        Categoria cat = new Categoria(1,R.drawable.ic_money,"Comida y Bebida",255,79,55,0);
        Categoria cat2 = new Categoria(3,R.drawable.ic_money,"Vivienda",211,84,0,0);

        movimientoList = new ArrayList<>();

        movimientoList.add(new Movimiento(1,10.50f,"Papitas", cat));
        movimientoList.add(new Movimiento(1,30.0f,"Refresco", cat));
        movimientoList.add(new Movimiento(1,46.50f,"Quesadillas", cat));
        movimientoList.add(new Movimiento(1,10.50f,"Servicios", cat2));
        movimientoList.add(new Movimiento(1,30.0f,"Deposito", cat2));
        movimientoList.add(new Movimiento(1,46.50f,"Renta", cat2));

        return movimientoList;
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Total:\n$548.5");
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
        entries.add(new PieEntry(0.17f,"Ahorros"));

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
        pie_data.setValueTextColor(Color.BLACK);

        pieChart.setData(pie_data);
        pieChart.invalidate();

        pieChart.animateY(1000, Easing.EaseInOutQuad);

    }
}