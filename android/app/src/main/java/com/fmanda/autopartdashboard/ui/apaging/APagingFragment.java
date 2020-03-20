package com.fmanda.autopartdashboard.ui.apaging;

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

public class APagingFragment extends Fragment {

    private APagingViewModel mViewModel;

    public static APagingFragment newInstance() {
        return new APagingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(APagingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_apaging, container, false);
        return root;
    }


}
