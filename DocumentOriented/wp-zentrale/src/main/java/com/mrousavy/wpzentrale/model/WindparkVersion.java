package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WindparkVersion {

    private String id;

    @Id
    private String timeStamp;

    private List<Windrad> windrads;

    public WindparkVersion(String id, String timeStamp) {
        this.id = id;
        this.windrads = new ArrayList<>();
        this.timeStamp = timeStamp;
    }

    public void addWindrad(Windrad windrad){
        this.windrads.add(windrad);
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public String getId() {
        return id;
    }

    public List<Windrad> getWindrads() {
        return windrads;
    }
}
