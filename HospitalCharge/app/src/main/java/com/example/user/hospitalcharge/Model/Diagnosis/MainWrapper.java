package com.example.user.hospitalcharge.Model.Diagnosis;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by user on 31-12-2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MainWrapper {

    public String Diagnosisname;

    public ArrayList<Diagnoseslist> getDiagnosisList() {
        return Diagnoseslist;
    }

    public void setDiagnosisList(ArrayList<Diagnoseslist> diagnoseslist) {
        this.Diagnoseslist = diagnoseslist;
    }

    public ArrayList<Diagnoseslist>  Diagnoseslist;
    public String getDiagnosisname() {
        return Diagnosisname;
    }

    public void setDiagnosisname(String diagnosisname) {
        Diagnosisname = diagnosisname;
    }

}
