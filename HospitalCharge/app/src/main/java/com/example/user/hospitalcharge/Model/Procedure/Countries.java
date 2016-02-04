package com.example.user.hospitalcharge.Model.Procedure;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 31-12-2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Countries implements Parcelable{


    public String id,
            code,
            name,
            callingCode;

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

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
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
        dest.writeString(callingCode);
    }

    public static final Creator<Countries> CREATOR = new Creator<Countries>() {

        public Countries createFromParcel(Parcel source) {

            Countries dh = new Countries();
            dh.id=source.readString();
            dh.code=source.readString();
            dh.name=source.readString();
            return dh;

        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };
}
