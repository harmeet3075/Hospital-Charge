package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MainWrapper  {


    public int response;
    public ArrayList<Physiciansprocedureslist> Physiciansprocedureslist;
    public ProviderCategory ProviderCategory;
    public State State;
    public double lat,lang;
    public Physician Physician;

    public com.example.user.hospitalcharge.Model.Physician.Physician getPhysician() {
        return Physician;
    }

    public void setPhysician(com.example.user.hospitalcharge.Model.Physician.Physician physician) {
        Physician = physician;
    }

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

    public ArrayList<com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist> getPhysiciansprocedureslist() {
        return Physiciansprocedureslist;
    }

    public void setPhysiciansprocedureslist(ArrayList<com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist> physiciansprocedureslist) {
        Physiciansprocedureslist = physiciansprocedureslist;
    }

    public com.example.user.hospitalcharge.Model.Physician.ProviderCategory getProviderCategory() {
        return ProviderCategory;
    }

    public void setProviderCategory(com.example.user.hospitalcharge.Model.Physician.ProviderCategory providerCategory) {
        ProviderCategory = providerCategory;
    }

    public com.example.user.hospitalcharge.Model.Physician.State getState() {
        return State;
    }

    public void setState(com.example.user.hospitalcharge.Model.Physician.State state) {
        State = state;
    }
}
