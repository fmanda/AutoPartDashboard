package com.fmanda.autopartdashboard.ui.cashflow;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerAPAging;
import com.fmanda.autopartdashboard.controller.ControllerCashFlow;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.fmanda.autopartdashboard.model.ModelCashFlow;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CashflowFragment extends Fragment {

    private CashflowViewModel mViewModel;
    ArrayAdapter<String> spProjectAdapter;
    Spinner spProject;
    Spinner spMonth;
    Spinner spYear;
    boolean spProjectinit = true;
    boolean spMonthinit = true;
    boolean spYearinit = true;
    List<ModelProject> projects = new ArrayList<>();
    LinearLayout lnParam;
    TextView txtSales;
    TextView txtOtherIncome;
    TextView txtIncome;
    TextView txtPurchase;
    TextView txtOtherExpense;
    TextView txtExpense;
    PieChart chart;

    public static CashflowFragment newInstance() {
        return new CashflowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CashflowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cashflow, container, false);
        setHasOptionsMenu(true);

        txtSales = root.findViewById(R.id.txtSales);
        txtOtherIncome = root.findViewById(R.id.txtOtherIncome);
        txtIncome = root.findViewById(R.id.txtIncome);
        txtPurchase = root.findViewById(R.id.txtPurchase);
        txtOtherExpense = root.findViewById(R.id.txtOtherExpense);
        txtExpense = root.findViewById(R.id.txtExpense);

        chart = root.findViewById(R.id.chart);

        lnParam = root.findViewById(R.id.lnParam);
        spMonth = root.findViewById(R.id.spMonth);
        spYear = root.findViewById(R.id.spYear);
        spProject = root.findViewById(R.id.spProject);
        spProjectAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spProjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjectAdapter);
        reInitProject();

        Calendar c = Calendar.getInstance();
        spMonth.setSelection(c.get(Calendar.MONTH));

        for (int i = 0; i < spYear.getAdapter().getCount(); i++) {
            if (Integer.parseInt(spYear.getAdapter().getItem(i).toString()) == c.get(Calendar.YEAR)) {
                spYear.setSelection(i);
                break;
            }
        }

        //set event after initiate, update :useless.. so we use initialSpinner = false;
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        loadFromRest();

        return root;
    }


    private void loadFromRest() {
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                loadCashFlow();
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

        cr.DownloadCashFlow(spMonth.getSelectedItemPosition()+1, Integer.parseInt(spYear.getSelectedItem().toString()));
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

    private void Animate(View itemView, int i) {
        itemView.setTranslationX(itemView.getX() + 400);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.setStartDelay(100*i);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();

    }

    private void loadCashFlow(){
        String paramProject = "";
        for (ModelProject project : projects) {
            if (project.getProjectname() == spProject.getSelectedItem().toString()) {
                paramProject = project.getProjectcode();
            }
        }


        ControllerCashFlow cr = new ControllerCashFlow(getContext());
        ModelCashFlow cashFlow =  cr.getCashFlow(paramProject, spMonth.getSelectedItemPosition()+1, Integer.parseInt(spYear.getSelectedItem().toString()));

        txtSales.setText(CurrencyHelper.format(cashFlow.getSales()));
        txtOtherIncome.setText(CurrencyHelper.format(cashFlow.getOtherincome()));
        txtIncome.setText(CurrencyHelper.format(cashFlow.getSales() + cashFlow.getOtherincome()));
        txtPurchase.setText(CurrencyHelper.format(cashFlow.getPurchase()));
        txtOtherExpense.setText(CurrencyHelper.format(cashFlow.getOtherexpense()));
        txtExpense.setText(CurrencyHelper.format(cashFlow.getPurchase() + cashFlow.getOtherexpense()  ));

        Animate((View) txtSales, 0);
        Animate((View) txtOtherIncome, 1);
        Animate((View) txtIncome, 2);
        Animate((View) txtPurchase, 3);
        Animate((View) txtOtherExpense, 4);
        Animate((View) txtExpense, 5);

        loadChart(cashFlow);

    }

    private void loadChart(ModelCashFlow cashFlow){

        ArrayList cashflows = new ArrayList();

        cashflows.add(new PieEntry((float) (cashFlow.getSales() + cashFlow.getOtherincome()), 0));
        cashflows.add(new PieEntry((float) (cashFlow.getPurchase() + cashFlow.getOtherexpense()), 1));

        PieDataSet dataSet = new PieDataSet(cashflows, "");

        ArrayList captions = new ArrayList();
        PieData data = new PieData(dataSet);
        chart.setData(data);
        dataSet.setColors( new int[] { R.color.agingCurrent, R.color.agingRange4}, getContext());
        dataSet.setLabel("'");

        chart.setDescription(null);
        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.animateXY(1000, 1000);
    }


}