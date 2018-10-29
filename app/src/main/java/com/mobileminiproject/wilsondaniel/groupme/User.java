package com.mobileminiproject.wilsondaniel.groupme;

public class User {

    private String userID, name, emailAddress, password;
    private Integer contactNo;
    private Boolean isStudent;

    public User() {}

    public User(String userID, String name, Integer contactNo, String emailAddress, String password, Boolean isStudent) {
        this.userID = userID;
        this.name = name;
        this.contactNo = contactNo;
        this.emailAddress = emailAddress;
        this.password = password;
        this.isStudent = isStudent;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() { return name; }

    public Integer getContactNo() {
        return contactNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }
}
