package com.example.user.hospitalcharge.Model.Diagnosis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 31-12-2015.
 */
public class Diagnoses implements Parcelable{


            private String id,
            diagnosis_code,
            diagnosis_name,
            created,
            modified,
            status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagnosis_code() {
        return diagnosis_code;
    }

    public void setDiagnosis_code(String diagnosis_code) {
        this.diagnosis_code = diagnosis_code;
    }

    public String getDiagnosis_name() {
        return diagnosis_name;
    }

    public void setDiagnosis_name(String diagnosis_name) {
        this.diagnosis_name = diagnosis_name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
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
        dest.writeString(diagnosis_code);
        dest.writeString(diagnosis_name);
        dest.writeString(created);
        dest.writeString(modified);
        dest.writeString(status);
    }

    public static final Parcelable.Creator<Diagnoses> CREATOR = new Creator<Diagnoses>() {

        public Diagnoses createFromParcel(Parcel source) {

            Diagnoses dh = new Diagnoses();
            dh.id=source.readString();
            dh.diagnosis_code=source.readString();
            dh.diagnosis_name=source.readString();
            dh.created=source.readString();
            dh.modified=source.readString();
            dh.status=source.readString();
            return dh;

        }

        @Override
        public Diagnoses[] newArray(int size) {
            return new Diagnoses[size];
        }
    };
}
