package com.fmanda.autopartdashboard.ui.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelSetting;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private TextView txtURL;
    ControllerSetting cs;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        txtURL = root.findViewById(R.id.txtURL);
        cs = new ControllerSetting(getContext());

        mViewModel.setting.observe(getViewLifecycleOwner(), new Observer<ModelSetting>() {
            @Override
            public void onChanged(ModelSetting modelSetting) {
                txtURL.setText(modelSetting.getVarvalue());
            }
        });

        loadCurrent();

        final Button btnUpdate = root.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              saveToDB();
            }
        });

        return root;
    }

    private void loadCurrent(){
        mViewModel.setting.setValue(cs.getSetting("rest_url"));
    }

    private void saveToDB(){
        DBHelper db = DBHelper.getInstance(getContext());
        cs.updateSetting("rest_url",txtURL.getText().toString());
        Toast.makeText(getContext(), "Rest URL Updated", Toast.LENGTH_SHORT).show();
    }

}