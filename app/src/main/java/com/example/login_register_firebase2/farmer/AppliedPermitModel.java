package com.example.login_register_firebase2.farmer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AppliedPermitModel implements Parcelable {

        private String documentId;
        private String userID;

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

        public AppliedPermitModel() {
            // Required empty constructor
        }



        public AppliedPermitModel(String documentId, String userID, String fullName, String userEmail, String phoneNumber, String fieldNumber,
                                 String subLocation, String area, String caneAge, String estimatedTonnes, String acreAge, String cropRotation, String bankName, String bankAccountNumber, String selectedDate) {


            this.documentId = documentId;
            this.userID = userID;
            this.fullName = fullName;
            this.userEmail = userEmail;
            this.phoneNumber =phoneNumber;
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

        }


    //parcelable implementation
    protected AppliedPermitModel(Parcel in) {
        documentId = in.readString();
        userID = in.readString();
        fullName = in.readString();
        userEmail = in.readString();
        phoneNumber = in.readString();
        fieldNumber = in.readString();
        subLocation = in.readString();
        area = in.readString();
        caneAge = in.readString();
        cropRotation = in.readString();
        estimatedTonnes = in.readString();
        acreAge = in.readString();
        bankName = in.readString();
        bankAccountNumber = in.readString();
        selectedDate = in.readString();
    }

    public static final Creator<AppliedPermitModel> CREATOR = new Creator<AppliedPermitModel>() {
        @Override
        public AppliedPermitModel createFromParcel(Parcel in) {
            return new AppliedPermitModel(in);
        }

        @Override
        public AppliedPermitModel[] newArray(int size) {
            return new AppliedPermitModel[size];
        }
    };

    // getter and setter methods for permit application data
        public String getDocumentId () {
             return documentId;
          }

        public void setDocumentId (String documentId){

            this.documentId = documentId;
         }
        public String getUserID () {
            return userID;
        }

        public void setUserID (String userID){

            this.userID = userID;
        }
        public String getFullName () {
            return fullName;
        }

        public void setFullName (String fullName){

            this.fullName = fullName;
        }
        public String getUserEmail () {
            return userEmail;
        }

        public void setUserEmail (String userEmail){

            this.userEmail = userEmail;
        }
        public String getPhoneNumber () {
            return phoneNumber;
        }

        public void setPhoneNumber (String phoneNumber){

            this.phoneNumber = phoneNumber;
        }

        public String getFieldNumber () {
            return fieldNumber;
        }

        public void setFieldNumber (String fieldNumber){

            this.fieldNumber = fieldNumber;
        }

        public String getSubLocation () {
            return subLocation;
        }

        public void setSubLocation (String subLocation){
            this.subLocation = subLocation;
        }

        public String getArea () {
            return area;
        }

        public void setArea (String area){
            this.area = area;
        }

        public String getCaneAge () {
            return caneAge;

        }

        public void setCaneAge (String caneAge){
            this.caneAge = caneAge;
        }

        public String getCropRotation () {
            return cropRotation;
        }

        public void setCropRotation (String cropRotation){

            this.cropRotation = cropRotation;
        }

        public String getEstimatedTonnes () {
            return estimatedTonnes;
        }

        public void setEstimatedTonnes (String estimatedTonnes){
            this.estimatedTonnes = estimatedTonnes;
        }

        public String getAcreAge () {
            return acreAge;
        }

        public void setAcreAge (String acreAge){
            this.acreAge = acreAge;
        }

        public String getBankName () {
            return bankName;
        }

        public void setBankName (String bankName){

            this.bankName = bankName;
        }

        public String getBankAccountNumber () {
            return bankAccountNumber;
        }

        public void setBankAccountNumber (String bankAccountNumber){
            this.bankAccountNumber = bankAccountNumber;
        }

        public String getSelectedDate () {
            return selectedDate;
        }

        public void setSelectedDate (String selectedDate){

            this.selectedDate = selectedDate;
        }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(userID);
        dest.writeString(fullName);
        dest.writeString(userEmail);
        dest.writeString(phoneNumber);
        dest.writeString(fieldNumber);
        dest.writeString(subLocation);
        dest.writeString(area);
        dest.writeString(caneAge);
        dest.writeString(cropRotation);
        dest.writeString(estimatedTonnes);
        dest.writeString(acreAge);
        dest.writeString(bankName);
        dest.writeString(bankAccountNumber);
        dest.writeString(selectedDate);
    }
}

