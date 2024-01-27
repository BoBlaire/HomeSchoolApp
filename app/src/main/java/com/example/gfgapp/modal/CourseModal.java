package com.example.gfgapp.modal;

public class CourseModal {

    /* variables for our studentName,
       description, tracks and duration, id. */
    private String studentName;
    private String studentSubject;
    private String studentHours;
    private String studentCore;
    private String descrip;

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public String getStudentHours() {
        return studentHours;
    }

    public void setStudentHours(String studentHours) {
        this.studentHours = studentHours;
    }

    public String getStudentCore() {
        return studentCore;
    }

    public void setStudentCore(String studentCore) {
        this.studentCore = studentCore;
    }

    private int id;
    private int adapterId;


    public int getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(int adapterId) {
        this.adapterId = adapterId;
    }

    int o = 0;
    int i = 0;

    // creating getter and setter methods
    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public CourseModal(String studentName, String studentSubject, String studentHours, String studentCore, String description) {
        this.studentName = studentName;
        this.studentSubject = studentSubject;
        this.studentHours = studentHours;
        this.studentCore = studentCore;
        this.descrip = description;
    }


}