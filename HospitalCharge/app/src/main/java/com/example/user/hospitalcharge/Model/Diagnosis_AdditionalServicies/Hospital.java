package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Hospital /*implements Parcelable*/ {

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospitals Hospitals ;

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospitals getHospitals() {
        return Hospitals;
    }

    public void setHospitals(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospitals hospitals) {
        Hospitals = hospitals;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedObject(Hospitals, flags);
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {

        public Hospital createFromParcel(Parcel source) {

            Hospital dh = new Hospital();
            dh.Hospitals=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospitals.CREATOR);
            return dh;

        }

        @Override
        public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospital[] newArray(int size) {
            return new com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospital[size];
        }
    };*/
}
