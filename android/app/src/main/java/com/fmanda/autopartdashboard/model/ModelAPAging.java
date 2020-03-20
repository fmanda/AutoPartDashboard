package com.fmanda.autopartdashboard.model;

public class ModelAPAging extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private double current;
    @TableField
    private double range1;
    @TableField
    private double range2;
    @TableField
    private double range3;
    @TableField
    private double range4;
    @TableField
    private double total;

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getRange1() {
        return range1;
    }

    public void setRange1(double range1) {
        this.range1 = range1;
    }

    public double getRange2() {
        return range2;
    }

    public void setRange2(double range2) {
        this.range2 = range2;
    }

    public double getRange3() {
        return range3;
    }

    public void setRange3(double range3) {
        this.range3 = range3;
    }

    public double getRange4() {
        return range4;
    }

    public void setRange4(double range4) {
        this.range4 = range4;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ModelAPAging(){
    }

}