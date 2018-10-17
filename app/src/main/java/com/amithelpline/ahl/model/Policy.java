package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class Policy {

    private String PolicyId, Name,PremiumStartDate, PreimumDate, PreimumAmt, PolicyNo, PolicyMode;


    public Policy() {
    }

    public Policy(String PolicyId, String Name,String PremiumStartDate, String PreimumDate, String PreimumAmt, String PolicyNo, String PolicyMode) {
        this.PolicyId = PolicyId;
        this.Name = Name;
        this.PremiumStartDate=PremiumStartDate;
        this.PreimumDate = PreimumDate;
        this.PreimumAmt = PreimumAmt;
        this.PolicyNo = PolicyNo;
        this.PolicyMode = PolicyMode;

    }



    public String getPolicyId() {
        return PolicyId;
    }

    public void setPolicyId(String PolicyId) {
        this.PolicyId = PolicyId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPremiumStartDate() {
        return PremiumStartDate;
    }

    public void setPremiumStartDate(String premiumStartDate) {
        PremiumStartDate = premiumStartDate;
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

    public String getPolicyMode() {
        return PolicyMode;
    }

    public void setPolicyMode(String PolicyMode) {
        this.PolicyMode = PolicyMode;
    }


}
