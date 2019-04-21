package com.example.akshay.PollingApp.send_model;

import android.widget.CheckBox;

public class send {

    public send (String groupName) {
        this.groupName = groupName;
        this.checked=false;

    }

    private String groupName;
    private boolean checked;

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
