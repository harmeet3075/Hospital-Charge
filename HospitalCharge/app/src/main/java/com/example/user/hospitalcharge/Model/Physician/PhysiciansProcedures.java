package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 05-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PhysiciansProcedures implements Parcelable {

    public String physician_id,total_amt,charge_amt,medicare_amt;


    public String getProcedure_id() {
        return physician_id;
    }

    public void setProcedure_id(String procedure_id) {
        this.physician_id = procedure_id;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getCharge_amt() {
        return charge_amt;
    }

    public void setCharge_amt(String charge_amt) {
        this.charge_amt = charge_amt;
    }

    public String getMedicare_amt() {
        return medicare_amt;
    }

    public void setMedicare_amt(String medicare_amt) {
        this.medicare_amt = medicare_amt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(physician_id);
        dest.writeString(total_amt);
        dest.writeString(charge_amt);
        dest.writeString(medicare_amt);
    }

    public static final Creator<PhysiciansProcedures> CREATOR = new Creator<PhysiciansProcedures>() {

        public PhysiciansProcedures createFromParcel(Parcel source) {

            PhysiciansProcedures dh = new PhysiciansProcedures();
            dh.physician_id=source.readString();
            dh.total_amt=source.readString();
            dh.charge_amt=source.readString();
            dh.medicare_amt=source.readString();
            return dh;

        }

        @Override
        public PhysiciansProcedures[] newArray(int size) {
            return new PhysiciansProcedures[size];
        }
    };

}
