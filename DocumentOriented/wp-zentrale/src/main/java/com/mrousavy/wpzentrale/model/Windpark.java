package com.mrousavy.wpzentrale.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Windpark {
    @Id
    private String id;

    private ArrayList<Windrad> windrad;

    public Windpark(String id) {
        this.id = id;
        this.windrad = new ArrayList<>();
    }

    public Windpark() {
    }

    public void addWindrad(Windrad windrad){
        this.windrad.add(windrad);
    }

    public ArrayList<Windrad> getWindrad() {
        return windrad;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Windpark && ((Windpark) o).id.equals(this.id);
    }
}
