package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.R;
import com.example.myapplication.view_models.MovimientoViewModel;
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

public class InicioFragment extends Fragment {
    private PieChart pieChart;
    ListView listViewMovimientos;
    List<Movimiento> movimientoList;
    ImageButton imageButton;

    ArrayList<Movimiento> gastosList = new ArrayList<>();
    ArrayList<Categoria> gastosCats = new ArrayList<>();

    List<Movimiento> movimientosList;
    MovimientoViewModel movs_vm;

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
         return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");

        movs_vm = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        movimientosList = movs_vm.getLista_mov().getValue();

        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(),drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        pieChart = view.findViewById(R.id.chart_inicio);
        setupPieChart();
        loadPieChartData();

        listViewMovimientos = (ListView) view.findViewById(R.id.listViewInicio);
        MovimientoAdapter adapter = new MovimientoAdapter(getActivity(), gastosList);
        listViewMovimientos.setAdapter(adapter);

        imageButton = (ImageButton) view.findViewById(R.id.buttonInicio);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MovimientosFragment()).commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_movimientos);
            }
        });
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(20);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

    }

    private void loadPieChartData(){


        movimientosList.forEach(movimiento -> {
            if(movimiento.getCat().getTipo_cat() == 0){
                gastosList.add(movimiento);
                if(!gastosCats.contains(movimiento.getCat()))
                    gastosCats.add(movimiento.getCat());
            }
        });

        ArrayList<PieEntry> entries = new ArrayList<>();
        float[] totales = new float[gastosCats.size()];

        for(int c = 0; c < gastosCats.size(); c++) {
            for(int d = 0; d < gastosList.size(); d++) {
                if(gastosList.get(d).getCat().equals(gastosCats.get(c))){
                    totales[c] = totales[c] + gastosList.get(d).getMonto();
                };
            }
            entries.add(new PieEntry(totales[c],gastosCats.get(c).getNombre()));
        }

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
        pieChart.setCenterText("Total:\n" + sumaTextual(totales));
        pieChart.animateY(1000, Easing.EaseInOutQuad);

    }

    private String sumaTextual(float[] f) {
        float total = 0;
        String s;

        for (int i = 0; i < f.length; i++){
            total = total + f[i];
        }

        s = Float.toString(total);
        return s;
    }
}
