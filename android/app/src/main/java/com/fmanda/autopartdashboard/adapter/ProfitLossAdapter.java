package com.fmanda.autopartdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.controller.ControllerSetting;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ProfitLossAdapter extends RecyclerView.Adapter<ProfitLossAdapter.ViewHolder> {
    private Context context;
    private List<ModelProfitLoss> profits;
    private List<ModelProfitLoss> groups;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public ProfitLossAdapter(Context context, List<ModelProfitLoss> groups, List<ModelProfitLoss> profits) {
        this.context = context;
        this.groups = groups;
        this.profits = profits;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_profitloss_group_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.ModelProfitLoss = groups.get(i);
        viewHolder.txtNama.setText(viewHolder.ModelProfitLoss.getGroupname());
        viewHolder.txtVal.setText(CurrencyHelper.format(viewHolder.ModelProfitLoss.getReportval()));
        viewHolder.txtSumValue.setText(CurrencyHelper.format(viewHolder.ModelProfitLoss.getReportval()));
        viewHolder.txtSumName.setText("Total " + viewHolder.txtNama.getText());


        if (viewHolder.ModelProfitLoss.getReportval() < 0){
            viewHolder.txtVal.setTextColor(context.getColor(R.color.colorWarning));
            viewHolder.txtSumValue.setTextColor(context.getColor(R.color.colorWarning));
        }else {
            viewHolder.txtVal.setTextColor(context.getColor(R.color.colorMoney));
            viewHolder.txtSumValue.setTextColor(context.getColor(R.color.colorMoney));
        }

        Integer[] groupOnly = {200,250,400,450,500};
        if (Arrays.asList(groupOnly).contains(viewHolder.ModelProfitLoss.getReportgroup() )) {
            viewHolder.rvDetail.setVisibility(View.GONE);
            viewHolder.txtVal.setVisibility(View.VISIBLE);
            viewHolder.lnSummary.setVisibility(View.GONE);
        }else{
            viewHolder.rvDetail.setVisibility(View.VISIBLE);
            viewHolder.txtVal.setVisibility(View.GONE);
            viewHolder.lnSummary.setVisibility(View.VISIBLE);
        }

        ProfitLossDetail ProfitLossDetail = new ProfitLossDetail(context,
                ModelProfitLoss.getDetails(this.profits, viewHolder.ModelProfitLoss.getReportgroup()));
        viewHolder.rvDetail.setLayoutManager(new GridLayoutManager(context, 1));
        viewHolder.rvDetail.setAdapter(ProfitLossDetail);

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelProfitLoss ModelProfitLoss;
        public TextView txtNama;
        public TextView txtVal;
        public TextView txtSumName;
        public TextView txtSumValue;
        public RecyclerView rvDetail;
        public LinearLayout lnSummary;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtName);
            txtVal = itemView.findViewById(R.id.txtVal);
            txtSumValue = itemView.findViewById(R.id.txtSumValue);
            txtSumName = itemView.findViewById(R.id.txtSumName);
            rvDetail = itemView.findViewById(R.id.rvDetail);
            lnSummary = itemView.findViewById(R.id.lnSummary);
        }
        //
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(ModelProfitLoss);
            }
        }


    }

    public interface ItemClickListener {
        void onClick(ModelProfitLoss ModelProfitLoss);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}

class ProfitLossDetail extends RecyclerView.Adapter<ProfitLossDetail.ViewHolder> {
    private Context context;
    private List<ModelProfitLoss> profits;
    private LayoutInflater mInflater;

    public ProfitLossDetail(Context context, List<ModelProfitLoss> profits) {
        this.context = context;
        this.profits = profits;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_profitloss_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.ModelProfitLoss = profits.get(i);
        viewHolder.txtVal.setText(CurrencyHelper.format(viewHolder.ModelProfitLoss.getReportval()));
        viewHolder.txtName.setText(viewHolder.ModelProfitLoss.getReportname());
        if (viewHolder.ModelProfitLoss.getReportval() < 0){
            viewHolder.txtVal.setTextColor(context.getColor(R.color.colorWarning));
        }else
            viewHolder.txtVal.setTextColor(context.getColor(R.color.colorMoney));
    }

    @Override
    public int getItemCount() {
        return profits.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelProfitLoss ModelProfitLoss;
        public TextView txtName;
        public TextView txtVal;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtVal = itemView.findViewById(R.id.txtVal);
        }

        @Override
        public void onClick(View v) {

        }
    }


}