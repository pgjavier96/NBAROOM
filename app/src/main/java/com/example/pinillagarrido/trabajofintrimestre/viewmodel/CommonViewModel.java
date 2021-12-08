package com.example.pinillagarrido.trabajofintrimestre.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.pinillagarrido.trabajofintrimestre.model.respository.PlayerRepository;

public class CommonViewModel extends ViewModel {

    private Context context;
    private PlayerRepository repository;

    public CommonViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }
}