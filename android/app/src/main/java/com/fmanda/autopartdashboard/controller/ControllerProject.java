package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerProject {
    private Context context;

    public ControllerProject(Context context) {
        this.context = context;
    }

    public ModelProject getProject(String projectcode){
        try {
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from project where projectcode = '" + projectcode + "'", null);
            ModelProject modelProject= new ModelProject();
            if (cursor.moveToNext()) {
                modelProject.loadFromCursor(cursor);
                return modelProject;
            }
        }catch(Exception ex){
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return new ModelProject();
    }

}

