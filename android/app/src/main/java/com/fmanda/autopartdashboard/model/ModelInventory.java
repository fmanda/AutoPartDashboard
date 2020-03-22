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

    public String getTransName() {
        switch (getHeader_flag()) {
            case 0:
                return "Saldo Awal Tahun";
            case 100:
                return "Pembelian Barang";
            case 150:
                return "Retur Pembelian";
            case 200:
                return "Penjualan Barang";
            case 250:
                return "Retur Penjualan";
            case 400:
                return "Transfer Stock";
            case 450:
                return "Wastage";
            case 500:
                return "Stock Adjustment";
            case -1:
                return "Saldo Akhir";
            default:
                return "";
        }
    }
}