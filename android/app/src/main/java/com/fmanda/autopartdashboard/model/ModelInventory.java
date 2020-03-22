package com.fmanda.autopartdashboard.model;

public class ModelInventory extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private int header_flag;
    @TableField
    private double amount;

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public int getHeader_flag() {
        return header_flag;
    }

    public void setHeader_flag(int header_flag) {
        this.header_flag = header_flag;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ModelInventory(){
    }

}