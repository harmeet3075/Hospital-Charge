package com.example.user.hospitalcharge.Model.Procedure;

/**
 * Created by user on 06-01-2016.
 */
public class ProcedureData {


    private String nameText,locText,phoneText,avgChargesText,avgTotalText,avgMedicareText,procedure_id,firstName,lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProcedure_id() {
        return procedure_id;
    }

    public void setProcedure_id(String procedure_id) {
        this.procedure_id = procedure_id;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getLocText() {
        return locText;
    }

    public void setLocText(String locText) {
        this.locText = locText;
    }

    public String getPhoneText() {
        return phoneText;
    }

    public void setPhoneText(String phoneText) {
        this.phoneText = phoneText;
    }

    public String getAvgChargesText() {
        return avgChargesText;
    }

    public void setAvgChargesText(String avgChargesText) {
        this.avgChargesText = avgChargesText;
    }

    public String getAvgTotalText() {
        return avgTotalText;
    }

    public void setAvgTotalText(String avgTotalText) {
        this.avgTotalText = avgTotalText;
    }

    public String getAvgMedicareText() {
        return avgMedicareText;
    }

    public void setAvgMedicareText(String avgMedicareText) {
        this.avgMedicareText = avgMedicareText;
    }
}
