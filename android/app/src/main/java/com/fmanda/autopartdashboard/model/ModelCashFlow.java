package com.fmanda.autopartdashboard.model;

public class ModelCashFlow extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private int monthperiod;
    @TableField
    private int yearperiod;
    @TableField
    private double sales;
    @TableField
    private double purchase;
    @TableField
    private double otherincome;
    @TableField
    private double otherexpense;

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public int getMonthperiod() {
        return monthperiod;
    }

    public void setMonthperiod(int monthperiod) {
        this.monthperiod = monthperiod;
    }

    public int getYearperiod() {
        return yearperiod;
    }

    public void setYearperiod(int yearperiod) {
        this.yearperiod = yearperiod;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public double getOtherincome() {
        return otherincome;
    }

    public void setOtherincome(double otherincome) {
        this.otherincome = otherincome;
    }

    public double getOtherexpense() {
        return otherexpense;
    }

    public void setOtherexpense(double otherexpense) {
        this.otherexpense = otherexpense;
    }

    public ModelCashFlow(){
    }

}