package com.example.akshay.PollingApp.model;

import java.util.List;

public class Poll {

    public Poll(String groupName, String question, List<Opt> options, String timestamp, String expiryDate,String asker) {
        this.groupName = groupName;
        this.question = question;
        this.options = options;
        this.timestamp = timestamp;
        this.expiryDate = expiryDate;
        this.asker=asker;
    }

    private String groupName;
    private String question;
    private List<Opt> options;
    private String timestamp;
    private String expiryDate;

    public String getAsker() {
        return asker;
    }


    private  String asker;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Opt> getOptions() {
        return options;
    }

    public void setOptions(List<Opt> options) {
        this.options = options;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
