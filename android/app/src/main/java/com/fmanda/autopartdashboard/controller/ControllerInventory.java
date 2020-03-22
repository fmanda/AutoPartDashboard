package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.fmanda.autopartdashboard.model.ModelInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerInventory {
    private Context context;

    public ControllerInventory(Context context) {
        this.context = context;
    }

    public List<ModelInventory> getInventories(String projectcode){
        try {
            List<ModelInventory> inventories = new ArrayList<ModelInventory>();
            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();

            String sql = "select header_flag, sum(amount) as amount from inventory";

            if (projectcode != "" && projectcode != "0") {
                sql += " where projectcode = '" + projectcode + "'";
            }
            sql += " group by header_flag";

            ModelInventory iTotal = new ModelInventory();
            iTotal.setHeader_flag(-1);
            iTotal.setAmount(0);
            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelInventory inventory = new ModelInventory();
                inventory.loadFromCursor(cursor);
                inventories.add(inventory);

                iTotal.setAmount(iTotal.getAmount() + inventory.getAmount());
            }
            inventories.add(iTotal);

            return inventories;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}

