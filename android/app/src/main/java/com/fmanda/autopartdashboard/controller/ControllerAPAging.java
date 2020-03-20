package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelAPAging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerAPAging {
    private Context context;

    public ControllerAPAging(Context context) {
        this.context = context;
    }

    public List<ModelAPAging> getAPAging(){
        try {
            List<ModelAPAging> modelAPAgings = new ArrayList<ModelAPAging>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from apaging", null);
            while (cursor.moveToNext()) {
                ModelAPAging modelAPAging = new ModelAPAging();
                modelAPAging.loadFromCursor(cursor);
                modelAPAgings.add(modelAPAging);
            }
            return modelAPAgings;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}

