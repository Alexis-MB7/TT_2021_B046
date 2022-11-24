package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class CategoriaAdapter extends BaseAdapter {
    Context context;
    List<Categorias> list_cat;

    public CategoriaAdapter(Context context, List<Categorias> list_cat) {
        this.context = context;
        this.list_cat = list_cat;
    }

    @Override
    public int getCount() {
        return list_cat.size();
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
        TextView textViewNombre;
        TextView textViewSubcategorias;
        Random rand = new Random();

        Categorias cat = list_cat.get(i);

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_categorias, null);
        }

            imageViewCategoria = view.findViewById(R.id.imageView_1);
            imageViewCategoria.setBackgroundColor(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
            textViewNombre = view.findViewById(R.id.textViewNombre);
            textViewSubcategorias = view.findViewById(R.id.textViewSubcategorias);

            imageViewCategoria.setImageResource(cat.image);
            textViewNombre.setText(cat.nombre);
            textViewSubcategorias.setText(cat.subcat);

        return view;
    }
}
