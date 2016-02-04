package com.example.user.hospitalcharge.Model.Diagnosis;

/**
 * Created by user on 31-12-2015.
 */
public class DiagnosisElements {

    private DiagnosesHospitals diagnosesHospitals;
    private Hospitals hospitals;
    private Countries countries;
    private States states;
    private Diagnoses diagnoses;

    public DiagnosesHospitals getDiagnosesHospitals() {
        return diagnosesHospitals;
    }

    public void setDiagnosesHospitals(DiagnosesHospitals diagnosesHospitals) {
        this.diagnosesHospitals = diagnosesHospitals;
    }

    public Hospitals getHospitals() {
        return hospitals;
    }

    public void setHospitals(Hospitals hospitals) {
        this.hospitals = hospitals;
    }

    public Countries getCountries() {
        return countries;
    }

    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public Diagnoses getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Diagnoses diagnoses) {
        this.diagnoses = diagnoses;
    }
}
