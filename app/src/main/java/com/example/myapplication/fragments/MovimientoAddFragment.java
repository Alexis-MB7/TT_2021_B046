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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.myapplication.view_models.MovimientoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovimientoAddFragment extends Fragment {
    ArrayList<Movimiento> movimientoList;
    MovimientoViewModel movs_vm;

    TextInputLayout textInputLayout_name;
    EditText editText_name;
    TextInputLayout textInputLayout_date;
    EditText editText_date;
    TextInputLayout textInputLayout_time;
    EditText editText_time;
    EditText editText_desc;
    LinearLayout cat_container;

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

                if (toast == 1) {
                    Toast.makeText(getActivity(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();
                    toast = 0;
                } else {
                    System.out.println("*************Se guardo*************");
                    Movimiento new_mov = new Movimiento(movimientoList.size() + 1, Float.parseFloat(String.valueOf(editText_name.getText())), desc, cat, hour, minute, año, mes, dia);
                    guardarMovimiento(new_mov);
                }

                return false;
            }
        });
    }

    private void guardarMovimiento(Movimiento mov) {

        movimientoList.add(mov);
        movs_vm.setLista_mov(movimientoList);
        Toast.makeText(getActivity(), "Se guardo el movimiento", Toast.LENGTH_SHORT).show();
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

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Nuevo Movimiento");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.showFab();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();

            }
        });

        spinner = (Spinner) getActivity().findViewById(R.id.spinnerTipoCat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.tipos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
    }

    private void showCategoriaPicker(List<Categoria> lista) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle("Seleccione una categoria:");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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
