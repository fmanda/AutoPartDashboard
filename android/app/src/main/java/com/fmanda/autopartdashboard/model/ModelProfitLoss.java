package com.fmanda.autopartdashboard.model;

import java.util.ArrayList;
import java.util.List;

public class ModelProfitLoss extends BaseModel {
    @TableField
    private String projectcode;
    @TableField
    private int monthperiod;
    @TableField
    private int yearperiod;
    @TableField
    private String reportname;
    @TableField
    private String groupname;
    @TableField
    private Double reportval;
    @TableField
    private int reportindex;
    @TableField
    private int reportgroup;

    public ModelProfitLoss() {

    }

    public Boolean isGroup(){
        return reportindex == reportgroup;
    }

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

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Double getReportval() {
        return reportval;
    }

    public void setReportval(Double reportval) {
        this.reportval = reportval;
    }

    public int getReportindex() {
        return reportindex;
    }

    public void setReportindex(int reportindex) {
        this.reportindex = reportindex;
    }

    public int getReportgroup() {
        return reportgroup;
    }

    public void setReportgroup(int reportgroup) {
        this.reportgroup = reportgroup;
    }

    public static List<ModelProfitLoss> getGroups(List<ModelProfitLoss> profits){
        List<ModelProfitLoss> groups = new ArrayList<ModelProfitLoss>();
        ModelProfitLoss group;
        for (ModelProfitLoss profit : profits){
            group = null;
            for (ModelProfitLoss igroup : groups){
                if (igroup.getReportgroup() == profit.getReportgroup()){
                    group = igroup;
                    break;
                }
            }
            if (group == null){
                group = new ModelProfitLoss();
                group.setMonthperiod(profit.getMonthperiod());
                group.setYearperiod(profit.getYearperiod());
                group.setGroupname(profit.getGroupname());
                group.setReportgroup(profit.getReportgroup());
                group.setReportindex(group.getReportgroup());
                group.setReportname(group.getReportname());
                group.setReportval(0.0);
                groups.add(group);
            }
            group.setReportval(group.getReportval() + profit.getReportval()); //getTotal
        };
        return groups;
    }

    public static List<ModelProfitLoss> getDetails(List<ModelProfitLoss> profits, int groupIndex){
        List<ModelProfitLoss> details = new ArrayList<ModelProfitLoss>();
        for (ModelProfitLoss profit : profits){
            if (profit.getReportgroup() == groupIndex){
                details.add(profit);
            }
        };
        return details;
    }
}
