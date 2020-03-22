package com.fmanda.autopartdashboard.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.helper.CurrencyHelper;
import com.fmanda.autopartdashboard.model.ModelInventory;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private Context context;
    private List<ModelInventory> inventories;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public InventoryAdapter(Context context, List<ModelInventory> inventories) {
        this.context = context;
        this.inventories = inventories;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_inventory_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try{
            viewHolder.inventory = inventories.get(i);

            viewHolder.txtAmount.setText(CurrencyHelper.format(viewHolder.inventory.getAmount()));

            if (viewHolder.inventory.getHeader_flag() == 0 || viewHolder.inventory.getHeader_flag() == -1){
                viewHolder.txtName.setText(viewHolder.inventory.getTransName());
            }else{
                viewHolder.txtName.setText("    " + viewHolder.inventory.getTransName());
            }

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.lnDetail.getLayoutParams();
            if  (viewHolder.inventory.getHeader_flag() == -1){
                viewHolder.txtName.setTypeface(viewHolder.txtName.getTypeface(), Typeface.BOLD);
                viewHolder.txtAmount.setTypeface(viewHolder.txtAmount.getTypeface(), Typeface.BOLD);
                params.setMargins(0,20,0,0);
            }else{
                viewHolder.txtName.setTypeface(viewHolder.txtName.getTypeface(), Typeface.NORMAL);
                viewHolder.txtAmount.setTypeface(viewHolder.txtAmount.getTypeface(), Typeface.NORMAL);
                params.setMargins(0,0,0,0);
            }
            viewHolder.lnDetail.setLayoutParams(params);



            if  (viewHolder.inventory.getAmount() < 0){
                viewHolder.txtAmount.setTextColor(context.getColor(R.color.agingRange3));
            }else{
                viewHolder.txtAmount.setTextColor(context.getColor(R.color.colorMoney));
            }

            if (viewHolder.inventory.getHeader_flag() == -1){
                viewHolder.txtAmount.setTextColor(context.getColor(R.color.colorPrimary));
                viewHolder.txtName.setTextColor(context.getColor(R.color.colorPrimary));
            }else{
                viewHolder.txtName.setTextColor(context.getColor(R.color.colorRegulerText));
            }

            Animate(viewHolder.itemView, i);

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelInventory inventory;
        public TextView txtName;
        public TextView txtAmount;
        public LinearLayout lnDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            lnDetail = itemView.findViewById(R.id.lnDetail);
        }
        //
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(inventory);
            }
        }


    }

    public interface ItemClickListener {
        void onClick(ModelInventory inventory);
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