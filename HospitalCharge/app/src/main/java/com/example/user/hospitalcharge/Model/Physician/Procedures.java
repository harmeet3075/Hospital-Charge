package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 05-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Procedures implements Parcelable {

    public String id,procedure_code,procedure_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcedure_code() {
        return procedure_code;
    }

    public void setProcedure_code(String procedure_code) {
        this.procedure_code = procedure_code;
    }

    public String getProcedure_name() {
        return procedure_name;
    }

    public void setProcedure_name(String procedure_name) {
        this.procedure_name = procedure_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(procedure_code);
        dest.writeString(procedure_name);
    }

    public static final Creator<Procedures> CREATOR = new Creator<Procedures>() {

        public Procedures createFromParcel(Parcel source) {

            Procedures dh = new Procedures();
            dh.id=source.readString();
            dh.procedure_code=source.readString();
            dh.procedure_name=source.readString();
            return dh;

        }

        @Override
        public Procedures[] newArray(int size) {
            return new Procedures[size];
        }
    };
}
