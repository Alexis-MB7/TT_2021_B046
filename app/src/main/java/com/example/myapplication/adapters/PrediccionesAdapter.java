package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.FactorPredictivo;
import com.example.myapplication.R;

import java.util.List;
import java.util.Map;

public class PrediccionesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private Map<String, List<FactorPredictivo>> colection;

    public PrediccionesAdapter(Context context, List<String> groupList, Map<String, List<FactorPredictivo>> colection) {
        this.context = context;
        this.groupList = groupList;
        this.colection = colection;
    }

    public void setData(Map<String, List<FactorPredictivo>> colection){
        this.colection = colection;
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
    public FactorPredictivo getChild(int i, int i1) {

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
            view = inflater.inflate(R.layout.expandlist_group, null);
        }
        TextView item = view.findViewById(R.id.group_item);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);
        return view;
    }

    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandlist_child, null);
        }

        ImageButton imageButton = view.findViewById(R.id.child_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageButton.getTag().equals("init")){
                    imageButton.setImageResource(R.drawable.ic_pred_unchecked);
                    imageButton.setTag("other");

                }else {
                    imageButton.setImageResource(R.drawable.ic_pred_checked);
                    imageButton.setTag("init");

                }
            }
        });


        TextView nombre = view.findViewById(R.id.child_categoria);
        nombre.setText(getChild(i, i1).getCategoria().getNombre());

        TextView monto = view.findViewById(R.id.child_monto);
        monto.setText("$" + String.valueOf(getChild(i, i1).getMonto()));


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
