package com.fmanda.autopartdashboard.ui.profitloss;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

        loadFromRest();

        return root;
    }

    private void loadProfits(){
//        List<ModelProfitLoss> profits = new ControllerProfitLoss(getContext()).getProfitLoss("1",1, 2020);
//        for (ModelProfitLoss profitloss : profits){
//            Toast.makeText(getContext(), profitloss.getReportname(), Toast.LENGTH_SHORT).show();
//        }
        ControllerProfitLoss controllerProfitLoss = new ControllerProfitLoss(getContext());
        mViewModel.profits.clear();
        mViewModel.profits.addAll(controllerProfitLoss.getProfitLoss("",1,2020));
        profitLossAdapter = new ProfitLossAdapter(getContext(), ModelProfitLoss.getGroups(mViewModel.profits), mViewModel.profits);
        rvProfit.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvProfit.setAdapter(profitLossAdapter);
//        profitLossAdapter.notifyDataSetChanged();
    }

    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                loadProfits();
            }

            @Override
            public void onError(String msg) {
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String msg) {
//                msg = "Progess " + msg;
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        cr.SyncProfitLoss(1, 2020);
    }
}
