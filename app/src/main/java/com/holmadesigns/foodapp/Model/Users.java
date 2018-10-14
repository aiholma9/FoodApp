package com.holmadesigns.foodapp.Model;

public class Users {
    private String userFirstName;
    private String userSurname;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String userCPassword;

    public Users() {
    }

    public Users(String userFirstName, String userSurname, String userEmail, String userPhone, String userPassword, String userCPassword) {
        this.userFirstName = userFirstName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userCPassword = userCPassword;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCPassword() {
        return userCPassword;
    }

    public void setUserCPassword(String userCPassword) {
        this.userCPassword = userCPassword;
    }
}
