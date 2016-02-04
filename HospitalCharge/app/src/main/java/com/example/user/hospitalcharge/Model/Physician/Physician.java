package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 11-01-2016.
 */
public class Physician /*implements Parcelable*/ {

    public Physicians Physicians;

    public com.example.user.hospitalcharge.Model.Physician.Physicians getPhysicians() {
        return Physicians;
    }

    public void setPhysicians(com.example.user.hospitalcharge.Model.Physician.Physicians physicians) {
        Physicians = physicians;
    }


    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedObject(Physicians, flags);
    }

    public static final Creator<Physician> CREATOR = new Creator<Physician>() {

        public Physician createFromParcel(Parcel source) {

            Physician dh = new Physician();
            dh.Physicians=source.readTypedObject(com.example.user.hospitalcharge.Model.Physician.Physicians.CREATOR);
            return dh;

        }

        @Override
        public Physician[] newArray(int size) {
            return new Physician[size];
        }
    };*/
}
