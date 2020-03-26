package com.fmanda.autopartdashboard.ui.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerRequest;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.helper.GsonRequest;
import com.fmanda.autopartdashboard.model.ModelSetting;
import com.fmanda.autopartdashboard.model.ModelUsers;
import com.google.android.material.snackbar.Snackbar;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private TextView txtURL;
    private TextView txtUserName;
    private TextView txtPassword;
    ControllerSetting cs;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        txtURL = root.findViewById(R.id.txtURL);
        txtUserName = root.findViewById(R.id.txtUserName);
        txtPassword = root.findViewById(R.id.txtPassword);
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

        final Button btnReset = root.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = DBHelper.getInstance(getContext());
                dbHelper.resetDatabase(dbHelper.getWritableDatabase());
                Toast.makeText(getContext(), "Database Reset OK", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void loadCurrent(){
        try {
            mViewModel.setting.setValue(cs.getSetting("rest_url"));
            txtUserName.setText(cs.getSettingStr("last_login"));
            if (txtUserName.getText().toString().equals("")){
                txtUserName.requestFocus();
            }else if (txtPassword.getText().toString().equals("")){
                txtPassword.requestFocus();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveToDB(){
        if (txtURL.getText().toString().equals("")){
            Snackbar.make(getView(), "URL wajib diisi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        if (txtUserName.getText().toString().equals("")){
            Snackbar.make(getView(), "User wajib diisi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        if (txtPassword.getText().toString().equals("")){
            Snackbar.make(getView(), "Password wajib diisi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        DBHelper db = DBHelper.getInstance(getContext());
        final ControllerSetting cs = ControllerSetting.getInstance(getContext());
        cs.updateSetting("rest_url",txtURL.getText().toString());
//        Toast.makeText(getContext(), "Rest URL Updated", Toast.LENGTH_SHORT).show();


        ControllerRest cr = new ControllerRest(getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                doLogin(txtUserName.getText().toString(), txtPassword.getText().toString());
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String msg) {

            }
        });
        cr.DownloadProject(Boolean.TRUE);
    }


    public void doLogin(final String username, final String password){
        try {
            final ControllerRequest controllerRequest = ControllerRequest.getInstance(getContext());
            final ControllerSetting cs = ControllerSetting.getInstance(getContext());
            final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            String url = "http://" + cs.getSettingStr("rest_url") + "/user/" + String.valueOf(username);
            GsonRequest<ModelUsers> gsonReq = new GsonRequest<>(url, ModelUsers.class,
                    new Response.Listener<ModelUsers>() {
                        @Override
                        public void onResponse(ModelUsers response) {
                            if (response != null) {
                                if (password.toLowerCase().equals(response.getPassword().toLowerCase())) {
                                    cs.updateSetting("last_login", username);
                                    cs.isLogin = true;
                                    navController.navigate(R.id.nav_home);
                                } else {
                                    Snackbar.make(getView(), "Password Anda salah", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            } else {
                                Snackbar.make(getView(), "User tidak ditemukan", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            controllerRequest.addToRequestQueue(gsonReq, url);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
