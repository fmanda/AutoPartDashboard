package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelARAging;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerARAging {
    private Context context;

    public ControllerARAging(Context context) {
        this.context = context;
    }

    public ModelARAging getARaging(String projectcode){
        try {
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();

            String sql = "select sum(current) as current,  sum(range1) as range1, sum(range2) as range2, " +
                    " sum(range3) as range3, sum(range4) as range4, sum(total) as total  from araging";

            if (projectcode != "" && projectcode != "0") {
                sql += " where projectcode = '" + projectcode + "'";
            }
            Cursor cursor = rdb.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                ModelARAging modelARaging = new ModelARAging();
                modelARaging.loadFromCursor(cursor);
                return modelARaging;
            }

            return new ModelARAging();
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}

