package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Diagnoseshospitalslist /*implements Parcelable*/ {

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.DiagnosesHospitals DiagnosesHospitals;
    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoses Diagnoses;

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.DiagnosesHospitals getDiagnosesHospitals() {
        return DiagnosesHospitals;
    }

    public void setDiagnosesHospitals(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.DiagnosesHospitals diagnosesHospitals) {
        DiagnosesHospitals = diagnosesHospitals;
    }

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoses getDiagnoses() {
        return Diagnoses;
    }

    public void setDiagnoses(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoses diagnoses) {
        Diagnoses = diagnoses;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


            dest.writeTypedObject(DiagnosesHospitals,flags);
            dest.writeTypedObject(Diagnoses,flags);

    }


    public static final Parcelable.Creator<Diagnoseshospitalslist> CREATOR = new Creator<Diagnoseshospitalslist>() {

        public Diagnoseshospitalslist createFromParcel(Parcel source) {

            Diagnoseshospitalslist dl = new Diagnoseshospitalslist();

            dl.DiagnosesHospitals=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.DiagnosesHospitals.CREATOR);
            dl.Diagnoses=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoses.CREATOR);
            return dl;

        }

        @Override
        public Diagnoseshospitalslist[] newArray(int size) {
            return new Diagnoseshospitalslist[size];
        }

    };*/
}
