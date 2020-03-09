package com.example.veriparkapp.model.detail;

import com.example.veriparkapp.model.handshake.Status;

import java.util.List;

public class DetailModel {

    private boolean isDown;
    private boolean isUp;
    private double bid;
    private double channge;
    private int count;
    private double difference;
    private double offer;
    private double highest;
    private double lowest;
    private double maximum;
    private double minumum;
    private double price;
    private double volume;
    private String symbol;
    private List<GraphicDataModel> graphicData;
    private Status status;

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getChannge() {
        return channge;
    }

    public void setChannge(double channge) {
        this.channge = channge;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public double getHighest() {
        return highest;
    }

    public void setHighest(double highest) {
        this.highest = highest;
    }

    public double getLowest() {
        return lowest;
    }

    public void setLowest(double lowest) {
        this.lowest = lowest;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getMinumum() {
        return minumum;
    }

    public void setMinumum(double minumum) {
        this.minumum = minumum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<GraphicDataModel> getGraphicData() {
        return graphicData;
    }

    public void setGraphicData(List<GraphicDataModel> graphicData) {
        this.graphicData = graphicData;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
