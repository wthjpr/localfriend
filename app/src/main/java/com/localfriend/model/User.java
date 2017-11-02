package com.localfriend.model;

import java.io.Serializable;

/**
 * Created by SONI on 10/14/2017.
 */

public class User implements Serializable {
    private final static long serialVersionUID = 81446634L;
    private Data data;
    private UserInfo UserInfo;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public User.UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(User.UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public class Data implements Serializable {
        private final static long serialVersionUID = 814851246634L;
        private String access_token;
        private String token_type;
        private String expires_in;
        private String userName;
        private String fullName;
        private String email;
        private String mobileNumber;
        private String gender;
        private String dOB;
        private String altMobileNo;
        private String profileImageURL;
        private String address;
        private String note;
        private String extra2;

        public String getExtra2() {
            return extra2;
        }

        public void setExtra2(String extra2) {
            this.extra2 = extra2;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getdOB() {
            return dOB;
        }

        public void setdOB(String dOB) {
            this.dOB = dOB;
        }

        public String getAltMobileNo() {
            return altMobileNo;
        }

        public void setAltMobileNo(String altMobileNo) {
            this.altMobileNo = altMobileNo;
        }

        public String getProfileImageURL() {
            return profileImageURL;
        }

        public void setProfileImageURL(String profileImageURL) {
            this.profileImageURL = profileImageURL;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public class UserInfo implements Serializable {
        private final static long serialVersionUID = 81485561246634L;
        private String FullName;
        private String Email;
        private String MobileNumber;
        private String Id;
        private String UserName;
        private String PasswordHash;
        private String SecurityStamp;

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            MobileNumber = mobileNumber;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPasswordHash() {
            return PasswordHash;
        }

        public void setPasswordHash(String passwordHash) {
            PasswordHash = passwordHash;
        }

        public String getSecurityStamp() {
            return SecurityStamp;
        }

        public void setSecurityStamp(String securityStamp) {
            SecurityStamp = securityStamp;
        }
    }
}
