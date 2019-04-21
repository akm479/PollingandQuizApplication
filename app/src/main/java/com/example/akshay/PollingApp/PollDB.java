package com.example.akshay.PollingApp;

import java.util.ArrayList;
import java.util.Map;

public class PollDB {

    String Asker;
    String Questions;
    ArrayList<String> listofoptions = new ArrayList<>();
    String Askedtime;
    String Expirytime;

    public Map<String, String> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, String> responses) {
        this.responses = responses;
    }

    Map<String,String> responses;

    public String getAsker() {
        return Asker;
    }

    public void setAsker(String asker) {
        Asker = asker;
    }

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public ArrayList<String> getListofoptions() {
        return listofoptions;
    }

    public void setListofoptions(ArrayList<String> listofoptions) {
        this.listofoptions = listofoptions;
    }

    public String getAskedtime() {
        return Askedtime;
    }

    public void setAskedtime(String askedtime) {
        Askedtime = askedtime;
    }

    public String getExpirytime() {
        return Expirytime;
    }

    public void setExpirytime(String expirytime) {
        Expirytime = expirytime;
    }

    public PollDB() {
        this.Askedtime=null;
        this.Asker=null;
        this.Expirytime=null;
        this.listofoptions=null;
        this.Questions=null;
        this.responses=null;
    }

    public PollDB(String asker, String questions, ArrayList<String> listofoptions, String askedtime, String expirytime,Map<String,String >responses) {
        this.Asker = asker;
        this.Questions = questions;
        this.listofoptions = listofoptions;
        this.Askedtime = askedtime;
        this.Expirytime = expirytime;
        this.responses=responses;
    }
}
