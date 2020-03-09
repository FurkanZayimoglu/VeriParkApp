package com.example.veriparkapp.model.list;


import com.example.veriparkapp.model.handshake.Status;

import java.util.ArrayList;

public class ListModel {
   private  ArrayList<Stocks> stocks;
   private  Status status;

    public ArrayList<Stocks> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stocks> stocks) {
        this.stocks = stocks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
