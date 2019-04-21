package com.example.akshay.PollingApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDB {
String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String rollno) {
        Rollno = rollno;
    }

    String password;
String Rollno;

    public UserDB(String username, ArrayList<String> listofgroups,String password,String rollno) {
        this.username = username;
        this.listofgroups = listofgroups;
        this.Rollno=rollno;
        this.password=password;
    }
    public UserDB(){
        this.listofgroups = null;
        this.username = null;
        this.password=null;
        this.Rollno=null;
    }
    //    String[] grouplist  = new String[100];
    ArrayList<String> listofgroups = new ArrayList<>();
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getListofgroups() {
        return listofgroups;
    }

    public void setListofgroups(ArrayList<String> listofgroups) {
        this.listofgroups = listofgroups;
    }

    public UserDB(String username ) {
        this.username = username;

    }
}
