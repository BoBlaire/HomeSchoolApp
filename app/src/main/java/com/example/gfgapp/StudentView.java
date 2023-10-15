package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentView extends AppCompatActivity {

    FloatingActionButton addStudent;

    StudentDBHandler studentDBHandler;
    String studentName;

    StudentAdapter studentAdapter;

    MainModal mainModal = MainActivity.mainModal;
    DBHandler dbHandler;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_view);

        addStudent = findViewById(R.id.addStudent);

        studentDBHandler = new StudentDBHandler(StudentView.this);

        dbHandler = new DBHandler(StudentView.this);

        RecyclerView courseRV = findViewById(R.id.recyView);

        // Here, we have created new array list and added data to it
        ArrayList<StudentModal> courseModelArrayList = new ArrayList<StudentModal>();
        ArrayList<StudentModal> totalSubjectHours = new ArrayList<StudentModal>();


        courseModelArrayList = studentDBHandler.readStudents();


        studentAdapter = new StudentAdapter(StudentView.this, courseModelArrayList);


        // we are initializing our adapter class and passing our arraylist to it.
        StudentAdapter courseAdapter = new StudentAdapter(this, courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);


        addStudent.setOnClickListener(V -> {

            Intent i = new Intent(StudentView.this, AddStudent.class);
            startActivity(i);

        });

    }
}

