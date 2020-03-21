package com.fmanda.autopartdashboard.ui.salesperiod;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.SalesPeriodAdapter;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.controller.ControllerSalesPeriod;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SalesPeriodFragment extends Fragment {

    private SalesPeriodViewModel mViewModel;
    private RecyclerView rvSales;
    private SalesPeriodAdapter salesPeriodAdapter;
    ArrayAdapter<String> spProjectAdapter;
    List<ModelProject> projects = new ArrayList<>();
    private LinearLayout lnParam;

    Spinner spProject;
    Spinner spPeriod;
    boolean spProjectinit = true;
    boolean spPeriodInit = true;

    BarChart chart ;


    public static SalesPeriodFragment newInstance() {
        return new SalesPeriodFragment();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(SalesPeriodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salesperiod, container, false);
        rvSales = root.findViewById(R.id.rvSales);
        salesPeriodAdapter = new SalesPeriodAdapter(getContext(), mViewModel.salesPeriods);
        rvSales.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvSales.setAdapter(salesPeriodAdapter);
        chart = root.findViewById(R.id.chart);
        lnParam = root.findViewById(R.id.lnParam);

        root.findViewById(R.id.lnParam);

        spPeriod = root.findViewById(R.id.spPeriod);
        spProject = root.findViewById(R.id.spProject);
        spProjectAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spProjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjectAdapter);
        reInitProject();

        spPeriod.setSelection(1);
        showOrHideParam();

        //set event after initiate, update :useless.. so we use initialSpinner = false;
        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spPeriodInit) {
                    spPeriodInit = false;
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


        loadFromRest();
        return root;
    }

    private void loadSales(Date dtStart, Date dtEnd){
        try {
            String paramProject = "";
            for (ModelProject project : projects){
                if (project.getProjectname() == spProject.getSelectedItem().toString()){
                    paramProject = project.getProjectcode();
                }
            }

            ControllerSalesPeriod controllerSalesPeriod = new ControllerSalesPeriod(getContext());

            mViewModel.salesPeriods.clear();
            mViewModel.salesPeriods.addAll(controllerSalesPeriod.getSalesPeriods(paramProject, dtStart, dtEnd));
            salesPeriodAdapter.notifyDataSetChanged();

            List<BarEntry> entries1 = new ArrayList<BarEntry>();
            final List<String> dates = new ArrayList<>();
            int i = 0;

            dates.clear();
            for (int n = mViewModel.salesPeriods.size()-1 ; n >= 0 ; n--) {
                ModelSalesPeriod salesPeriod = mViewModel.salesPeriods.get(n);
                entries1.add(new BarEntry(i, (float) (salesPeriod.getNetsales())/1000)  );
                dates.add( new SimpleDateFormat("ddMMM").format(
                        salesPeriod.getTransdate()
                ));

                i++;

//                if (i>=7) break;
            }

            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    try {
                        return dates.get((int) value);
                    }catch(Exception e){
                        //error here
//                        Toast.makeText(getContext(), "Count " + String.valueOf(dates.size())
//                                + ", request idx : " + String.valueOf(value)
//                                , Toast.LENGTH_SHORT).show();
                    }
                    return null;
                }
            };
//
            XAxis xAxis = chart.getXAxis();
//            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setValueFormatter(formatter);

            BarDataSet dataSet1 = new BarDataSet(entries1, "Net Sales");
            BarData barData = new BarData();
            barData.addDataSet(dataSet1);

//            dataSet1.setColor( getContext().getColor(R.color.colorAccent) );
            chart.getAxisLeft().setDrawLabels(false);
            chart.getAxisRight().setDrawLabels(false);
//            chart.getXAxis().setDrawLabels(false);

            chart.getLegend().setEnabled(false);   // Hide the legend

            chart.setFitBars(Boolean.TRUE);
            chart.getDescription().setText("");

//            chart.clear();
            chart.setData(barData);

            chart.invalidate(); // refresh
            chart.animateY(2000);
        }catch(Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromRest(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);

        final Date dtEnd = c.getTime();

        if (spPeriod.getSelectedItemPosition() == 0) {
            c.add(Calendar.DAY_OF_YEAR, -14);
        }
        else{
            c.add(Calendar.DAY_OF_YEAR, -30);
        }
        final Date dtStart = c.getTime();

        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                loadSales(dtStart, dtEnd);
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

        cr.DownloadSalesPeriod(dtStart, dtEnd);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tb_tune){
            showOrHideParam();
        }
        return super.onOptionsItemSelected(item);
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

}
