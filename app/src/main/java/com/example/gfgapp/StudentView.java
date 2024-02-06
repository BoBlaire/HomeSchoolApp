package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.dataadapter.AddStudentDB;
import com.example.gfgapp.databases.DBHandler;
//import com.example.gfgapp.databases.StudentDBHandler;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.StudentModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentView extends AppCompatActivity {

    FloatingActionButton addStudent;

//    StudentDBHandler studentDBHandler;
    String studentName;

    StudentAdapter studentAdapter;

    MainModal mainModal = MainActivity.mainModal;
    DBHandler dbHandler;

    AddStudentDB addStudentDB;
    private ArrayList<StudentModal> courseModelArrayList;
    private  StudentAdapter courseAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_view);

        addStudentDB = new AddStudentDB(this);

        if (mainModal.getGoogleEmail() != null) {
            addStudentDB.readStudents(mainModal.getGoogleEmail(), new AddStudentDB.FirestoreCallback() {
                @Override
                public void onCallback(ArrayList<StudentModal> arrayValue) {
                    updateUIWithData(arrayValue);
                }
            });

        } else if (mainModal.getGoogleEmail() == null) {
            addStudentDB.readStudents(mainModal.getUserEmail(), new AddStudentDB.FirestoreCallback() {
                @Override
                public void onCallback(ArrayList<StudentModal> arrayValue) {
                    updateUIWithData(arrayValue);
                }
            });
        }


        addStudent = findViewById(R.id.addStudent);


        RecyclerView courseRV = findViewById(R.id.recyView);
        courseModelArrayList = new ArrayList<StudentModal>();

        courseAdapter = new StudentAdapter(this, courseModelArrayList);

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

    private void updateUIWithData(ArrayList<StudentModal> data) {
        // Update your UI or perform other actions with the retrieved data
        // For example, update the adapter or any other UI components.
        courseModelArrayList.clear();
        courseModelArrayList.addAll(data);
        courseAdapter.notifyDataSetChanged();
    }

}

