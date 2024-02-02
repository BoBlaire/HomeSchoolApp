package com.example.gfgapp.modal;

public class YourData {
    private String name;
    private String subject;
    private String hours;
    private String core;
    private String description;
    private String email;
    private String df;

    // Add other fields as needed

    public YourData() {
        // Default constructor required for Firestore
    }

    public YourData(String name) {
        this.name = name;
        // Initialize other fields as needed
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
