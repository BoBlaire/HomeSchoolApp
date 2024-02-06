package com.example.gfgapp.modal;

public class StudentModal {

    private String name;
    private String email;
    private String grade;

    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public StudentModal(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }


}
