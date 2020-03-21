package com.fmanda.autopartdashboard.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
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
    public boolean isExpand = true;
    private static final long DURATION = 1000;

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
//        viewHolder.txtSumValue.setText(CurrencyHelper.format(viewHolder.ModelProfitLoss.getReportval()));
        viewHolder.txtSumName.setText("Total " + viewHolder.txtNama.getText());

        if (isExpand){
            viewHolder.rvDetail.setVisibility(View.VISIBLE);
            viewHolder.txtSumName.setVisibility(View.VISIBLE);
        }else{
            viewHolder.rvDetail.setVisibility(View.GONE);
            viewHolder.txtSumName.setVisibility(View.GONE);
        }

        //override here
        Integer[] groupOnly = {200,250,400,450,500};
        if (Arrays.asList(groupOnly).contains(viewHolder.ModelProfitLoss.getReportgroup() )) {
            viewHolder.rvDetail.setVisibility(View.GONE);
            viewHolder.txtSumName.setVisibility(View.GONE);
        }

        ProfitLossDetail ProfitLossDetail = new ProfitLossDetail(context,
                ModelProfitLoss.getDetails(this.profits, viewHolder.ModelProfitLoss.getReportgroup()));
        viewHolder.rvDetail.setLayoutManager(new GridLayoutManager(context, 1));
        viewHolder.rvDetail.setAdapter(ProfitLossDetail);

        if (viewHolder.ModelProfitLoss.getReportval() == 0){
//            viewHolder.lnGroup.setVisibility(View.GONE);
            viewHolder.lnGroup.setLayoutParams(viewHolder.hideparams);
        }
        Animate(viewHolder.itemView, i);

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
        public Button btnExpand;
        public LinearLayout lnGroup;
//        public TextView txtSumValue;
        public RecyclerView rvDetail;
        public LinearLayout lnSummary;
        public LinearLayout.LayoutParams hideparams = new LinearLayout.LayoutParams(0, 0);

        public ViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtName);
            txtVal = itemView.findViewById(R.id.txtVal);
//            txtSumValue = itemView.findViewById(R.id.txtSumValue);
            txtSumName = itemView.findViewById(R.id.txtSumName);
            rvDetail = itemView.findViewById(R.id.rvDetail);

//            rvDetail.addItemDecoration(new DividerItemDecoration(rvDetail.getContext(), DividerItemDecoration.VERTICAL));
            lnSummary = itemView.findViewById(R.id.lnSummary);
            lnGroup = itemView.findViewById(R.id.lnGroup);


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

    private void Animate(View itemView, int i) {
        itemView.setTranslationX(itemView.getX() + 400);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setDuration(DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();

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