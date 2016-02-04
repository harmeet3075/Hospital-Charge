package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 07-01-2016.
 */
public class AdditionalData implements Parcelable {

    public String providerName,avgChargesText,avgTotalText,avgMedicareText;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getAvgChargesText() {
        return avgChargesText;
    }

    public void setAvgChargesText(String avgChargesText) {
        this.avgChargesText = avgChargesText;
    }

    public String getAvgTotalText() {
        return avgTotalText;
    }

    public void setAvgTotalText(String avgTotalText) {
        this.avgTotalText = avgTotalText;
    }

    public String getAvgMedicareText() {
        return avgMedicareText;
    }

    public void setAvgMedicareText(String avgMedicareText) {
        this.avgMedicareText = avgMedicareText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(providerName);
        dest.writeString(avgChargesText);
        dest.writeString(avgTotalText);
        dest.writeString(avgMedicareText);
    }

    public static final Creator<AdditionalData> CREATOR = new Creator<AdditionalData>() {

        public AdditionalData createFromParcel(Parcel source) {

            AdditionalData dh = new AdditionalData();
            dh.providerName=source.readString();
            dh.avgChargesText=source.readString();
            dh.avgTotalText=source.readString();
            dh.avgMedicareText=source.readString();
            return dh;

        }

        @Override
        public AdditionalData[] newArray(int size) {
            return new AdditionalData[size];
        }
    };
}
