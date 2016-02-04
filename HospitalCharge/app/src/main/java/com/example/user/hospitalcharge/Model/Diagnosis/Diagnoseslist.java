package com.example.user.hospitalcharge.Model.Diagnosis;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 31-12-2015.
 */
public class Diagnoseslist  /*implements Parcelable*/ {

    public DiagnosesHospitals DiagnosesHospitals;
    public Hospitals Hospitals;
    public Countries Countries;
    public States States;
    public Diagnoses Diagnoses;

    public DiagnosesHospitals getDiagnosesHospitals() {
        return DiagnosesHospitals;
    }

    public void setDiagnosesHospitals(DiagnosesHospitals diagnosesHospitals) {
        this.DiagnosesHospitals = diagnosesHospitals;
    }

    public Hospitals getHospitals() {
        return Hospitals;
    }

    public void setHospitals(Hospitals hospitals) {
        this.Hospitals = hospitals;
    }

    public Countries getCountries() {
        return Countries;
    }

    public void setCountries(Countries countries) {
        this.Countries = countries;
    }

    public States getStates() {
        return States;
    }

    public void setStates(States states) {
        this.States = states;
    }

    public Diagnoses getDiagnoses() {
        return Diagnoses;
    }

    public void setDiagnoses(Diagnoses diagnoses) {
        this.Diagnoses = diagnoses;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dest.writeTypedObject(DiagnosesHospitals,flags);
            dest.writeTypedObject(Hospitals,flags);
            dest.writeTypedObject(Countries,flags);
            dest.writeTypedObject(States,flags);
            dest.writeTypedObject(Diagnoses,flags);
        }
    }


    public static final Parcelable.Creator<Diagnoseslist> CREATOR = new Creator<Diagnoseslist>() {

        public Diagnoseslist createFromParcel(Parcel source) {

            Diagnoseslist dl = new Diagnoseslist();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dl.DiagnosesHospitals=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis.DiagnosesHospitals.CREATOR);
                dl.Hospitals=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis.Hospitals.CREATOR);
                dl.Countries=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis.Countries.CREATOR);
                dl.States=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis.States.CREATOR);
                dl.Diagnoses=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis.Diagnoses.CREATOR);
            }

            return dl;

        }

        @Override
        public Diagnoseslist[] newArray(int size) {
            return new Diagnoseslist[size];
        }

    };*/
}

