package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Windpark {
    @Id
    private String id;

    private String latestTimestamp;

    private ArrayList<WindparkVersion> versions;

    public Windpark(String id) {
        this.id = id;
        this.versions = new ArrayList<>();
    }

    public void addVersion(WindparkVersion version){
        this.versions.add(version);
        this.latestTimestamp = version.getTimeStamp();
    }

    public String getId() {
        return id;
    }

    public String getLatestTimestamp() {
        return latestTimestamp;
    }

    public ArrayList<WindparkVersion> getVersions() {
        return versions;
    }

    public WindparkVersion getLatestWindpark(){
        for(WindparkVersion windpark: this.versions){
            if(windpark.getTimeStamp().equals(this.latestTimestamp)){
                return windpark;
            }
        }
        return null;
    }
}
