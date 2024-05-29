package com.example.gfgapp.modal;

public class SubjectModal {
    private static SubjectModal instance;
    private String subject;
    private String history;
    private String math;
    private String english;
    private String science;
    private SubjectModal() {}

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public static synchronized SubjectModal getInstance() {
        if (instance == null) {
            instance = new SubjectModal();
        }
        return instance;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
