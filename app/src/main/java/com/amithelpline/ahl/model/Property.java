package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 28-04-2017.
 */

public class Property {

    private String PropertyID, Name, Address, Address1, City, Size, Rent, UserId;

    public Property(String PropertyID,String Name, String Address, String Address1, String City, String Size, String Rent, String UserId) {
        this.PropertyID=PropertyID;
        this.Name = Name;
        this.Address = Address;
        this.Address1 = Address1;
        this.City = City;
        this.Size = Size;
        this.Rent = Rent;
        this.UserId = UserId;
    }
    public String getPropertyID(){
        return PropertyID;
    }
    public String setPropertyID(String PropertyID){
        return PropertyID;
    }
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCity() {
        return City;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getSize() {
        return Size;
    }

    public void setRent(String Rent) {
        this.Rent = Rent;
    }

    public String getRent() {
        return Rent;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserId() {
        return UserId;
    }


}
