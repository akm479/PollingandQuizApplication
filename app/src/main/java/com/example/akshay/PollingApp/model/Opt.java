package com.example.akshay.PollingApp.model;

public class Opt {
    public Opt(boolean checkBox, String option_text) {
        this.checkBox = checkBox;
        this.option_text = option_text;
    }

    private boolean checkBox;
    private String option_text;

    public boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }

    public String getOption_text() {
        return option_text;
    }
}
