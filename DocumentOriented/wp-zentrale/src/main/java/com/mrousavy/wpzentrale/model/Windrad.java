package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Windrad {

    private String id;

    @Id
    private String timeStamp;

    private double power;

    private double blindpower;

    private double windspeed;

    private double rotationspeed;

    private double temperature;

    private double bladeposition;

    private double transfertime;

    public Windrad(String id, String timeStamp, double power, double blindpower, double windspeed, double rotationspeed, double temperature, double bladeposition, double transfertime) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.power = power;
        this.blindpower = blindpower;
        this.windspeed = windspeed;
        this.rotationspeed = rotationspeed;
        this.temperature = temperature;
        this.bladeposition = bladeposition;
        this.transfertime = transfertime;
    }

    public Windrad() {
    }

    public String getId() {
        return id;
    }

    public double getPower() {
        return power;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public double getBlindpower() {
        return blindpower;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public double getRotationspeed() {
        return rotationspeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getBladeposition() {
        return bladeposition;
    }

    public double getTransfertime() {
        return transfertime;
    }
}
