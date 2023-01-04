package com.example.myapplication.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class PresupuestoNuevoFragment extends Fragment {
    private FloatingActionButton fab;
    private TextInputLayout textInputLayout;
    private EditText text;
    private TextView textView;
    public int opt = -1;
    public String[] descs;
    public String[] items = {"50/30/20","Zero Budget"};

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
        return inflater.inflate(R.layout.fragment_presupuesto_nuevo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Presupuesto");

        textView = getActivity().findViewById(R.id.desc_presupuesto);
        descs = getResources().getStringArray(R.array.presuspuestos_descrip);

        textInputLayout = getActivity().findViewById(R.id.textInputLayout_presupuesto_nuevo);
        text = textInputLayout.getEditText();
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(text);
            }
        });

        textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(text);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(text);
            }
        });

        fab = getActivity().findViewById(R.id.fab_presupuesto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opt == 0){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Presupuesto50Fragment()).commit();
                }else if(opt == 1){
                    Toast.makeText(getActivity(), items[1], Toast.LENGTH_SHORT).show();
                }else{
                    textInputLayout.setError("Seleccione algun presupuesto");
                }

            }
        });
    }

    private void showAlertDialog(EditText text) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setSingleChoiceItems(items, opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                opt = i;
                text.setText(items[opt]);
                textView.setText(descs[opt]);
                textInputLayout.setError(null);
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
