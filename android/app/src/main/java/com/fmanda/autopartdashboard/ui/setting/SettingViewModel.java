package com.fmanda.autopartdashboard.ui.setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fmanda.autopartdashboard.model.ModelSetting;

public class SettingViewModel extends ViewModel {
    public MutableLiveData<ModelSetting> setting;
    public SettingViewModel() {
        setting = new MutableLiveData<>();
        setting.setValue(new ModelSetting());
    }
}
