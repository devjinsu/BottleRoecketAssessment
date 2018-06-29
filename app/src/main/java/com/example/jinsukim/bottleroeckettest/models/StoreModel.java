package com.example.jinsukim.bottleroeckettest.models;

import java.io.Serializable;

//TODO: implement as Parcelable
public class StoreModel implements Serializable{
    int mID;
    String mAddress, mCity, mState, mZip;
    String mName;
    String mPhone;
    double mLat,mLng;
    String mLogoURL;

    public StoreModel(int ID, String address, String city, String state, String zip, String name, String phone, double lat, double lng) {
        mID = ID;
        mAddress = address;
        mCity = city;
        mState = state;
        mZip = zip;
        mName = name;
        mPhone = phone;
        mLat = lat;
        mLng = lng;
    }

    public StoreModel(int ID, String address, String city, String state, String zip, String name, String phone, double lat, double lng, String logoURL) {
        mID = ID;
        mAddress = address;
        mCity = city;
        mState = state;
        mZip = zip;
        mName = name;
        mPhone = phone;
        mLat = lat;
        mLng = lng;
        mLogoURL = logoURL;
    }

    public int getID() {
        return mID;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }

    public String getZip() {
        return mZip;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }

    public String getLogoURL() {
        return mLogoURL;
    }

    public void setLogoURL(String logoURL) {
        mLogoURL = logoURL;
    }

    @Override
    public String toString() {
        return "StoreModel{" +
                "mID=" + mID +
                ", mAddress='" + mAddress + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mZip='" + mZip + '\'' +
                ", mName='" + mName + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mLat=" + mLat +
                ", mLng=" + mLng +
                ", mLogoURL='" + mLogoURL + '\'' +
                '}';
    }
}
