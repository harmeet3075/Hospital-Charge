package com.example.user.hospitalcharge.Model.Diagnosis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 31-12-2015.
 */
public class DiagnosesHospitals implements Parcelable {

    private String hospital_id, discharges, charge_amt, total_amt, medicare_amt;


    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getDischarges() {
        return discharges;
    }

    public void setDischarges(String discharges) {
        this.discharges = discharges;
    }

    public String getCharge_amt() {
        return charge_amt;
    }

    public void setCharge_amt(String charge_amt) {
        this.charge_amt = charge_amt;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
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

        dest.writeString(hospital_id);
        dest.writeString(discharges);
        dest.writeString(charge_amt);
        dest.writeString(total_amt);
        dest.writeString(medicare_amt);
    }


    public static final Parcelable.Creator<DiagnosesHospitals> CREATOR = new Creator<DiagnosesHospitals>() {

        public DiagnosesHospitals createFromParcel(Parcel source) {

            DiagnosesHospitals dh = new DiagnosesHospitals();
            dh.hospital_id=source.readString();
            dh.discharges=source.readString();
            dh.charge_amt=source.readString();
            dh.total_amt=source.readString();
            dh.medicare_amt=source.readString();
            return dh;

        }

        @Override
        public DiagnosesHospitals[] newArray(int size) {
            return new DiagnosesHospitals[size];
        }
    };
}
