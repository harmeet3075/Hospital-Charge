package com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by user on 31-12-2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Hospitals implements Parcelable{

    private String id, hospital_code,
            hospital_name, street_address1,
            street_address2, phone, email, city,
            state_code, zip_code,
            country_code, license_no,
            verified, created_by,
            created, modified, status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospital_code() {
        return hospital_code;
    }

    public void setHospital_code(String hospital_code) {
        this.hospital_code = hospital_code;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getStreet_address1() {
        return street_address1;
    }

    public void setStreet_address1(String street_address1) {
        this.street_address1 = street_address1;
    }

    public String getStreet_address2() {
        return street_address2;
    }

    public void setStreet_address2(String street_address2) {
        this.street_address2 = street_address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
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
        dest.writeString(hospital_code);
        dest.writeString(hospital_name);
        dest.writeString(street_address1);
        dest.writeString(street_address2);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(city);
        dest.writeString(state_code);
        dest.writeString(zip_code);
        dest.writeString(country_code);
        dest.writeString(license_no);
        dest.writeString(verified);
        dest.writeString(created_by);
        dest.writeString(created);
        dest.writeString(modified);
        dest.writeString(status);

    }
    public static final Creator<Hospitals> CREATOR = new Creator<Hospitals>() {

        public Hospitals createFromParcel(Parcel source) {

            Hospitals dh = new Hospitals();
            dh.id=source.readString();
            dh.hospital_code=source.readString();
            dh.hospital_name=source.readString();
            dh.street_address1=source.readString();
            dh.street_address2=source.readString();
            dh.phone=source.readString();
            dh.email=source.readString();
            dh.city=source.readString();
            dh.state_code=source.readString();
            dh.zip_code=source.readString();
            dh.country_code=source.readString();
            dh.license_no=source.readString();
            dh.verified=source.readString();
            dh.created_by=source.readString();
            dh.created=source.readString();
            dh.modified=source.readString();
            dh.status=source.readString();
            return dh;

        }

        @Override
        public Hospitals[] newArray(int size) {
            return new Hospitals[size];
        }
    };
}
