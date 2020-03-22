package com.fmanda.autopartdashboard.ui.inventory;

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

public class InventoryFragment extends Fragment {

    private InventoryViewModel mViewModel;

    public static InventoryFragment newInstance() {
        return new InventoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
        return root;
    }



}
