package com.example.gfgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
    Name=Name
    Duration=Subject
    Tracks=Hours
    Description=Core
*/
public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler

    private Button addCourseBtn, readCourseBtn, splashPageButton;
    private DBHandler dbHandler;
    private EditText studentName, studentHours, studentSubject, studentCore;
    private RecyclerView courseRV;
    private CourseRVAdapter courseRVAdapter;
    private TextView name;
   static MainModal mainModal = new MainModal();


    // variable for our adapter
    // class and array list
    private CourseRVAdapter adapter;
    private ArrayList<CourseModal> courseModalArrayList;

    @NonNull
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* on below line we are inflating our layout
           file for our recycler view items.
           below line is to inflate our layout.*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_rv_item, parent, false);

        return new CourseRVAdapter.ViewHolder(view);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // initializing all our variables.
        studentName = findViewById(R.id.studentNameDb);
        studentHours = findViewById(R.id.studentHoursDb);
        studentSubject = findViewById(R.id.studentSubjectDb);
        studentCore = findViewById(R.id.studentCoreDb);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        readCourseBtn = findViewById(R.id.idBtnReadCourse);

        courseRV = findViewById(R.id.idRVCourses);

        // initializing db class
        dbHandler = new DBHandler(MainActivity.this);


        addCourseBtn.setOnClickListener(v -> {

            // below line is to get data from all edit text fields.
            String studentNameDb = studentName.getText().toString();
            String studentSubjectDb = studentSubject.getText().toString();
            String studentHoursDb = studentHours.getText().toString();
            String studentCoreDb = studentCore.getText().toString();

            //Getting date format for database
            String df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());


            // validating if the text fields are empty or not.
            if (studentNameDb.isEmpty() && studentSubjectDb.isEmpty() && studentHoursDb.isEmpty() && studentCoreDb.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }

            /* on below line we are calling a method to add new
               course to sqlite data and pass all our values to it.*/

            dbHandler.addNewCourse(studentNameDb, studentSubjectDb, studentHoursDb, studentCoreDb, df, mainModal.getUserEmail());

            // after adding the data we are displaying a toast message.
            Toast.makeText(MainActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
            studentName.setText("");
            studentSubject.setText("");
            studentHours.setText("");
            studentCore.setText("");

        });


        readCourseBtn.setOnClickListener(v -> {
            // opening a new activity via a intent.
            Intent i = new Intent(MainActivity.this, ViewCourses.class);
            startActivity(i);

        });


    }




}
