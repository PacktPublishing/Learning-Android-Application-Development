package com.packt.rrafols.example.model;

import io.realm.RealmObject;

public class Fields extends RealmObject {
    private String name;
    private String price;
    private String symbol;
    private String ts;
    private String type;
    private String utctime;
    private String volume;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getTs() {
        return ts;
    }
    public void setTs(String ts) {
        this.ts = ts;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUtctime() {
        return utctime;
    }
    public void setUtctime(String utctime) {
        this.utctime = utctime;
    }
    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return name + ", " + price + ", " + symbol + ", " + ts + ", " + type + ", " + utctime + ", " + volume;
    }
}




