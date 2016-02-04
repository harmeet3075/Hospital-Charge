package com.example.user.hospitalcharge.Model.Physician;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 11-01-2016.
 */
public class Physicians implements Parcelable{

    public String id,physician_code,physician_fname,physician_lname,
            gender,credentials,street_address1,street_address2,city,
            state_code,zip_code,country_code,license_no,category_id,
            phone,email,created_by,created,status,scrappstatus,
            modified,physician_images,latitude,longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhysician_code() {
        return physician_code;
    }

    public void setPhysician_code(String physician_code) {
        this.physician_code = physician_code;
    }

    public String getPhysician_fname() {
        return physician_fname;
    }

    public void setPhysician_fname(String physician_fname) {
        this.physician_fname = physician_fname;
    }

    public String getPhysician_lname() {
        return physician_lname;
    }

    public void setPhysician_lname(String physician_lname) {
        this.physician_lname = physician_lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScrappstatus() {
        return scrappstatus;
    }

    public void setScrappstatus(String scrappstatus) {
        this.scrappstatus = scrappstatus;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPhysician_images() {
        return physician_images;
    }

    public void setPhysician_images(String physician_images) {
        this.physician_images = physician_images;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean verified;
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(physician_code);
        dest.writeString(physician_fname);
        dest.writeString(physician_lname);
        dest.writeString(gender);
        dest.writeString(credentials);
        dest.writeString(street_address1);
        dest.writeString(street_address2);
        dest.writeString(city);
        dest.writeString(state_code);
        dest.writeString(zip_code);
        dest.writeString(country_code);
        dest.writeString(license_no);
        dest.writeString(category_id);
        dest.writeString(String.valueOf(verified));
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(created_by);
        dest.writeString(created);
        dest.writeString(status);
        dest.writeString(scrappstatus);
        dest.writeString(physician_images);
        dest.writeString(latitude);
        dest.writeString(longitude);

    }

    public static final Creator<Physicians> CREATOR = new Creator<Physicians>() {

        public Physicians createFromParcel(Parcel source) {



            Physicians dh = new Physicians();
            dh.id=source.readString();
            dh.physician_code=source.readString();
            dh.physician_fname=source.readString();
            dh.physician_lname=source.readString();
            dh.gender=source.readString();
            dh.credentials=source.readString();
            dh.street_address1=source.readString();
            dh.street_address2=source.readString();
            dh.city=source.readString();
            dh.state_code=source.readString();
            dh.zip_code=source.readString();
            dh.country_code=source.readString();
            dh.license_no=source.readString();
            dh.category_id=source.readString();
            dh.verified=Boolean.parseBoolean(source.readString());
            dh.phone=source.readString();
            dh.email=source.readString();
            dh.created_by=source.readString();
            dh.created=source.readString();
            dh.status=source.readString();
            dh.scrappstatus=source.readString();
            dh.modified=source.readString();
            dh.physician_images=source.readString();
            dh.latitude=source.readString();
            dh.longitude=source.readString();
            return dh;

        }

        @Override
        public Physicians[] newArray(int size) {
            return new Physicians[size];
        }
    };
}
