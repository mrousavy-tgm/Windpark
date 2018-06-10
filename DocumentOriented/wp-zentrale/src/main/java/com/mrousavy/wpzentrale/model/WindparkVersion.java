package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WindparkVersion {
    private String id;

    @Id
    private String timeStamp;

    private Windpark windpark;

    public WindparkVersion(Windpark windpark, String timeStamp) {
        this.windpark = windpark;
        this.timeStamp = timeStamp;
    }

    public WindparkVersion() {
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public Windpark getWindpark() {
        return windpark;
    }
}
