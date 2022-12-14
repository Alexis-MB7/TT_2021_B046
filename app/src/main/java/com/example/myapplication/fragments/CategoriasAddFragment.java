package com.example.myapplication.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;

public class CategoriasAddFragment extends Fragment {
    ArrayList<Categoria> categoriaList;
    CategoriaViewModel cats_vm;
    TextInputLayout textInputLayout;
    EditText editText;
    ImageButton imageButton;
    ImageView imageView;
    Spinner spinner;
    int color[] = new int[3];


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

        textInputLayout = getActivity().findViewById(R.id.textInputLayout_cat_nombre);
        editText = textInputLayout.getEditText();


        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(TextUtils.isEmpty(editText.getText())){
                    Toast.makeText(getActivity(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                }else{
                    Categoria new_cat = new Categoria(categoriaList.size()+1,R.drawable.ic_money, editText.getText().toString() , spinner.getSelectedItem().toString(), color[0], color[1],color[2]);
                    guardarCategoria(new_cat);
                }
                return false;
            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_categorias_add, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Nueva Categoria");

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();

        spinner = (Spinner) getActivity().findViewById(R.id.spinnerTipoCat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.tipos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        imageView = getActivity().findViewById(R.id.imagenNewCat);
        imageButton = getActivity().findViewById(R.id.buttonColor);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColorPickerDialog.Builder(getActivity())
                        .attachAlphaSlideBar(false)
                        .attachBrightnessSlideBar(false)
                        .setTitle("Seleccione un color")
                        .setPositiveButton("Aceptar",
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        color[0] = envelope.getArgb()[1];
                                        color[1] = envelope.getArgb()[2];
                                        color[2] = envelope.getArgb()[3];
                                        imageView.setBackgroundColor(Color.rgb(color[0],color[1],color[2]));
                                    }
                                })

                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                        .show();



            }
        });


    }

    public void guardarCategoria(Categoria categoria){

        categoriaList.add(categoria);
        cats_vm.setLista_cat(categoriaList);
        Toast.makeText(getActivity(), "Se guardo la categoria", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriasFragment()).commit();

    }

}
