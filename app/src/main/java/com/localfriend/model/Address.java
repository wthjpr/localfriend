package com.localfriend.model;

import java.io.Serializable;

/**
 * Created by SONI on 10/23/2017.
 */

public class Address implements Serializable{

    private final static long serialVersionUID = 81446638563254L;

    private String addID;
    private String addName;
    private String addContactNo;
    private String addEmailID;
    private String addCompany;
    private String addZipCode;
    private String addDate;
    private String addCity;
    private String addSatate;
    private String addCountry;
    private String addDetails;
    private String addDetails1;
    private String addDetails2;
    private int selectedPosition = -1;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getAddDetails3() {
        return addDetails3;
    }

    public void setAddDetails3(String addDetails3) {
        this.addDetails3 = addDetails3;
    }

    private String addDetails3;
    private String addType;
    private String addUserID;
    private String addLatitude;
    private String addLongitude;
    private boolean addIsActive;
    private String addCreationTime;
    private String addModifiedTime;

    public String getAddID() {
        return addID;
    }

    public void setAddID(String addID) {
        this.addID = addID;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddContactNo() {
        return addContactNo;
    }

    public void setAddContactNo(String addContactNo) {
        this.addContactNo = addContactNo;
    }

    public String getAddEmailID() {
        return addEmailID;
    }

    public void setAddEmailID(String addEmailID) {
        this.addEmailID = addEmailID;
    }

    public String getAddCompany() {
        return addCompany;
    }

    public void setAddCompany(String addCompany) {
        this.addCompany = addCompany;
    }

    public String getAddZipCode() {
        return addZipCode;
    }

    public void setAddZipCode(String addZipCode) {
        this.addZipCode = addZipCode;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddSatate() {
        return addSatate;
    }

    public void setAddSatate(String addSatate) {
        this.addSatate = addSatate;
    }

    public String getAddCountry() {
        return addCountry;
    }

    public void setAddCountry(String addCountry) {
        this.addCountry = addCountry;
    }

    public String getAddDetails() {
        return addDetails;
    }

    public void setAddDetails(String addDetails) {
        this.addDetails = addDetails;
    }

    public String getAddDetails1() {
        return addDetails1;
    }

    public void setAddDetails1(String addDetails1) {
        this.addDetails1 = addDetails1;
    }

    public String getAddDetails2() {
        return addDetails2;
    }

    public void setAddDetails2(String addDetails2) {
        this.addDetails2 = addDetails2;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getAddUserID() {
        return addUserID;
    }

    public void setAddUserID(String addUserID) {
        this.addUserID = addUserID;
    }

    public String getAddLatitude() {
        return addLatitude;
    }

    public void setAddLatitude(String addLatitude) {
        this.addLatitude = addLatitude;
    }

    public String getAddLongitude() {
        return addLongitude;
    }

    public void setAddLongitude(String addLongitude) {
        this.addLongitude = addLongitude;
    }

    public boolean getAddIsActive() {
        return addIsActive;
    }

    public void setAddIsActive(boolean addIsActive) {
        this.addIsActive = addIsActive;
    }

    public String getAddCreationTime() {
        return addCreationTime;
    }

    public void setAddCreationTime(String addCreationTime) {
        this.addCreationTime = addCreationTime;
    }

    public String getAddModifiedTime() {
        return addModifiedTime;
    }

    public void setAddModifiedTime(String addModifiedTime) {
        this.addModifiedTime = addModifiedTime;
    }
}
