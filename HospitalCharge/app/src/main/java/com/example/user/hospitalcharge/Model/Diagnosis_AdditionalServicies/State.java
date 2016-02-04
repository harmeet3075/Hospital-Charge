package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 06-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class State /*implements Parcelable*/ {

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.States States;

    public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.States getStates() {
        return States;
    }

    public void setStates(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.States states) {
        States = states;
    }


    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedObject(States, flags);
    }

    public static final Creator<State> CREATOR = new Creator<State>() {

        public State createFromParcel(Parcel source) {

            State dh = new State();
            dh.States=source.readTypedObject(com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.States.CREATOR);
            return dh;

        }

        @Override
        public com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.State[] newArray(int size) {
            return new com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.State[size];
        }
    };*/
}
