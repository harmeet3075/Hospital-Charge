package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProviderCategory /*implements Parcelable*/ {


    public Categories Categories;

    public com.example.user.hospitalcharge.Model.Physician.Categories getCategories() {
        return Categories;
    }

    public void setCategories(com.example.user.hospitalcharge.Model.Physician.Categories categories) {
        Categories = categories;
    }


    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedObject(Categories, flags);
    }

    public static final Creator<ProviderCategory> CREATOR = new Creator<ProviderCategory>() {

        public ProviderCategory createFromParcel(Parcel source) {

            ProviderCategory dh = new ProviderCategory();
            dh.Categories=source.readTypedObject(com.example.user.hospitalcharge.Model.Physician.Categories.CREATOR);
            return dh;

        }

        @Override
        public ProviderCategory[] newArray(int size) {
            return new ProviderCategory[size];
        }
    };*/
}
