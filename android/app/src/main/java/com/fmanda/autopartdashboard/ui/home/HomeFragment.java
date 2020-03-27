package com.fmanda.autopartdashboard.ui.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
//import androidx.lifecycle.ViewModelProviders;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.BaseModel;
import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.fmanda.autopartdashboard.model.ModelARAging;
import com.fmanda.autopartdashboard.model.ModelCashFlow;
import com.fmanda.autopartdashboard.model.ModelCurrentCashFlow;
import com.fmanda.autopartdashboard.model.ModelCurrentSales;
import com.fmanda.autopartdashboard.model.ModelInventory;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSetting;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView txtNSMTD;
    TextView txtNSYTD;
    TextView txtGPMTD;
    TextView txtGPYTD;
    TextView txtPGPMTD;
    TextView txtPGPYTD;
    PieChart chartMTD;
    PieChart chartYTD;
    TextView txtInventory;
    TextView txtAP;
    TextView txtAR;
    TextView txtIncome;
    TextView txtExpense;
    List<ModelProject> projects = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (!chekLogin()) {
            return null;
        }

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            txtNSMTD = root.findViewById(R.id.txtNSMTD);
            txtNSYTD = root.findViewById(R.id.txtNSYTD);
            txtGPMTD = root.findViewById(R.id.txtGPMTD);
            txtGPYTD = root.findViewById(R.id.txtGPYTD);
            txtPGPMTD = root.findViewById(R.id.txtPGPMTD);
            txtPGPYTD = root.findViewById(R.id.txtPGPYTD);
            chartMTD = root.findViewById(R.id.chartMTD);
            chartYTD = root.findViewById(R.id.chartYTD);
            txtInventory = root.findViewById(R.id.txtInventory);
            txtAP = root.findViewById(R.id.txtAP);
            txtAR = root.findViewById(R.id.txtAR);
            txtIncome = root.findViewById(R.id.txtIncome);
            txtExpense = root.findViewById(R.id.txtExpense);
            projects = new ControllerProject(getContext()).getProjects();
            loadSales();
            loadAP();
            loadInventory();
            loadAR();
            loadCashFlow();

        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }


    private void loadSales() {
        try {
            ControllerRest cr = new ControllerRest(this.getContext());
            cr.setObjectListener(new ControllerRest.ObjectListener() {
                @Override
                public void onSuccess(BaseModel[] obj) {
                    processSales(obj);
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
            cr.DownloadCurrentSales();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadCashFlow() {
        try {
            ControllerRest cr = new ControllerRest(this.getContext());
            cr.setObjectListener(new ControllerRest.ObjectListener() {
                @Override
                public void onSuccess(BaseModel[] obj) {
                    ModelCurrentCashFlow[] currentCashFlows = (ModelCurrentCashFlow[]) obj;
                    double income = 0;
                    double expense = 0;
                    for (ModelCurrentCashFlow cashFlow : currentCashFlows){
                        income += cashFlow.income;
                        expense += cashFlow.expense;
                    }
                    txtIncome.setText(CurrencyHelper.format(income));
                    txtExpense.setText(CurrencyHelper.format(expense));
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
            cr.DownloadCurrentCashFlow();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void loadInventory() {
        try {
            ControllerRest cr = new ControllerRest(this.getContext());
            cr.setObjectListener(new ControllerRest.ObjectListener() {
                @Override
                public void onSuccess(BaseModel[] obj) {
                    try{
                        ModelInventory[] inventories = (ModelInventory[]) obj;
                        double total = 0;
                        for (ModelInventory inventory : inventories){
                            total += inventory.getAmount();
                        }
                        txtInventory.setText(CurrencyHelper.format(total));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
            cr.DownloadCurrentInventory();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void loadAP() {
        try {
            ControllerRest cr = new ControllerRest(this.getContext());
            cr.setObjectListener(new ControllerRest.ObjectListener() {
                @Override
                public void onSuccess(BaseModel[] obj) {
                    ModelAPAging[] agings = (ModelAPAging[]) obj;
                    double total = 0;
                    for (ModelAPAging aging : agings){
                        total += aging.getTotal();
                    }
                    txtAP.setText(CurrencyHelper.format(total));
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
            cr.DownloadCurrentAPAging();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadAR() {
        try {
            ControllerRest cr = new ControllerRest(this.getContext());
            cr.setObjectListener(new ControllerRest.ObjectListener() {
                @Override
                public void onSuccess(BaseModel[] obj) {
                    ModelARAging[] agings = (ModelARAging[]) obj;
                    double total = 0;
                    for (ModelARAging aging : agings){
                        total += aging.getTotal();
                    }
                    txtAR.setText(CurrencyHelper.format(total));
                }
                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
            cr.DownloadCurrentARAging();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void processSales(BaseModel[] response){
        try {
            ModelCurrentSales[] listCurrentSales = (ModelCurrentSales[]) response;
            double netsales_mtd = 0;
            double netsales_ytd = 0;
            double grossprofit_mtd = 0;
            double grossprofit_ytd = 0;
            double pgrossprofit_mtd = 0;
            double pgrossprofit_ytd = 0;

            for (ModelCurrentSales currentSales : listCurrentSales){
                netsales_mtd += currentSales.netsales_mtd;
                netsales_ytd += currentSales.netsales_ytd;
                grossprofit_mtd += currentSales.grossprofit_mtd;
                grossprofit_ytd += currentSales.grossprofit_ytd;
            }

            if (netsales_mtd != 0){
                pgrossprofit_mtd = grossprofit_mtd / netsales_mtd * 100;
            }

            if (netsales_ytd != 0){
                pgrossprofit_ytd = grossprofit_ytd / netsales_ytd * 100;
            }

            txtNSMTD.setText(CurrencyHelper.format(netsales_mtd));
            txtNSYTD.setText(CurrencyHelper.format(netsales_ytd));
            txtGPMTD.setText(CurrencyHelper.format(grossprofit_mtd));
            txtGPYTD.setText(CurrencyHelper.format(grossprofit_ytd));
            txtPGPMTD.setText(CurrencyHelper.decformat(pgrossprofit_mtd) + " %");
            txtPGPYTD.setText(CurrencyHelper.decformat(pgrossprofit_ytd) + " %");

            loadChartMTD(listCurrentSales);
            loadChartYTD(listCurrentSales);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void Animate(View itemView, int i) {
        itemView.setTranslationX(itemView.getX() + 400);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setDuration(500);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();

    }

    private void loadChartMTD(ModelCurrentSales[] listCurrentSales){
        try {
            ArrayList arcurrentsales = new ArrayList();
            for (ModelCurrentSales currentSales : listCurrentSales) {

                for (ModelProject project : projects){
                    if (project.getProjectcode().equals(currentSales.projectcode)) {
                        arcurrentsales.add(new PieEntry((float) (currentSales.netsales_mtd), project.getProjectname()));
                        break;
                    }
                }

            }
            PieDataSet dataSet = new PieDataSet(arcurrentsales, "");
            ArrayList captions = new ArrayList();
            PieData data = new PieData(dataSet);
            chartMTD.setData(data);
            chartMTD.setEntryLabelColor(getContext().getColor(R.color.colorRegulerText));
            chartMTD.setEntryLabelTextSize(10);
            dataSet.setColors(new int[]{R.color.agingCurrent, R.color.agingRange4, R.color.agingRange2, R.color.agingRange3, R.color.agingRange4}, getContext());


            chartMTD.setDescription(null);
            chartMTD.getLegend().setEnabled(false);   // Hide the legend
            chartMTD.animateXY(1000, 1000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadChartYTD(ModelCurrentSales[] listCurrentSales){
        try {
            ArrayList arcurrentsales = new ArrayList();
            for (ModelCurrentSales currentSales : listCurrentSales) {

                for (ModelProject project : projects){
                    if (project.getProjectcode().equals(currentSales.projectcode)) {
                        arcurrentsales.add(new PieEntry((float) (currentSales.netsales_ytd), project.getProjectname()));
                        break;
                    }
                }

            }
            PieDataSet dataSet = new PieDataSet(arcurrentsales, "");
            ArrayList captions = new ArrayList();
            PieData data = new PieData(dataSet);
            chartYTD.setData(data);
            chartYTD.setEntryLabelColor(getContext().getColor(R.color.colorRegulerText));
            chartYTD.setEntryLabelTextSize(10);
            dataSet.setColors(new int[]{R.color.agingCurrent, R.color.agingRange4, R.color.agingRange2, R.color.agingRange3, R.color.agingRange4}, getContext());


            chartYTD.setDescription(null);
            chartYTD.getLegend().setEnabled(false);   // Hide the legend
            chartYTD.animateXY(1000, 1000);
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
