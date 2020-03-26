package com.fmanda.autopartdashboard.model;

public class ModelUsers extends BaseModel {
    @TableField
    private String username;
    @TableField
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ModelUsers(){
    }

}