package com.fmanda.autopartdashboard.ui.cashflow;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmanda.autopartdashboard.R;

public class CashflowFragment extends Fragment {

    private CashflowViewModel mViewModel;

    public static CashflowFragment newInstance() {
        return new CashflowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CashflowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cashflow, container, false);
        return root;
    }
}
