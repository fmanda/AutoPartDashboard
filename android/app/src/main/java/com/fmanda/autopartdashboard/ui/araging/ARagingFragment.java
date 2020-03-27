package com.fmanda.autopartdashboard.ui.araging;

import androidx.lifecycle.ViewModelProvider;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerARAging;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.BaseModel;
import com.fmanda.autopartdashboard.model.ModelARAging;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ARagingFragment extends Fragment {

    TextView txtCurrent;
    TextView txtRange1;
    TextView txtRange2;
    TextView txtRange3;
    TextView txtRange4;
    TextView txtTotal;
    LinearLayout lnCurrent;
    LinearLayout lnRange1;
    LinearLayout lnRange2;
    LinearLayout lnRange3;
    LinearLayout lnRange4;
    LinearLayout lnTotal;
    PieChart chart;
    List<ModelProject> projects = new ArrayList<>();
    Spinner spProject;
    ArrayAdapter<String> spProjectAdapter;
    boolean spProjectinit = true;

    
    private ARagingViewModel mViewModel;

    public static ARagingFragment newInstance() {
        return new ARagingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (!chekLogin()) {
            return null;
        }
        mViewModel = new ViewModelProvider(this).get(ARagingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_araging, container, false);

        txtCurrent = root.findViewById(R.id.txtCurrent);
        txtRange1 = root.findViewById(R.id.txtRange1);
        txtRange2 = root.findViewById(R.id.txtRange2);
        txtRange3 = root.findViewById(R.id.txtRange3);
        txtRange4 = root.findViewById(R.id.txtRange4);
        txtTotal = root.findViewById(R.id.txtTotal);

        lnCurrent = root.findViewById(R.id.lnCurrent);
        lnRange1 = root.findViewById(R.id.lnRange1);
        lnRange2 = root.findViewById(R.id.lnRange2);
        lnRange3 = root.findViewById(R.id.lnRange3);
        lnRange4 = root.findViewById(R.id.lnRange4);
        lnTotal = root.findViewById(R.id.lnTotal);
        chart = root.findViewById(R.id.chart);
        spProject = root.findViewById(R.id.spProject);

        txtCurrent.setText(CurrencyHelper.format(0));
        txtRange1.setText(CurrencyHelper.format(0));
        txtRange2.setText(CurrencyHelper.format(0));
        txtRange3.setText(CurrencyHelper.format(0));
        txtRange4.setText(CurrencyHelper.format(0));
        txtTotal.setText(CurrencyHelper.format(0));

        spProject = root.findViewById(R.id.spProject);
        spProjectAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        spProjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjectAdapter);
        reInitProject();

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


    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onError(String msg) {
//                Toast.makeText(getContext(), "Gagal koneksi ke REST Server", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String msg) {
            }
        });

        cr.setObjectListener(new ControllerRest.ObjectListener() {
            @Override
            public void onSuccess(BaseModel[] obj) {
                loadAgingAR(obj);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), "Gagal koneksi ke REST Server", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        cr.DownloadCurrentARAging();
    }

    private void loadAgingAR(BaseModel[] responses){
        ControllerARAging cr = new ControllerARAging(getContext());

        if (responses == null){
            return;
        }

        if (responses.length == 0){
            return;
        }


        String paramProject = "";
        for (ModelProject project : projects) {
            if (project.getProjectname() == spProject.getSelectedItem().toString()) {
                paramProject = project.getProjectcode();
            }
        }

        ModelARAging[] arAgings = (ModelARAging[]) responses;
        double current = 0;
        double range1 = 0;
        double range2 = 0;
        double range3 = 0;
        double range4 = 0;
        double total = 0;

        for (ModelARAging arAging : arAgings){
            if (paramProject.equals("0") || paramProject.equals(arAging.getProjectcode())) {
                current += arAging.getCurrent();
                range1 += arAging.getRange1();
                range2 += arAging.getRange2();
                range3 += arAging.getRange3();
                range4 += arAging.getRange4();
                total += arAging.getTotal();
            }
        }

        txtCurrent.setText(CurrencyHelper.format(current));
        txtRange1.setText(CurrencyHelper.format(range1));
        txtRange2.setText(CurrencyHelper.format(range2));
        txtRange3.setText(CurrencyHelper.format(range3));
        txtRange4.setText(CurrencyHelper.format(range4));
        txtTotal.setText(CurrencyHelper.format(total));

        Animate((View) lnCurrent, 0);
        Animate((View) lnRange1, 1);
        Animate((View) lnRange2, 2);
        Animate((View) lnRange3, 3);
        Animate((View) lnRange4, 4);
        Animate((View) lnTotal, 5);

        loadPieChart(current, range1, range2, range3, range4);

    }

    private void loadPieChart(double current, double range1, double range2, double range3, double range4){

        ArrayList agings = new ArrayList();

        agings.add(new PieEntry((float) current, "Belum Jt"));
        agings.add(new PieEntry((float) range1, "0-30"));
        agings.add(new PieEntry((float) range2, "31-60"));
        agings.add(new PieEntry((float) range3, "61-90"));
        agings.add(new PieEntry((float) range4, ">90"));

        PieDataSet dataSet = new PieDataSet(agings, "Aging AP");

        ArrayList captions = new ArrayList();
        PieData data = new PieData(dataSet);
        chart.setData(data);
        dataSet.setColors( new int[] { R.color.agingCurrent, R.color.agingRange1, R.color.agingRange2, R.color.agingRange3,  R.color.agingRange4 }, getContext());
        dataSet.setLabel("'");

        chart.setDescription(null);
        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.animateXY(1000, 1000);
    }


//    private void loadBarChart(double current, double range1, double range2, double range3, double range4){
//
//        ArrayList agings = new ArrayList();
//        float divider = 1000000;
//
//        agings.add(new BarEntry(5, (float) current/divider));
//        agings.add(new BarEntry(4, (float) range1/divider));
//        agings.add(new BarEntry(3, (float) range2/divider));
//        agings.add(new BarEntry(2, (float) range3/divider));
//        agings.add(new BarEntry(1, (float) range4/divider));
//
//        chart.getAxisLeft().setDrawLabels(false);
//        chart.getAxisRight().setDrawLabels(false);
//        chart.getLegend().setEnabled(false);   // Hide the legend
//        chart.setFitBars(Boolean.TRUE);
//        chart.getDescription().setText("");
//
//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                try {
//                    switch ((int)value) {
//                        case 1:
//                            return ">90";
//                        case 2:
//                            return "61-90";
//                        case 3:
//                            return "31-60";
//                        case 4:
//                            return "0-30";
//                        case 5:
//                            return "Current";
//                    }
//                }catch(Exception e){
//                    //error here
////                        Toast.makeText(getContext(), "Count " + String.valueOf(dates.size())
////                                + ", request idx : " + String.valueOf(value)
////                                , Toast.LENGTH_SHORT).show();
//                }
//                return null;
//            }
//        };
////
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setValueFormatter(formatter);
//
//        BarDataSet dataSet = new BarDataSet(agings, "Aging AP");
//
//        ArrayList captions = new ArrayList();
//        BarData data = new BarData(dataSet);
//        chart.setData(data);
//        dataSet.setColors( new int[] { R.color.agingCurrent, R.color.agingRange4, R.color.agingRange2, R.color.agingRange3,  R.color.agingRange4 }, getContext());
//        dataSet.setLabel("'");
//
//        chart.setDescription(null);
//        chart.getLegend().setEnabled(false);   // Hide the legend
//        chart.animateXY(1000, 1000);
//    }

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
