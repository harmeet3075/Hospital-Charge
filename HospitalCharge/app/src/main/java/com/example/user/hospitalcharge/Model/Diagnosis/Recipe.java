package com.example.user.hospitalcharge.Model.Diagnosis;

/**
 * Created by user on 04-01-2016.
 */
public class Recipe {

    public String name;
    public String id;
    public String recipe;
    public String image;
    public Countries Countries;
    public States States;

    public com.example.user.hospitalcharge.Model.Diagnosis.Countries getCountries() {
        return Countries;
    }

    public void setCountries(com.example.user.hospitalcharge.Model.Diagnosis.Countries countries) {
        Countries = countries;
    }

    public com.example.user.hospitalcharge.Model.Diagnosis.States getStates() {
        return States;
    }

    public void setStates(com.example.user.hospitalcharge.Model.Diagnosis.States states) {
        States = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
