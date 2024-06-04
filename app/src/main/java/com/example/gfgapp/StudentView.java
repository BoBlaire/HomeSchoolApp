package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gfgapp.dataadapter.AddStudentDB;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.StudentModal;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

public class StudentView extends AppCompatActivity {

    ExtendedFloatingActionButton addStudent;
    MainModal mainModal = MainModal.getInstance();
    AddStudentDB addStudentDB;
    private ArrayList<StudentModal> courseModelArrayList;
    private StudentAdapter courseAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_view);

        addStudentDB = new AddStudentDB(this);

        RecyclerView courseRV = findViewById(R.id.recyView);
        courseModelArrayList = new ArrayList<>();
        courseAdapter = new StudentAdapter(this, courseModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);

        addStudent = findViewById(R.id.addStudent);
        addStudent.setOnClickListener(V -> {
            Intent i = new Intent(StudentView.this, AddStudent.class);
            startActivity(i);
        });

        fetchStudents();
    }

    private void fetchStudents() {
        if (mainModal.getUserEmail() != null) {
            addStudentDB.readStudents(mainModal.getUserEmail(), this::updateUIWithData);
        }
    }

    private void updateUIWithData(ArrayList<StudentModal> data) {
        courseModelArrayList.clear();
        courseModelArrayList.addAll(data);
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchStudents(); // Re-fetch students when returning to this activity
    }

}
