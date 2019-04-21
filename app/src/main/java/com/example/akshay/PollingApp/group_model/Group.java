package com.example.akshay.PollingApp.group_model;

public class Group {
    public   Group (String groupname){
        this.groupname = groupname;
    }
    private String groupname;

    public String getGroupname(){
        return  groupname;
    }
    public void setGroupname(String groupname){
        this.groupname=groupname;
    }
}
