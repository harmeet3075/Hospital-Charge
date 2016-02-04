package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 07-01-2016.
 */
public class AddServiciesData implements Parcelable{

    public String diagnosisName,discharges,avgChargesText,avgTotalText,avgMedicareText;



    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDischarges() {
        return discharges;
    }

    public void setDischarges(String discharges) {
        this.discharges = discharges;
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

        dest.writeString(diagnosisName);
        dest.writeString(discharges);
        dest.writeString(avgChargesText);
        dest.writeString(avgTotalText);
        dest.writeString(avgMedicareText);

    }

    public static final Creator<AddServiciesData> CREATOR = new Creator<AddServiciesData>() {

        public AddServiciesData createFromParcel(Parcel source) {

            AddServiciesData dh = new AddServiciesData();
            dh.diagnosisName=source.readString();
            dh.discharges=source.readString();
            dh.avgChargesText=source.readString();
            dh.avgTotalText=source.readString();
            dh.avgMedicareText=source.readString();

            return dh;

        }

        @Override
        public AddServiciesData[] newArray(int size) {
            return new AddServiciesData[size];
        }
    };
}
