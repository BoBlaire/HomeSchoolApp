package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class UpdateCourseActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and dbhandler class.
    private EditText studentNameUpd, studentHoursUpd, studentSubjectUpd, studentCoreUpd;
    private Button updateCourseBtn, deleteCourseBtn;
    private DBHandler dbHandler;
    String studentName, studentCore, studentSubject, studentHours, id;
    private int idPos;
    CourseRVAdapter courseRVAdapter;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);


        // initializing all our variables.
        studentNameUpd = findViewById(R.id.updStudentName);
        studentHoursUpd = findViewById(R.id.updStudentHours);
        studentSubjectUpd = findViewById(R.id.updStudentSubject);
        studentCoreUpd = findViewById(R.id.updStudentCore);
        updateCourseBtn = findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = findViewById(R.id.idBtnDelete);


        // on below line we are initialing our dbhandler class.
        dbHandler = new DBHandler(UpdateCourseActivity.this);

        /* on below lines we are getting data which
           we passed in our adapter class. */
        studentName = getIntent().getStringExtra("name");
        studentSubject = getIntent().getStringExtra("subject");
        studentHours = getIntent().getStringExtra("hours");
        studentCore = getIntent().getStringExtra("core");
        idPos = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));

        /* setting data to edit text
           of our update activity. */
        System.out.println(idPos);
        Cursor cursor = dbHandler.getDbId(studentName);



        long l = cursor.getLong(cursor.getColumnIndex("id"));

        studentNameUpd.setText(studentName);
        studentSubjectUpd.setText(studentSubject);
        studentHoursUpd.setText(studentHours);
        studentCoreUpd.setText(studentCore);


        // adding on click listener to our update course button.
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cursor.moveToPosition(idPos)) {
                    String str = cursor.getString(cursor.getColumnIndex("id"));


                    System.out.println(str);
                    dbHandler.updateCourse(str, studentNameUpd.getText().toString(), studentSubjectUpd.getText().toString(), studentHoursUpd.getText().toString(), studentCoreUpd.getText().toString());

                    // displaying a toast message that our course has been updated.
                    Toast.makeText(UpdateCourseActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();

                    // launching our main activity.
                    Intent i = new Intent(UpdateCourseActivity.this, MainActivity.class);
                    startActivity(i);
                }
                /* inside this method we are calling an update course
                   method and passing all our edit text values. */

            }
        });

        // adding on click listener for delete button to delete our course.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cursor != null && cursor.moveToNext()) {
                    // calling a method to delete our course.
                    String str = cursor.getString(cursor.getColumnIndex("id"));
                    dbHandler.deleteCourse(str);
                    Toast.makeText(UpdateCourseActivity.this, "Deleted the course", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateCourseActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}