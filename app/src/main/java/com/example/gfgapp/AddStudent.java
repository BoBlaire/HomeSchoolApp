package com.example.gfgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudent extends AppCompatActivity {

    EditText studentName, studentGrade;
    Button addStudent;

    MainModal mainModal = MainActivity.mainModal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        addStudent = findViewById(R.id.addStudentData);
        studentGrade = findViewById(R.id.studentGrade);
        studentName = findViewById(R.id.studentNameEdit);


        StudentDBHandler studentDBHandler = new StudentDBHandler(AddStudent.this);


        addStudent.setOnClickListener(v -> {
            String nameStudent = studentName.getText().toString();

            String gradeStudent = studentGrade.getText().toString();

            if (nameStudent.isEmpty() || gradeStudent.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else if (!nameStudent.isEmpty() || !gradeStudent.isEmpty()) {


                if (mainModal.getUserEmail() == null) {
                    studentDBHandler.addUserInfo(nameStudent, gradeStudent, mainModal.getGoogleEmail());
                } else {
                    studentDBHandler.addUserInfo(nameStudent, gradeStudent, mainModal.getUserEmail());
                }



                Intent i = new Intent(AddStudent.this, StudentView.class);
                startActivity(i);
            }
        });
    }
}