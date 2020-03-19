package com.fmanda.autopartdashboard.model;

import java.util.Date;

public class ModelSalesPeriod extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private Date transdate;
    @TableField
    private double netsales;
    @TableField
    private double cogs;
    @TableField
    private double grossprofit;

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public Date getTransdate() {
        return transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    public double getNetsales() {
        return netsales;
    }

    public void setNetsales(double netsales) {
        this.netsales = netsales;
    }

    public double getCogs() {
        return cogs;
    }

    public void setCogs(double cogs) {
        this.cogs = cogs;
    }

    public double getGrossprofit() {
        return grossprofit;
    }

    public void setGrossprofit(double grossprofit) {
        this.grossprofit = grossprofit;
    }

    public ModelSalesPeriod() {

    }

}