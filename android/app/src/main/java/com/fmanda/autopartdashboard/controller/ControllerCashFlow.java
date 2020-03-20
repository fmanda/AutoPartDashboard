package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelCashFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerCashFlow {
    private Context context;

    public ControllerCashFlow(Context context) {
        this.context = context;
    }


    public List<ModelCashFlow> getCashFlow(String projectcode, int monthperiod, int yearperiod){
        try {
            List<ModelCashFlow> cashFlows = new ArrayList<ModelCashFlow>();
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            String sql = "select * from cashflow";
            sql += " where monthperiod = " + String.valueOf(monthperiod) + " and yearperiod = " + String.valueOf(yearperiod);
            if (projectcode != "" && projectcode != "0") {
                sql += " and projectcode = '" + projectcode + "'";
            }

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelCashFlow cashFlow = new ModelCashFlow();
                cashFlow.loadFromCursor(cursor);
                cashFlows.add(cashFlow);
            }
            return cashFlows;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

