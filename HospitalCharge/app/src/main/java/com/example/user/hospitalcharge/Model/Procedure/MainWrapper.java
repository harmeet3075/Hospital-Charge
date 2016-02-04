package com.example.user.hospitalcharge.Model.Procedure;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by user on 05-01-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MainWrapper {

    public String Physiciansname;
    public ArrayList<Physiciansprocedureslist> Physiciansprocedureslist;

    public String getPhysiciansname() {
        return Physiciansname;
    }

    public void setPhysiciansname(String physiciansname) {
        Physiciansname = physiciansname;
    }

    public ArrayList<com.example.user.hospitalcharge.Model.Procedure.Physiciansprocedureslist> getPhysiciansprocedureslist() {
        return Physiciansprocedureslist;
    }

    public void setPhysiciansprocedureslist(ArrayList<com.example.user.hospitalcharge.Model.Procedure.Physiciansprocedureslist> physiciansprocedureslist) {
        Physiciansprocedureslist = physiciansprocedureslist;
    }
}
