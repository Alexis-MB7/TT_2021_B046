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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.R;
import com.example.myapplication.adapters.MovimientoAdapter;
import com.example.myapplication.view_models.CategoriaViewModel;
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
import java.util.Calendar;
import java.util.List;

public class EstadisticasFragment extends Fragment {
    private PieChart pieChart;
    MovimientoAdapter adapter;
    ListView listViewMovimientos;
    List<Movimiento> movimientoList;
    MovimientoViewModel movs_vm;
    CategoriaViewModel cats_vm;
    ImageButton imageButton;
    ImageButton imageButton2;
    RadioGroup radioGroup;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;

    public int opt = 0;
    public String[] items = {"Gastos por Categoria","Gastos por Tipo"};

    ArrayList<Movimiento> gastoList = new ArrayList<>();
    ArrayList<Movimiento> gastosList = new ArrayList<>();
    ArrayList<Categoria> gastosCats = new ArrayList<>();

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

        r1 = (RadioButton) view.findViewById(R.id.radio0);
        r2 = (RadioButton) view.findViewById(R.id.radio1);
        r3 = (RadioButton) view.findViewById(R.id.radio2);

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);

        movs_vm  = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        listViewMovimientos = (ListView) view.findViewById(R.id.listViewEstadisticas);
        movimientoList = movs_vm.getLista_mov().getValue();

        movimientoList.forEach(movimiento -> {
            if(movimiento.getTipo() != 2){
                gastoList.add(movimiento);
            }
        });

        adapter = new MovimientoAdapter(getActivity(), gastoList);
        listViewMovimientos.setAdapter(adapter);

        pieChart = view.findViewById(R.id.chart_estadisticas);
        setupPieChart();
        loadPieChartData(movimientoList);


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

        radioGroup = (RadioGroup) view.findViewById(R.id.daily_weekly_button_view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int check) {
                cambiarPeriodo(radioGroup.indexOfChild(view.findViewById(radioGroup.getCheckedRadioButtonId())));
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
                loadPieChartData(movimientoList);
                }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
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

    private void loadPieChartData(List<Movimiento> List){
        ArrayList<PieEntry> entries = new ArrayList<>();
        gastosList = new ArrayList<>();
        if(opt == 0){
            List.forEach(movimiento -> {
                if (movimiento.getTipo() != 2) {
                    gastosList.add(movimiento);
                    if (!gastosCats.contains(movimiento.getCat()))
                        gastosCats.add(movimiento.getCat());
                }
            });

            float[] totales = new float[gastosCats.size()];

            for(int c = 0; c < gastosCats.size(); c++) {
                for(int d = 0; d < gastosList.size(); d++) {
                    if(gastosList.get(d).getCat().equals(gastosCats.get(c))){
                        if(gastosList.get(d).getMonto() < 0) {
                            totales[c] = totales[c] + gastosList.get(d).getMonto()*-1;
                        }else{
                            totales[c] = totales[c] + gastosList.get(d).getMonto();
                        }
                    };
                }
                entries.add(new PieEntry(totales[c],gastosCats.get(c).getNombre()));
            }
            pieChart.setCenterText("Total:\n" + sumaTextual(totales));
        }else{
            float[] totales = new float[3];

            for(int d = 0; d < List.size(); d++) {
                switch (List.get(d).getTipo()){
                    case 0:
                        if(List.get(d).getMonto() < 0){
                            totales[0] = totales[0] + List.get(d).getMonto()*-1;
                        }else{
                            totales[0] = totales[0] + List.get(d).getMonto();
                        }
                        break;
                    case 1:
                        if(List.get(d).getMonto() < 0){
                            totales[1] = totales[1] + List.get(d).getMonto()*-1;
                        }else{
                            totales[1] = totales[1] + List.get(d).getMonto();
                        }
                        break;
                    case 3:
                        if(List.get(d).getMonto() < 0){
                            totales[2] = totales[2] + List.get(d).getMonto()*-1;
                        }else{
                            totales[2] = totales[2] + List.get(d).getMonto();
                        }
                        break;
                    default:
                        break;
                    }
                }
            String[] tipos = getResources().getStringArray(R.array.tipos_de_movimiento);
            entries.add(new PieEntry(totales[0],tipos[0]));
            entries.add(new PieEntry(totales[1],tipos[1]));
            entries.add(new PieEntry(totales[2],tipos[3]));
            pieChart.setCenterText("Total:\n" + sumaTextual(totales));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries,"Movimientos");
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

    private String sumaTextual(float[] f) {
        float total = 0;
        String s;

        for (int i = 0; i < f.length; i++){
            total = total + f[i];
        }

        s = "- $" + Float.toString(total);
        return s;
    }

    private void cambiarLista(List<Movimiento> List) {
        adapter.setData(List);
        adapter.notifyDataSetChanged();
        loadPieChartData(List);
    }

    private void cambiarPeriodo(int check) {
        List<Movimiento> mov_list = new ArrayList<>();
        Calendar actual = Calendar.getInstance();

        switch (check){
            case 0:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) + " con " + actual.get(Calendar.WEEK_OF_YEAR));
                    if(movimiento.getFechaCompleta().get(Calendar.WEEK_OF_YEAR) == actual.get(Calendar.WEEK_OF_YEAR)){
                        mov_list.add(movimiento);
                    }
                });
                break;
            case 1:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.MONTH) + " con " + actual.get(Calendar.MONTH));
                    if(movimiento.getFechaCompleta().get(Calendar.MONTH) == actual.get(Calendar.MONTH)){
                        mov_list.add(movimiento);
                    }
                });
                break;
            case 2:
                movimientoList.forEach(movimiento -> {
                    System.out.println("Comparamos: " + movimiento.getFechaCompleta().get(Calendar.YEAR) + " con " + actual.get(Calendar.YEAR));
                    if(movimiento.getFechaCompleta().get(Calendar.YEAR) == actual.get(Calendar.YEAR)){
                        mov_list.add(movimiento);
                    }
                });
                break;
        }
        cambiarLista(mov_list);
    }
}