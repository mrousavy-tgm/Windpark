package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Windrad {
    @Id
    private String id;

    private String power;

    private String blindpower;

    private String windspeed;

    private String rotationspeed;

    private String temperature;

    private String bladeposition;

    private String transfertime;

    public Windrad(String id, String power, String blindpower, String windspeed, String rotationspeed, String temperature, String bladeposition, String transfertime) {
        this.id = id;
        this.power = power;
        this.blindpower = blindpower;
        this.windspeed = windspeed;
        this.rotationspeed = rotationspeed;
        this.temperature = temperature;
        this.bladeposition = bladeposition;
        this.transfertime = transfertime;
    }

    public Windrad(String id) {
        this.id = id;
    }

    public Windrad() {
    }

    public String getId() {
        return id;
    }

    public String getPower() {
        return power;
    }

    public String getBlindpower() {
        return blindpower;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getRotationspeed() {
        return rotationspeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getBladeposition() {
        return bladeposition;
    }

    public String getTransfertime() {
        return transfertime;
    }
}
