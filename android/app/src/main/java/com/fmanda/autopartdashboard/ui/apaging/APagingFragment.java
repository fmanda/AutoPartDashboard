package com.fmanda.autopartdashboard.ui.apaging;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerAPAging;
import com.fmanda.autopartdashboard.controller.ControllerRest;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class APagingFragment extends Fragment {

    private APagingViewModel mViewModel;
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
    HorizontalBarChart chart;

    public static APagingFragment newInstance() {
        return new APagingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(APagingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_apaging, container, false);

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

        txtCurrent.setText(CurrencyHelper.format(0));
        txtRange1.setText(CurrencyHelper.format(0));
        txtRange2.setText(CurrencyHelper.format(0));
        txtRange3.setText(CurrencyHelper.format(0));
        txtRange4.setText(CurrencyHelper.format(0));
        txtTotal.setText(CurrencyHelper.format(0));

        loadFromRest();
        return root;
    }


    private void loadFromRest(){
        ControllerRest cr = new ControllerRest(this.getContext());
        cr.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                loadAgingAP();
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

        cr.DownloadAgingAP();
    }

    private void loadAgingAP(){
        ControllerAPAging cr = new ControllerAPAging(getContext());
        ModelAPAging apAging =  cr.getAPAging("");

        txtCurrent.setText(CurrencyHelper.format(apAging.getCurrent()));
        txtRange1.setText(CurrencyHelper.format(apAging.getRange1()));
        txtRange2.setText(CurrencyHelper.format(apAging.getRange2()));
        txtRange3.setText(CurrencyHelper.format(apAging.getRange3()));
        txtRange4.setText(CurrencyHelper.format(apAging.getRange4()));
        txtTotal.setText(CurrencyHelper.format(apAging.getTotal()));

        Animate((View) lnCurrent, 0);
        Animate((View) lnRange1, 1);
        Animate((View) lnRange2, 2);
        Animate((View) lnRange3, 3);
        Animate((View) lnRange4, 4);
        Animate((View) lnTotal, 5);

        loadBarChart(apAging);

    }

    private void loadPieChart(ModelAPAging apAging){

//        ArrayList agings = new ArrayList();
//
//        agings.add(new PieEntry((float) apAging.getCurrent()/1000000, 0));
//        agings.add(new PieEntry((float) apAging.getRange1()/1000000, 1));
//        agings.add(new PieEntry((float) apAging.getRange2()/1000000, 2));
//        agings.add(new PieEntry((float) apAging.getRange4()/1000000, 3));
//        agings.add(new PieEntry((float) apAging.getRange4()/1000000, 4));
//
//        PieDataSet dataSet = new PieDataSet(agings, "Aging AP");
//
//        ArrayList captions = new ArrayList();
//        PieData data = new PieData(dataSet);
//        chart.setData(data);
//        dataSet.setColors( new int[] { R.color.agingCurrent, R.color.agingRange1, R.color.agingRange2, R.color.agingRange3,  R.color.agingRange4 }, getContext());
//        dataSet.setLabel("'");
//
//        chart.setDescription(null);
//        chart.getLegend().setEnabled(false);   // Hide the legend
//        chart.animateXY(5000, 5000);
    }


    private void loadBarChart(ModelAPAging apAging){

        ArrayList agings = new ArrayList();
        float divider = 1000000;

        agings.add(new BarEntry(5, (float) apAging.getCurrent()/divider));
        agings.add(new BarEntry(4, (float) apAging.getRange1()/divider));
        agings.add(new BarEntry(3, (float) apAging.getRange2()/divider));
        agings.add(new BarEntry(2, (float) apAging.getRange4()/divider));
        agings.add(new BarEntry(1, (float) apAging.getRange4()/divider));

//        agings.add(new BarEntry((float) apAging.getCurrent(), 0));
//        agings.add(new BarEntry((float) apAging.getRange1(), 1));
//        agings.add(new BarEntry((float) apAging.getRange2(), 2));
//        agings.add(new BarEntry((float) apAging.getRange4(), 3));
//        agings.add(new BarEntry((float) apAging.getRange4(), 4));

        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.setFitBars(Boolean.TRUE);
        chart.getDescription().setText("");

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                try {
                    switch ((int)value) {
                        case 1:
                            return ">90";
                        case 2:
                            return "61-90";
                        case 3:
                            return "31-60";
                        case 4:
                            return "0-30";
                        case 5:
                            return "Current";
                    }
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
        xAxis.setValueFormatter(formatter);

        BarDataSet dataSet = new BarDataSet(agings, "Aging AP");

        ArrayList captions = new ArrayList();
        BarData data = new BarData(dataSet);
        chart.setData(data);
        dataSet.setColors( new int[] { R.color.agingCurrent, R.color.agingRange4, R.color.agingRange2, R.color.agingRange3,  R.color.agingRange4 }, getContext());
        dataSet.setLabel("'");

        chart.setDescription(null);
        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.animateXY(1000, 1000);
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


}
