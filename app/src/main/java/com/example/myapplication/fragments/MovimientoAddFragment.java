package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Categoria;
import com.example.myapplication.adapters.CategoriaAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Movimiento;
import com.example.myapplication.R;
import com.example.myapplication.view_models.CategoriaViewModel;
import com.example.myapplication.view_models.MovimientoFijoViewModel;
import com.example.myapplication.view_models.MovimientoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovimientoAddFragment extends Fragment {
    ArrayList<Movimiento> movimientoList;
    MovimientoViewModel movs_vm;
    MovimientoFijoViewModel fijo_vm;

    TextInputLayout textInputLayout_name;
    EditText editText_name;
    TextInputLayout textInputLayout_date;
    EditText editText_date;
    TextInputLayout textInputLayout_time;
    EditText editText_time;
    EditText editText_desc;
    LinearLayout cat_container;
    LinearLayout linearLayout;
    TextInputLayout textInputLayout_periodos;
    EditText editText_periodos;
    TextInputLayout textInputLayout_repeticiones;
    EditText editText_repeticiones;
    Switch sw;
    Spinner spinnerPeriodos;

    List<Categoria> categoriaList;
    CategoriaViewModel cats_vm;
    Categoria cat;
    ImageView catImage;
    TextView catText;
    Spinner spinner;
    int cat_flag = 0;

    int hour, minute;
    int año, mes, dia;
    String desc;
    int periodos, repeticiones;


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

        editText_desc = getActivity().findViewById(R.id.nuevoMov_desc);
        textInputLayout_name = getActivity().findViewById(R.id.nuevoMov_monto);
        editText_name = textInputLayout_name.getEditText();

        textInputLayout_date = getActivity().findViewById(R.id.textInputLayout_nuevoMov_fecha);
        editText_date = textInputLayout_date.getEditText();

        textInputLayout_time = getActivity().findViewById(R.id.textInputLayout_nuevoMov_hora);
        editText_time = textInputLayout_time.getEditText();

        spinner = (Spinner) getActivity().findViewById(R.id.spinnerTipoCat);

        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int toast = 0;

                if (TextUtils.isEmpty(editText_name.getText())) {
                    textInputLayout_name.setError("-");
                    toast = 1;
                } else {
                    textInputLayout_name.setError(null);
                }

                if (TextUtils.isEmpty(editText_date.getText())) {
                    textInputLayout_date.setError("-");
                    toast = 1;
                } else {
                    textInputLayout_date.setError(null);
                }

                if (TextUtils.isEmpty(editText_time.getText())) {
                    textInputLayout_time.setError("-");
                    toast = 1;
                } else {
                    textInputLayout_time.setError(null);
                }

                if (cat_flag == 0) {
                    catText.setTextColor(Color.RED);
                    toast = 1;
                } else {
                    catText.setTextColor(Color.BLACK);
                }

                if (TextUtils.isEmpty(editText_desc.getText())) {
                    desc = "";
                } else {
                    desc = String.valueOf(editText_desc.getText());
                }

                if(sw.isChecked()){
                    if (TextUtils.isEmpty(editText_periodos.getText())) {
                        textInputLayout_periodos.setError("-");
                        toast = 1;
                    } else {
                        textInputLayout_time.setError(null);
                    }

                    if (TextUtils.isEmpty(editText_repeticiones.getText())) {
                        textInputLayout_repeticiones.setError("-");
                        toast = 1;
                    } else {
                        textInputLayout_repeticiones.setError(null);
                    }
                }

                if (toast == 1) {
                    Toast.makeText(getActivity(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();
                    toast = 0;
                } else {
                    System.out.println("*************Se guardo*************");
                    Movimiento new_mov;
                    if(spinner.getSelectedItemPosition() == 2){
                        new_mov = new Movimiento(movimientoList.size() + 1, Float.parseFloat(String.valueOf(editText_name.getText())), desc, cat, hour, minute, año, mes, dia);
                    }else{
                        new_mov = new Movimiento(movimientoList.size() + 1, Float.parseFloat(String.valueOf(editText_name.getText()))*-1, desc, cat, hour, minute, año, mes, dia);
                    }
                    new_mov.setTipo(spinner.getSelectedItemPosition());
                    if(sw.isChecked()){
                        int p = Integer.parseInt(String.valueOf(editText_periodos.getText()));
                        switch (spinnerPeriodos.getSelectedItemPosition()){
                            case 0:
                                p = p * 7;
                                break;
                            case 1:
                                p = p * 30;
                                break;
                            case 2:
                                p = p * 365;
                                break;
                        }
                        guardarMovimientoRecurrente(new_mov, p, Integer.parseInt(String.valueOf(editText_repeticiones.getText())));
                    }else{
                        guardarMovimiento(new_mov);
                    }
                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.nav_inicio);
                }

                return false;
            }
        });
    }

    private void guardarMovimiento(Movimiento mov) {
        movs_vm.insertMov(getActivity(), mov);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_main);
        fab.show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();

    }

    private void guardarMovimientoRecurrente(Movimiento mov, int period, int reps){
        fijo_vm.insertFijo(getActivity(), mov,period, reps);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_main);
        fab.show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return inflater.inflate(R.layout.fragment_nuevo_movimiento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cats_vm = new ViewModelProvider(requireActivity()).get(CategoriaViewModel.class);
        categoriaList = cats_vm.getLista_cat().getValue();

        movs_vm = new ViewModelProvider(requireActivity()).get(MovimientoViewModel.class);
        movimientoList = movs_vm.getLista_mov().getValue();

        fijo_vm = new ViewModelProvider(requireActivity()).get(MovimientoFijoViewModel.class);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Nuevo Movimiento");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.showFab();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_inicio);

            }
        });

        spinner = (Spinner) getActivity().findViewById(R.id.spinnerTipoCat);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.tipos_de_movimiento)){
            @Override
            public int getCount() {
                return super.getCount()-2;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinnerPeriodos = getActivity().findViewById(R.id.spinnerMovRecurrente);
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.periodos));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodos.setAdapter(adapter2);


        textInputLayout_time = getActivity().findViewById(R.id.textInputLayout_nuevoMov_hora);
        editText_time = textInputLayout_time.getEditText();
        textInputLayout_time.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(editText_time);
            }
        });
        textInputLayout_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(editText_time);
            }
        });
        editText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(editText_time);
            }
        });

        textInputLayout_date = getActivity().findViewById(R.id.textInputLayout_nuevoMov_fecha);
        editText_date = textInputLayout_date.getEditText();
        textInputLayout_date.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(editText_date);
            }
        });
        textInputLayout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(editText_date);
            }
        });
        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(editText_date);
            }
        });

        cat_container = getActivity().findViewById(R.id.nuevoMov_categoria);
        catImage = getActivity().findViewById(R.id.nuevoMov_imagenCat);
        catText = getActivity().findViewById(R.id.nuevoMov_nombreCat);
        cat_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoriaPicker(categoriaList);

            }
        });

        linearLayout = getActivity().findViewById(R.id.linear);
        textInputLayout_periodos = getActivity().findViewById(R.id.textInputLayout_recu_periodos);
        editText_periodos = getActivity().findViewById(R.id.textInputEdit_recu_periodos);
        linearLayout.setVisibility(View.INVISIBLE);

        textInputLayout_repeticiones = getActivity().findViewById(R.id.textInputLayout_repeticiones);
        editText_repeticiones = getActivity().findViewById(R.id.textInputEdit_repeticiones);
        textInputLayout_repeticiones.setVisibility(View.INVISIBLE);

        sw = getActivity().findViewById(R.id.mov_recurrente);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    linearLayout.setVisibility(View.VISIBLE);
                    textInputLayout_repeticiones.setVisibility(View.VISIBLE);
                    textInputLayout_time.setEnabled(false);
                    editText_time.setText(String.format(Locale.getDefault(), "%02d:%02d", 0, 0));
                } else {
                    // The toggle is disabled
                    linearLayout.setVisibility(View.INVISIBLE);
                    textInputLayout_repeticiones.setVisibility(View.INVISIBLE);
                    textInputLayout_time.setEnabled(true);
                    editText_time.setText("");

                }
            }
        });
    }

    private void showCategoriaPicker(List<Categoria> lista) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle("Seleccione una categoria:");

        builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        CategoriaAdapter adapter = new CategoriaAdapter(getActivity(), lista);
        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cat = lista.get(which);
                cat_flag = 1;
                System.out.println(cat.getNombre());
                catImage.setImageResource(cat.getImage());
                catImage.setBackgroundColor(Color.rgb(cat.getColorR(), cat.getColorG(), cat.getColorB()));
                catText.setText(cat.getNombre());
                switch(cat.getTipo_cat()){
                    case 0:
                        spinner.setSelection(0);
                        spinner.setEnabled(true);
                        break;
                    case 1:
                        spinner.setSelection(2);
                        spinner.setEnabled(false);
                        break;
                    case 2:
                        spinner.setSelection(3);
                        spinner.setEnabled(false);
                        break;
                }


            }
        });
        builderSingle.show();

    }

    private void showTimePicker(EditText text) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                text.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), /*style,*/ onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    private void showDatePicker(EditText text) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                año = year;
                dia = day;
                month = month + 1;
                mes = month;
                String date = day + "/" + month + "/" + year;
                text.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
