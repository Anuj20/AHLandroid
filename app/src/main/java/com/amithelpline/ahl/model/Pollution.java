package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 29-04-2017.
 */

public class Pollution {
    private String VehicleNo, DueDate,Description;


    public Pollution() {
    }

    public Pollution(String VehicleNo, String DueDate,String Description) {
        this.VehicleNo = VehicleNo;
        this.DueDate = DueDate;
        this.Description=Description;


    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String VehicleNo) {
        this.VehicleNo = VehicleNo;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
