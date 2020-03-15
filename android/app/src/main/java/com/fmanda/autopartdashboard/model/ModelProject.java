package com.fmanda.autopartdashboard.model;

import android.database.sqlite.SQLiteDatabase;

public class ModelProject extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private String projectname;

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public ModelProject(String projectcode, String projectname) {
        this.projectcode = projectcode;
        this.projectname = projectname;
    }

    public ModelProject(){
    }

}