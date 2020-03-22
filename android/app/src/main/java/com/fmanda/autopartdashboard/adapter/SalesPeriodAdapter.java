package com.fmanda.autopartdashboard.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class SalesPeriodAdapter extends RecyclerView.Adapter<SalesPeriodAdapter.ViewHolder> {
    private Context context;
    private List<ModelSalesPeriod> salesPeriods;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public SalesPeriodAdapter(Context context, List<ModelSalesPeriod> salesPeriods) {
        this.context = context;
        this.salesPeriods = salesPeriods;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_sales_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try{
            viewHolder.modelSalesPeriod = salesPeriods.get(i);
            viewHolder.txtDay.setText(
                    new SimpleDateFormat("EEEE").format(
                            viewHolder.modelSalesPeriod.getTransdate()
                    )
            );
            viewHolder.txtDate.setText(
                    new SimpleDateFormat("dd-MMM-yyyy").format(
                            viewHolder.modelSalesPeriod.getTransdate()
                    )
            );
            viewHolder.txtNetSales.setText(CurrencyHelper.format(viewHolder.modelSalesPeriod.getNetsales()));
            viewHolder.txtGrossProfit.setText(CurrencyHelper.format(viewHolder.modelSalesPeriod.getGrossprofit()));

            Calendar c = Calendar.getInstance();
            c.setTime(viewHolder.modelSalesPeriod.getTransdate());
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                viewHolder.txtDay.setTextColor(context.getColor(R.color.colorWarning));
                viewHolder.txtDate.setTextColor(context.getColor(R.color.colorWarning));
            }else{
                viewHolder.txtDay.setTextColor(context.getColor(R.color.colorRegulerText));
                viewHolder.txtDate.setTextColor(context.getColor(R.color.colorRegulerText));
            }
            Animate(viewHolder.itemView, i);
        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return salesPeriods.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelSalesPeriod modelSalesPeriod;
        public TextView txtDate;
        public TextView txtNetSales;
        public TextView txtGrossProfit;
        public TextView txtDay;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtNetSales = itemView.findViewById(R.id.txtNetSales);
            txtGrossProfit = itemView.findViewById(R.id.txtGrossProfit);
            txtDay = itemView.findViewById(R.id.txtDay);
        }
        //
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(modelSalesPeriod);
            }
        }


    }

    public interface ItemClickListener {
        void onClick(ModelSalesPeriod modelSalesPeriod);
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
        animatorTranslateY.setDuration(500);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();

    }


}