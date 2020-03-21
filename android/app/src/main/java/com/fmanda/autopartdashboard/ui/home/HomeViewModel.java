package com.fmanda.autopartdashboard.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fmanda.autopartdashboard.controller.ControllerSetting;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Home Dashboard belum didesain, silahkan cek menu lain");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String aText){
        mText.setValue(aText);
    }
}