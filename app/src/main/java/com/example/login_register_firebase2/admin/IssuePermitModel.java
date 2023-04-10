package com.example.login_register_firebase2.admin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.login_register_firebase2.farmer.AppliedPermitModel;

import java.io.Serializable;

public class IssuePermitModel {

    private String fullName;
    private String userEmail;
    private String phoneNumber;
    private String fieldNumber;
    private String subLocation;
    private String area;
    private String caneAge;
    private String cropRotation;
    private String estimatedTonnes;
    private String acreAge;
    private String bankName;
    private String bankAccountNumber;
    private String selectedDate;

    private String allocated_harvesting_date;
    private String trucksIssued;
    public IssuePermitModel(){
        // Required empty constructor
    }

    // Constructors, getters and setters


    public IssuePermitModel(String fullName, String userEmail, String phoneNumber, String fieldNumber, String subLocation, String area, String caneAge, String cropRotation, String estimatedTonnes, String acreAge, String bankName, String bankAccountNumber, String selectedDate, String allocated_harvesting_date, String trucksIssued) {
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.fieldNumber = fieldNumber;
        this.subLocation = subLocation;
        this.area = area;
        this.caneAge = caneAge;
        this.cropRotation = cropRotation;
        this.estimatedTonnes = estimatedTonnes;
        this.acreAge = acreAge;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.selectedDate = selectedDate;
        this.allocated_harvesting_date = allocated_harvesting_date;
        this.trucksIssued = trucksIssued;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCaneAge() {
        return caneAge;
    }

    public void setCaneAge(String caneAge) {
        this.caneAge = caneAge;
    }

    public String getCropRotation() {
        return cropRotation;
    }

    public void setCropRotation(String cropRotation) {
        this.cropRotation = cropRotation;
    }

    public String getEstimatedTonnes() {
        return estimatedTonnes;
    }

    public void setEstimatedTonnes(String estimatedTonnes) {
        this.estimatedTonnes = estimatedTonnes;
    }

    public String getAcreAge() {
        return acreAge;
    }

    public void setAcreAge(String acreAge) {
        this.acreAge = acreAge;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getAllocated_harvesting_date() {
        return allocated_harvesting_date;
    }

    public void setAllocated_harvesting_date(String allocated_harvesting_date) {
        this.allocated_harvesting_date = allocated_harvesting_date;
    }

    public String getTrucksIssued() {
        return trucksIssued;
    }

    public void setTrucksIssued(String trucksIssued) {
        this.trucksIssued = trucksIssued;
    }
}
