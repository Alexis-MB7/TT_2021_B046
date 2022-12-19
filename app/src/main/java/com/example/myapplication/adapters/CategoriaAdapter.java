package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Categoria;
import com.example.myapplication.R;

import java.util.List;
import java.util.Random;

public class CategoriaAdapter extends BaseAdapter {
    Context context;
    List<Categoria> list_cat;

    public CategoriaAdapter(Context context, List<Categoria> list_cat) {
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

        Categoria cat = list_cat.get(i);

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_categorias, null);
        }
            String[] tipos = view.getResources().getStringArray(R.array.tipos);
            imageViewCategoria = view.findViewById(R.id.imageView_1);
            textViewNombre = view.findViewById(R.id.textViewNombre);
            textViewSubcategorias = view.findViewById(R.id.textViewSubcategorias);

            imageViewCategoria.setBackgroundColor(Color.rgb(cat.colorR,cat.colorG,cat.colorB));
            imageViewCategoria.setImageResource(cat.image);
            textViewNombre.setText(cat.nombre);
            textViewSubcategorias.setText(tipos[cat.tipo_cat]);

        return view;
    }
}
