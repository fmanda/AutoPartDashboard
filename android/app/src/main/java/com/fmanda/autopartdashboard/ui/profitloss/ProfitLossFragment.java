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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.ProfitLossAdapter;
import com.fmanda.autopartdashboard.controller.ControllerProfitLoss;
import com.fmanda.autopartdashboard.controller.ControllerRequest;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;

import java.util.Calendar;
import java.util.List;

public class ProfitLossFragment extends Fragment {

    private ProfitLossViewModel mViewModel;
    private ProfitLossAdapter profitLossAdapter;
    private RecyclerView rvProfit;

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
        loadFromRest();



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

        Spinner spMonth = root.findViewById(R.id.spMonth);
//        ArrayAdapter<CharSequence> spMonthAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.monts_array, android.R.layout.simple_spinner_item);
//        spMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spMonth.setAdapter(spMonthAdapter);

        Spinner spYear = root.findViewById(R.id.spYear);
//        ArrayAdapter<CharSequence> spYearAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.years_array, android.R.layout.simple_spinner_item);
//        spYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spYear.setAdapter(spYearAdapter);


        return root;
    }

    private void loadProfits(){
        ControllerProfitLoss controllerProfitLoss = new ControllerProfitLoss(getContext());
        mViewModel.profits.clear();
        mViewModel.profits.addAll(controllerProfitLoss.getProfitLoss("",1,2020));
        mViewModel.groups.clear();
        mViewModel.groups.addAll(ModelProfitLoss.getGroups(mViewModel.profits));
        profitLossAdapter.notifyDataSetChanged();
    }

    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
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


        cr.SyncProfitLoss(1, 2020);
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

}
