package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class Ipo {

    private String Name, MinAmount, OpenDate, LastDate, PriceBand, LotSize;


    public Ipo() {
    }

    public Ipo(String Name, String MinAmount, String OpenDate, String LastDate, String PriceBand, String LotSize) {
        this.Name = Name;
        this.MinAmount = MinAmount;
        this.OpenDate = OpenDate;
        this.LastDate = LastDate;
        this.PriceBand = PriceBand;
        this.LotSize = LotSize;

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(String MinAmount) {
        this.MinAmount = MinAmount;
    }

    public String getOpenDate() {
        return OpenDate;
    }

    public void setOpenDate(String OpenDate) {
        this.OpenDate = OpenDate;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String LastDate) {
        this.LastDate = LastDate;
    }

    public String getPriceBand() {
        return PriceBand;
    }

    public void setPriceBand(String PriceBand) {
        this.PriceBand = PriceBand;
    }

    public String getLotSize() {
        return LotSize;
    }

    public void setLotSize(String LotSize) {
        this.LotSize = LotSize;
    }


}
