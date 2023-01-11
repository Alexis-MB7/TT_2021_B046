package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Categoria;
import com.example.myapplication.Movimiento;
import com.example.myapplication.R;
import com.example.myapplication.bd.BD_handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresupuestoZeroAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private List<Categoria> categoriaList;
    private float[] montoList;
    private float total;
    private Map<String, List<Movimiento>> colection;
    private int flag = 0;

    public PresupuestoZeroAdapter(Context context, List<String> groupList, List<Categoria> categoriaList, float[] montoList, float total, Map<String, List<Movimiento>> colection) {
        this.context = context;
        this.groupList = groupList;
        this.colection = colection;
        this.montoList = montoList;
        this.categoriaList = categoriaList;
        this.total = total;
        flag = 1;
    }

    @Override
    public int getGroupCount() {
        return colection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return colection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return colection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String mobileName = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandlist_group_presupuesto, null);
        }
        TextView item = view.findViewById(R.id.group_title);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);

        TextView monto = view.findViewById(R.id.group_monto);
        monto.setTypeface(null, Typeface.BOLD);
        if (flag == 0){
            monto.setText("$" + String.valueOf(montoList[i]));
        }else{
            monto.setText("- $" + String.valueOf(montoList[i]));
            monto.setTextColor(Color.rgb(192,87,70));
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ImageView imageViewCategoria;
        TextView textViewCategoria;
        TextView textViewMonto;
        BD_handler handler = new BD_handler(context);
        ArrayList<Movimiento> lista = handler.readPresupuestoZero();

        Movimiento catMonto  = (Movimiento) getChild(i, i1);
        Categoria cat = catMonto.getCat();

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandlist_child_zero, null);
        }

        imageViewCategoria = view.findViewById(R.id.imageView_mov);
        textViewCategoria = view.findViewById(R.id.child_categoria);
        textViewMonto = view.findViewById(R.id.child_monto);

        imageViewCategoria.setBackgroundColor(Color.rgb(cat.colorR,cat.colorG,cat.colorB));
        imageViewCategoria.setImageResource(cat.image);
        textViewCategoria.setText(cat.nombre);

        NumberFormat df = new DecimalFormat("0.00");
        if(lista.size() != 0){
            for (Movimiento m: lista){
                if (m.getCat().getId() == cat.getId()){
                    textViewMonto.setText("$" + df.format(m.getMonto()));
                }
            }
        }else{
            textViewMonto.setText("$ -");
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void guardarPresupuestoZero(){

    }
}
