package com.example.akshay.PollingApp.model;

import java.util.Comparator;

public class Sorter implements Comparator<Poll> {
        public int compare(Poll a,Poll b){
            return  a.getTimestamp().compareTo(b.getTimestamp());


        }
}
