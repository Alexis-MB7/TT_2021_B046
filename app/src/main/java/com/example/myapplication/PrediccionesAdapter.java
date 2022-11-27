package com.example.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PrediccionesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private Map<String, List<String>> colection;

    public PrediccionesAdapter(Context context, List<String> groupList, Map<String, List<String>> colection) {
        this.context = context;
        this.groupList = groupList;
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
            view = inflater.inflate(R.layout.expandlist_group, null);
        }
        TextView item = view.findViewById(R.id.group_item);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String model = getChild(i, i1).toString();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandlist_child, null);
        }
        TextView item = view.findViewById(R.id.child_categoria);
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
        item.setText(model);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
