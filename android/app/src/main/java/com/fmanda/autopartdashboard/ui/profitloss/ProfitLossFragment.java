package com.fmanda.autopartdashboard.ui.profitloss;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;

public class ProfitLossFragment extends Fragment {

    private ProfitLossViewModel mViewModel;

    public static ProfitLossFragment newInstance() {
        return new ProfitLossFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profitloss, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfitLossViewModel.class);
        // TODO: Use the ViewModel

        Toast.makeText(getContext(), "Profit Loss", Toast.LENGTH_SHORT).show();
    }

}
