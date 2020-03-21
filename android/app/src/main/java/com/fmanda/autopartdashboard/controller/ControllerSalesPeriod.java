package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerSalesPeriod {
    private Context context;

    public ControllerSalesPeriod(Context context) {
        this.context = context;
    }


    public List<ModelSalesPeriod> getSalesPeriods(String projectcode, Date startdate, Date enddate){
        try {
            List<ModelSalesPeriod> profits = new ArrayList<ModelSalesPeriod>();
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            String sql = "select transdate, sum(netsales) as netsales, sum(cogs) as cogs, sum(grossprofit) as grossprofit from salesperiod";
            sql += " where transdate between '" + String.valueOf(startdate.getTime())
                 + "' and '" + String.valueOf(enddate.getTime()) + "'";
            if (projectcode != "" && projectcode != "0") {
                sql += " and projectcode = '" + projectcode + "'";
            }
            sql += " group by transdate ";
            sql += " order by transdate desc ";

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelSalesPeriod profit = new ModelSalesPeriod();
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

