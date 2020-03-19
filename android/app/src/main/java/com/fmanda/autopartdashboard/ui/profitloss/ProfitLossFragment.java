package com.fmanda.autopartdashboard.ui.profitloss;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.ProfitLossAdapter;
import com.fmanda.autopartdashboard.controller.ControllerProfitLoss;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRequest;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;
import com.fmanda.autopartdashboard.model.ModelProject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfitLossFragment extends Fragment {

    private ProfitLossViewModel mViewModel;
    private ProfitLossAdapter profitLossAdapter;
    private RecyclerView rvProfit;
    ArrayAdapter<String> spProjectAdapter;
    Spinner spProject;
    Spinner spMonth;
    Spinner spYear;
    boolean spProjectinit = true;
    boolean spMonthinit = true;
    boolean spYearinit = true;
    List<ModelProject> projects = new ArrayList<>();

    public static ProfitLossFragment newInstance() {
        return new ProfitLossFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProfitLossViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profitloss, container, false);
        rvProfit = root.findViewById(R.id.rvProfit);
//        rvProfit.addItemDecoration(new DividerItemDecoration(rvProfit.getContext(), DividerItemDecoration.VERTICAL));

        profitLossAdapter = new ProfitLossAdapter(getContext(), mViewModel.groups, mViewModel.profits);
        rvProfit.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvProfit.setAdapter(profitLossAdapter);

        final Button btnExpand = root.findViewById(R.id.btnExpand);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profitLossAdapter.isExpand = !profitLossAdapter.isExpand;
                profitLossAdapter.notifyDataSetChanged();
                if (profitLossAdapter.isExpand) {
                    btnExpand.setText("+Show Details");
                }else{
                    btnExpand.setText("-Hide Details");
                }

//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show( getParentFragmentManager(), "datePicker");

            }
        });

        spMonth = root.findViewById(R.id.spMonth);
        spYear = root.findViewById(R.id.spYear);
        spProject = root.findViewById(R.id.spProject);
        spProjectAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spProjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjectAdapter);
        reInitProject();

        Calendar c = Calendar.getInstance();
        spMonth.setSelection(c.get(Calendar.MONTH));

        for (int i = 0; i < spYear.getAdapter().getCount(); i++){
            if (Integer.parseInt(spYear.getAdapter().getItem(i).toString()) == c.get(Calendar.YEAR)){
                spYear.setSelection(i);
                break;
            }
        }

        //set event after initiate, update :useless.. so we use initialSpinner = false;
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                loadProfits();
                if (spMonthinit) {
                    spMonthinit = false;
                    return;
                }
                loadFromRest();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                loadProfits();
                if (spYearinit) {
                    spYearinit = false;
                    return;
                }
                loadFromRest();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Button btnLoad = root.findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromRest();
            }
        });

        loadFromRest();
        return root;
    }

    private void loadProfits(){
        String paramProject = "";
        for (ModelProject project : projects){
            if (project.getProjectname() == spProject.getSelectedItem().toString()){
                paramProject = project.getProjectcode();
            }
        }

        ControllerProfitLoss controllerProfitLoss = new ControllerProfitLoss(getContext());
        mViewModel.profits.clear();
        mViewModel.profits.addAll(controllerProfitLoss.getProfitLoss(
                paramProject,spMonth.getSelectedItemPosition()+1, Integer.parseInt(spYear.getSelectedItem().toString()))
        );
        mViewModel.groups.clear();
        mViewModel.groups.addAll(ModelProfitLoss.getGroups(mViewModel.profits));
        profitLossAdapter.notifyDataSetChanged();
    }

    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
//                reInitProject();
                loadProfits();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), "Gagal koneksi ke REST Server", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();            }

            @Override
            public void onProgress(String msg) {
            }
        });
        cr.DownloadProfitLoss(Boolean.TRUE,spMonth.getSelectedItemPosition()+1, Integer.parseInt(spYear.getSelectedItem().toString()));
//        cr.SyncProfitLoss(spMonth.getSelectedItemPosition()+1, Integer.parseInt(spYear.getSelectedItem().toString()));
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
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

}
