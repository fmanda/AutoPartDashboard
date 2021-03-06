package com.fmanda.autopartdashboard.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.fmanda.autopartdashboard.model.ModelARAging;
import com.fmanda.autopartdashboard.model.ModelCashFlow;
import com.fmanda.autopartdashboard.model.ModelInventory;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;
import com.fmanda.autopartdashboard.model.ModelSetting;

/**
 * Created by fmanda on 08/01/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "autopartdashboard";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper mInstance;

    public static synchronized DBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DBHelper(Context context, String name) {
        super(context, name, null, 0);
        this.DATABASE_NAME = name;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new ModelSetting().generateMetaData());
        db.execSQL(new ModelProject().generateMetaData());
        db.execSQL(new ModelProfitLoss().generateMetaData());
        db.execSQL(new ModelSalesPeriod().generateMetaData());
        db.execSQL(new ModelCashFlow().generateMetaData());
        db.execSQL(new ModelAPAging().generateMetaData());
//        db.execSQL(new ModelARAging().generateMetaData());
        db.execSQL(new ModelInventory().generateMetaData());
        ModelSetting.initMetaData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        resetDatabase(db);
    }


    public void dropAllTables(SQLiteDatabase db){
        db.execSQL(new ModelSetting().generateDropMetaData());
        db.execSQL(new ModelProject().generateDropMetaData());
        db.execSQL(new ModelProfitLoss().generateDropMetaData());
        db.execSQL(new ModelSalesPeriod().generateDropMetaData());
        db.execSQL(new ModelCashFlow().generateDropMetaData());
        db.execSQL(new ModelAPAging().generateDropMetaData());
//        db.execSQL(new ModelARAging().generateDropMetaData());
        db.execSQL(new ModelInventory().generateDropMetaData());
    }

    public void resetDatabase(SQLiteDatabase db){
        dropAllTables(db);
        onCreate(db);
    }


}