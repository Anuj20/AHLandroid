package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class Appointment {

    private String Name, AppDate, AppTime;


    public Appointment() {
    }

    public Appointment(String Name, String AppDate, String AppTime) {
        this.Name = Name;
        this.AppDate = AppDate;
        this.AppTime = AppTime;

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String AppDate) {
        this.AppDate = AppDate;
    }

    public String getAppTime() {
        return AppTime;
    }

    public void setAppTime(String AppTime) {
        this.AppTime = AppTime;
    }


}
