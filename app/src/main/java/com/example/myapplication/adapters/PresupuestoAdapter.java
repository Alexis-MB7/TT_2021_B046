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

import com.example.myapplication.Movimiento;
import com.example.myapplication.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class PresupuestoAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private float[] montoList;
    private float total;
    private Map<String, List<Movimiento> > colection;
    private int flag = 0;

    public PresupuestoAdapter(Context context, List<String> groupList, float[] montoList, Map<String, List<Movimiento> > colection) {
        this.context = context;
        this.groupList = groupList;
        this.colection = colection;
        this.montoList = montoList;
    }

    public PresupuestoAdapter(Context context, List<String> groupList, float[] montoList, float total, Map<String, List<Movimiento> > colection) {
        this.context = context;
        this.groupList = groupList;
        this.colection = colection;
        this.montoList = montoList;
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
            switch (i){
                case 0:
                    monto.setText("$" + String.valueOf(montoList[i]) +" / $" + String.valueOf(total*0.5));
                    if(montoList[i] > total*0.5){
                        monto.setTextColor(Color.rgb(192,87,70));
                    }else if(montoList[i] > total*0.5*0.75){
                        monto.setTextColor(Color.rgb(221,164, 72));
                    }else{
                        monto.setTextColor(Color.rgb(153,209,123));
                    }
                    break;
                case 1:
                    monto.setText("$" + String.valueOf(montoList[i]) +" / $" + String.valueOf(total*0.3));
                    if(montoList[i] > total*0.3){
                        monto.setTextColor(Color.rgb(192,87,70));
                    }else if(montoList[i] > total*0.3*0.75){
                        monto.setTextColor(Color.rgb(221,164, 72));
                    }else{
                        monto.setTextColor(Color.rgb(153,209,123));
                    }
                    break;
                case 2:
                    monto.setText("$" + String.valueOf(montoList[i]) +" / $" + String.valueOf(total*0.2));
                    if(montoList[i] > total*0.2){
                        monto.setTextColor(Color.rgb(192,87,70));
                    }else if(montoList[i] > total*0.2*0.75){
                        monto.setTextColor(Color.rgb(221,164, 72));
                    }else{
                        monto.setTextColor(Color.rgb(153,209,123));
                    }
                    break;
            }
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ImageView imageViewCategoria;
        TextView textViewCategoria;
        TextView textViewDescripcion;
        TextView textViewMonto;

        Movimiento mov  = (Movimiento) getChild(i, i1);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_movimientos, null);
        }

        imageViewCategoria = view.findViewById(R.id.imageView_mov);
        textViewCategoria = view.findViewById(R.id.textViewMovCategoria);
        textViewDescripcion = view.findViewById(R.id.textViewMovDescripcion);
        textViewMonto = view.findViewById(R.id.mov_Monto);
        imageViewCategoria.setBackgroundColor(Color.rgb(mov.cat.colorR,mov.cat.colorG,mov.cat.colorB));

        imageViewCategoria.setImageResource(mov.cat.image);
        textViewCategoria.setText(mov.cat.nombre);
        textViewDescripcion.setText(mov.descripcion);
        NumberFormat df = new DecimalFormat("0.00");
        textViewMonto.setText("$" + df.format(mov.monto));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {

        return false;
    }
}
