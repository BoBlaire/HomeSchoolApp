package com.example.gfgapp.modal;

import java.util.ArrayList;

public class MainModal {
    private static MainModal instance;

    private String userEmail;
    private String userName;
    private String googleEmail;
    private String googleVerification;
    private String email;
    private boolean isSignedIn;
    private ArrayList<CourseModal> arrayList;

    // Private constructor to prevent instantiation
    private MainModal() {}

    // Public method to provide access to the singleton instance
    public static synchronized MainModal getInstance() {
        if (instance == null) {
            instance = new MainModal();
        }
        return instance;
    }

    // Getter and setter methods
    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public ArrayList<CourseModal> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<CourseModal> arrayList) {
        this.arrayList = arrayList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleVerification() {
        return googleVerification;
    }

    public void setGoogleVerification(String googleVerification) {
        this.googleVerification = googleVerification;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
