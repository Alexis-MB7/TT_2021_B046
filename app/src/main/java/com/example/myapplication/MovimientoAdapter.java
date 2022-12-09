package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MovimientoAdapter extends BaseAdapter {
    Context context;
    List<Movimiento> movimientoList;

    public MovimientoAdapter(Context context, List<Movimiento> movimientoList) {
        this.context = context;
        this.movimientoList = movimientoList;
    }

    @Override
    public int getCount() {
        return movimientoList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageViewCategoria;
        TextView textViewCategoria;
        TextView textViewDescripcion;
        TextView textViewMonto;

        Movimiento mov = movimientoList.get(i);

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_movimientos, null);
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
}
