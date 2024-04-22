package com.example.gfgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gfgapp.dataadapter.AddStudentDB;
//import com.example.gfgapp.databases.StudentDBHandler;
import com.example.gfgapp.modal.MainModal;

import org.checkerframework.checker.units.qual.A;

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


//        StudentDBHandler studentDBHandler = new StudentDBHandler(AddStudent.this);
        AddStudentDB addStudentDB = new AddStudentDB(this);

        addStudent.setOnClickListener(v -> {
            try {
                String nameStudent = studentName.getText().toString();
                String gradeStudent = studentGrade.getText().toString();

                if (nameStudent.isEmpty() || gradeStudent.isEmpty()) {
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (!nameStudent.isEmpty() || !gradeStudent.isEmpty()) {

                    System.out.println("user email" + mainModal.getUserEmail());
                    if (mainModal.getUserEmail() == null) {

                        addStudentDB.addUserInfo(nameStudent, gradeStudent, mainModal.getGoogleEmail());
                    } else {

                        addStudentDB.addUserInfo(nameStudent, gradeStudent, mainModal.getUserEmail());
                    }


                    Intent i = new Intent(AddStudent.this, StudentView.class);
                    startActivity(i);
                }
            } catch (Exception e) {
                System.out.println("Add Student Failed: " + e);
            }
        });
    }
}