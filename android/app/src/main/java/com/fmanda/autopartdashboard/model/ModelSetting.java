package com.fmanda.autopartdashboard.model;

import android.database.sqlite.SQLiteDatabase;

public class ModelSetting extends BaseModel {
    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public String getVarvalue() {
        return varvalue;
    }

    public void setVarvalue(String varvalue) {
        this.varvalue = varvalue;
    }

    @TableField
    private String varname;
    @TableField
    private String varvalue;

    public ModelSetting() {
        this.varname = "";
        this.varvalue = "";
    }

    public ModelSetting(String varname, String varvalue){
        this.varname = varname;
        this.varvalue = varvalue;
    }

    public static void initMetaData(SQLiteDatabase db) {
        //company_info
//        new ModelSetting("company_name","[your-company-name]").saveToDB(db);
//        new ModelSetting("company_address","[your company address]\n[your company address]").saveToDB(db);
//        new ModelSetting("company_phone","[company-phone]").saveToDB(db);
        new ModelSetting("rest_url","api.motoroli.web.id").saveToDB(db);

    }

}
