package com.fmanda.autopartdashboard.ui.inventory;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.InventoryAdapter;
import com.fmanda.autopartdashboard.controller.ControllerInventory;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.model.ModelInventory;
import com.fmanda.autopartdashboard.model.ModelProject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InventoryFragment extends Fragment {

    private InventoryViewModel mViewModel;
    private RecyclerView rvInventory;
    private InventoryAdapter inventoryAdapter;

    ArrayAdapter<String> spProjectAdapter;
    List<ModelProject> projects = new ArrayList<>();
    private LinearLayout lnParam;

    Spinner spProject;
    boolean spProjectinit = true;

    public static InventoryFragment newInstance() {
        return new InventoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (!chekLogin()) {
            return null;
        }
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        lnParam = root.findViewById(R.id.lnParam);
        spProject = root.findViewById(R.id.spProject);
        spProjectAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spProjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjectAdapter);
        reInitProject();

        rvInventory = root.findViewById(R.id.rvInventory);
        inventoryAdapter = new InventoryAdapter(getContext(), mViewModel.inventories);
        rvInventory.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvInventory.setAdapter(inventoryAdapter);

        spProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spProjectinit) {
                    spProjectinit = false;
                    return;
                }
                loadFromRest();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        loadFromRest();
        return root;
    }

    private void reInitProject(){
        spProjectAdapter.clear();

        ControllerProject cp = new ControllerProject(getContext());
        projects.clear();
        projects.add(new ModelProject("0","All Unit"));
        projects.addAll(cp.getProjects());

        for(ModelProject project : projects){
            spProjectAdapter.add(project.getProjectname());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tb_tune){
            showOrHideParam();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrHideParam(){
        ViewGroup.LayoutParams params = lnParam.getLayoutParams();
        if (lnParam.getVisibility() == View.VISIBLE){
            lnParam.setVisibility(View.INVISIBLE);
            params.height = 0;
        }else {
            lnParam.setVisibility(View.VISIBLE);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        lnParam.setLayoutParams(params);
    }

    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                loadInventory();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), "Gagal koneksi ke REST Server", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String msg) {
            }
        });

        cr.DownloadInventory();
    }

    private void loadInventory() {
        try {
            String paramProject = "";
            for (ModelProject project : projects) {
                if (project.getProjectname() == spProject.getSelectedItem().toString()) {
                    paramProject = project.getProjectcode();
                }
            }

            ControllerInventory cr = new ControllerInventory(getContext());

            mViewModel.inventories.clear();
            mViewModel.inventories.addAll(cr.getInventories(paramProject));
            inventoryAdapter.notifyDataSetChanged();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean chekLogin(){
        try {
            final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            ControllerSetting cs = ControllerSetting.getInstance(getContext());
            if (cs.isLogin == false) {
                navController.navigate(R.id.nav_setting);
                return false;
            } else {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
