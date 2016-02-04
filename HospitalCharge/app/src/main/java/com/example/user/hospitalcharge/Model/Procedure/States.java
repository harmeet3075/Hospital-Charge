package com.example.user.hospitalcharge.Model.Procedure;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 31-12-2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class States implements Parcelable{


    public String id, code, name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(name);
    }

    public static final Creator<States> CREATOR = new Creator<States>() {

        public States createFromParcel(Parcel source) {

            States dh = new States();
            dh.id=source.readString();
            dh.code=source.readString();
            dh.name=source.readString();
            return dh;

        }

        @Override
        public States[] newArray(int size) {
            return new States[size];
        }
    };

}
