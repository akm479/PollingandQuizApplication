package com.example.akshay.PollingApp;

import com.example.akshay.PollingApp.model.Poll;
import com.example.akshay.PollingApp.PollDB;
import java.util.ArrayList;

public class GroupDB {
    String gid;
    ArrayList<String> userlist = new ArrayList<>();
    ArrayList<PollDB> listofpolls = new ArrayList<>();
//    String groupcode;

    public GroupDB() {
        this.gid = null;
        userlist = null;
        listofpolls = null;
//        groupcode = null;
    }

    public GroupDB(String gid, ArrayList<String> userlist, ArrayList<PollDB> listofpolls) {
        this.gid = gid;
        this.userlist = userlist;
        this.listofpolls = listofpolls;
//        this.groupcode=groupcode;
    }

//    public String getGroupcode() {
//        return groupcode;
//    }
//
//    public void setGroupcode(String groupcode) {
//        this.groupcode = groupcode;
//    }

    public String getGid() {
        return gid;
    }

    public ArrayList<String> getUserlist() {
        return userlist;
    }

    public ArrayList<PollDB> getListofpolls() {
        return listofpolls;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setUserlist(ArrayList<String> userlist) {
        this.userlist = userlist;
    }

    public void setListofpolls(ArrayList<PollDB> listofpolls) {
        this.listofpolls = listofpolls;
    }
}
