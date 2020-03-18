package com.fmanda.autopartdashboard.ui.profitloss;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.ProfitLossAdapter;
import com.fmanda.autopartdashboard.controller.ControllerProfitLoss;
import com.fmanda.autopartdashboard.controller.ControllerRequest;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;

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
        rvProfit.addItemDecoration(new DividerItemDecoration(rvProfit.getContext(), DividerItemDecoration.VERTICAL));

        profitLossAdapter = new ProfitLossAdapter(getContext(), mViewModel.groups, mViewModel.profits);
        rvProfit.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvProfit.setAdapter(profitLossAdapter);
        loadFromRest();

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
}
