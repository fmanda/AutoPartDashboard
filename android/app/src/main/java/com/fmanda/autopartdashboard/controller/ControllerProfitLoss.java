package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;
import com.fmanda.autopartdashboard.model.ModelProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerProfitLoss {
    private Context context;

    public ControllerProfitLoss(Context context) {
        this.context = context;
    }


    public List<ModelProfitLoss> getProfitLoss(String projectcode, int monthperiod, int yearperiod){
        try {
            List<ModelProfitLoss> profits = new ArrayList<ModelProfitLoss>();
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            String sql = "select reportname, sum(reportval) as reportval, reportgroup, groupname,  min(reportidx) as reportidx from profitloss";
            sql += " where monthperiod = " + String.valueOf(monthperiod) + " and yearperiod = " + String.valueOf(yearperiod);
            if (projectcode != "" && projectcode != "0") {
                sql += " and projectcode = '" + projectcode + "'";
            }
            sql += " group by reportname, reportgroup, groupname order by reportidx";

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelProfitLoss profit = new ModelProfitLoss();
                profit.loadFromCursor(cursor);
                profits.add(profit);
            }
            return profits;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

