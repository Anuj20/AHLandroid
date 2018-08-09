package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 29-04-2017.
 */

public class Currency {
    private String Name, Rate;


    public Currency() {
    }

    public Currency(String Name, String Rate) {
        this.Name = Name;
        this.Rate = Rate;


    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String Rate) {
        this.Rate = Rate;
    }
}
