package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class PolicyGeneral {

    private String Id,VehicleNo, PreimumDate, PreimumAmt, PolicyNo,PolicyType;


    public PolicyGeneral() {
    }

    public PolicyGeneral(String Id,String VehicleNo, String PreimumDate, String PreimumAmt, String PolicyNo, String PolicyType) {

       this.Id=Id;
        this.VehicleNo = VehicleNo;
        this.PreimumDate = PreimumDate;
        this.PreimumAmt = PreimumAmt;
        this.PolicyNo = PolicyNo;
        this.PolicyType = PolicyType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }


    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String VehicleNo) {
        this.VehicleNo = VehicleNo;
    }

    public String getPreimumDate() {
        return PreimumDate;
    }

    public void setPreimumDate(String PreimumDate) {
        this.PreimumDate = PreimumDate;
    }

    public String getPreimumAmt() {
        return PreimumAmt;
    }

    public void setPreimumAmt(String PreimumAmt) {
        this.PreimumAmt = PreimumAmt;
    }

    public String getPolicyNo() {
        return PolicyNo;
    }

    public void setPolicyNo(String PolicyNo) {
        this.PolicyNo = PolicyNo;
    }


    public String getPolicyType() {
        return PolicyType;
    }

    public void setPolicyType(String PolicyType) {
        this.PolicyType = PolicyType;
    }


}
