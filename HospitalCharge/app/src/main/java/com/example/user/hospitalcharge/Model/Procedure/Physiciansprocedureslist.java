package com.example.user.hospitalcharge.Model.Procedure;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 05-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Physiciansprocedureslist /*implements Parcelable*/ {

    public PhysiciansProcedures PhysiciansProcedures;
    public Procedures Procedures;
    public Physicians Physicians;
    public Categories Categories;
    public Countries  Countries;
    public States States;


    public com.example.user.hospitalcharge.Model.Procedure.PhysiciansProcedures getPhysiciansProcedures() {
        return PhysiciansProcedures;
    }

    public void setPhysiciansProcedures(com.example.user.hospitalcharge.Model.Procedure.PhysiciansProcedures physiciansProcedures) {
        PhysiciansProcedures = physiciansProcedures;
    }

    public com.example.user.hospitalcharge.Model.Procedure.Procedures getProcedures() {
        return Procedures;
    }

    public void setProcedures(com.example.user.hospitalcharge.Model.Procedure.Procedures procedures) {
        Procedures = procedures;
    }

    public com.example.user.hospitalcharge.Model.Procedure.Physicians getPhysicians() {
        return Physicians;
    }

    public void setPhysicians(com.example.user.hospitalcharge.Model.Procedure.Physicians physicians) {
        Physicians = physicians;
    }

    public com.example.user.hospitalcharge.Model.Procedure.Categories getCategories() {
        return Categories;
    }

    public void setCategories(com.example.user.hospitalcharge.Model.Procedure.Categories categories) {
        Categories = categories;
    }

    public com.example.user.hospitalcharge.Model.Procedure.Countries getCountries() {
        return Countries;
    }

    public void setCountries(com.example.user.hospitalcharge.Model.Procedure.Countries countries) {
        Countries = countries;
    }

    public com.example.user.hospitalcharge.Model.Procedure.States getStates() {
        return States;
    }

    public void setStates(com.example.user.hospitalcharge.Model.Procedure.States states) {
        States = states;
    }


    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dest.writeTypedObject(PhysiciansProcedures,flags);
            dest.writeTypedObject(Procedures,flags);
            dest.writeTypedObject(Physicians,flags);
            dest.writeTypedObject(Categories,flags);
            dest.writeTypedObject(Countries,flags);
            dest.writeTypedObject(States,flags);
        }
    }


    public static final Parcelable.Creator<Physiciansprocedureslist> CREATOR = new Creator<Physiciansprocedureslist>() {

        public Physiciansprocedureslist createFromParcel(Parcel source) {

            Physiciansprocedureslist dl = new Physiciansprocedureslist();

            dl.PhysiciansProcedures=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.PhysiciansProcedures.CREATOR);
            dl.Procedures=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.Procedures.CREATOR);
            dl.Physicians=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.Physicians.CREATOR);
            dl.Categories=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.Categories.CREATOR);
            dl.Countries=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.Countries.CREATOR);
            dl.States=source.readTypedObject(com.example.user.hospitalcharge.Model.Procedure.States.CREATOR);
            return dl;

        }

        @Override
        public Physiciansprocedureslist[] newArray(int size) {
            return new Physiciansprocedureslist[size];
        }

    };*/
}
