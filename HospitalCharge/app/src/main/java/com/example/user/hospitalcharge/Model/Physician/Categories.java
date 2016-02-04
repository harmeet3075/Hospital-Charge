package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 05-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Categories implements Parcelable {

    public String id,category_name,status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(category_name);
        dest.writeString(status);
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {

        public Categories createFromParcel(Parcel source) {

            Categories dh = new Categories();
            dh.id=source.readString();
            dh.category_name=source.readString();
            dh.status=source.readString();
            return dh;

        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}
