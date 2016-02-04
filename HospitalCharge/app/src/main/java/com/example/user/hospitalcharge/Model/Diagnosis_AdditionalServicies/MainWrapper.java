package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MainWrapper {

    public int response;
    public ArrayList<Diagnoseshospitalslist> Diagnoseshospitalslist;
    public Hospital Hospital;
    public State State;
    public double lat,lang;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public ArrayList<com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist> getDiagnoseshospitalslist() {
        return Diagnoseshospitalslist;
    }

    public void setDiagnoseshospitalslist(ArrayList<com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist> diagnoseshospitalslist) {
        Diagnoseshospitalslist = diagnoseshospitalslist;
    }

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospital getHospital() {
        return Hospital;
    }

    public void setHospital(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Hospital hospital) {
        Hospital = hospital;
    }

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.State getState() {
        return State;
    }

    public void setState(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.State state) {
        State = state;
    }
}
