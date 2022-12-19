package com.example.myapplication.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Categoria;

public class OptionsViewModel extends ViewModel {
    private MutableLiveData<String> flag_str = new MutableLiveData<String>();
    private MutableLiveData<Integer> flag_int = new MutableLiveData<Integer>();
    private MutableLiveData<Categoria> flag_cat = new MutableLiveData<Categoria>();

    public void setOptions_String(String options_string){
        flag_str.setValue(options_string);
    }

    public void setOptions_int(int options_int){
        flag_int.setValue(options_int);
    }

    public void setOptions_cat(Categoria options_cat){
        flag_cat.setValue(options_cat);
    }

    public LiveData<String> getOptions_String(){
        return flag_str;
    }

    public LiveData<Integer> getOptions_int(){
        return flag_int;
    }

    public LiveData<Categoria> getOptions_cat(){
        return flag_cat;
    }

}
