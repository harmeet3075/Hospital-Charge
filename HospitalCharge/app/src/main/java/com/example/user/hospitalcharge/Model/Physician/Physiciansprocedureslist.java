package com.example.user.hospitalcharge.Model.Physician;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Physiciansprocedureslist /*implements Parcelable*/ {

    public PhysiciansProcedures PhysiciansProcedures;
    public Procedures Procedures;

    public com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures getPhysiciansProcedures() {
        return PhysiciansProcedures;
    }

    public void setPhysiciansProcedures(com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures physiciansProcedures) {
        PhysiciansProcedures = physiciansProcedures;
    }

    public com.example.user.hospitalcharge.Model.Physician.Procedures getProcedures() {
        return Procedures;
    }

    public void setProcedures(com.example.user.hospitalcharge.Model.Physician.Procedures procedures) {
        Procedures = procedures;
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

        }
    }


    public static final Parcelable.Creator<Physiciansprocedureslist> CREATOR = new Creator<Physiciansprocedureslist>() {

        public Physiciansprocedureslist createFromParcel(Parcel source) {

            Physiciansprocedureslist dl = new Physiciansprocedureslist();

            dl.PhysiciansProcedures=source.readTypedObject(com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures.CREATOR);
            dl.Procedures=source.readTypedObject(com.example.user.hospitalcharge.Model.Physician.Procedures.CREATOR);

            return dl;

        }

        @Override
        public Physiciansprocedureslist[] newArray(int size) {
            return new Physiciansprocedureslist[size];
        }

    };*/

}
