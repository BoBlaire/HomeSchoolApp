package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gfgapp.dataadapter.UpdateInfo;
import com.example.gfgapp.databases.DBHandler;
import com.example.gfgapp.modal.Modal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateCourseActivity extends AppCompatActivity {

    // variables for our edit text, button, strings and dbhandler class.
    private EditText studentNameUpd, studentHoursUpd, studentSubjectUpd, studentCoreUpd, studentDescriptionUpd;
    private Button updateCourseBtn, deleteCourseBtn;
    private DBHandler dbHandler;
    String studentName, studentCore, studentSubject, studentHours, documentId, description;
    private int idPos;
    CourseRVAdapter courseRVAdapter;
    String adapterUpdate;
    private Modal modal;
    private boolean bool = false;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);


        // initializing all our variables.
        studentNameUpd = findViewById(R.id.updStudentName);
        studentHoursUpd = findViewById(R.id.updStudentHours);
        updateCourseBtn = findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = findViewById(R.id.idBtnDelete);
        studentDescriptionUpd = findViewById(R.id.studentDescriptionUpd);
        AutoCompleteTextView studentSubjectUpd = (AutoCompleteTextView) findViewById(R.id.updStudentSubject);
        AutoCompleteTextView studentCoreUpd = (AutoCompleteTextView) findViewById(R.id.updStudentCore);

        // on below line we are initialing our dbhandler class.
//        dbHandler = new DBHandler(UpdateCourseActivity.this);

        /* on below lines we are getting data which
           we passed in our adapter class. */
        studentName = getIntent().getStringExtra("name");
        studentSubject = getIntent().getStringExtra("subject");
        studentHours = getIntent().getStringExtra("hours");
        studentCore = getIntent().getStringExtra("core");
        description = getIntent().getStringExtra("description");
        documentId = getIntent().getStringExtra("documentId");


        studentNameUpd.setText(studentName);
        studentSubjectUpd.setText(studentSubject);
        studentHoursUpd.setText(studentHours);
        studentCoreUpd.setText(studentCore);
        studentDescriptionUpd.setText(description);


        String[] core = getResources().getStringArray(R.array.Core);
        String[] subjects = getResources().getStringArray(R.array.Subjects);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, core);
        ArrayAdapter<String> adapterSubjects = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);

        studentCoreUpd.setAdapter(adapter);
        studentSubjectUpd.setAdapter(adapterSubjects);


//         adding on click listener to our update course button.
        updateCourseBtn.setOnClickListener(v -> {

            String collectionName = "homeschool";

            Map<String, Object> updates = new HashMap<>();
            updates.put("name", studentNameUpd.getText().toString());
            updates.put("subject", studentSubjectUpd.getText().toString());
            updates.put("hours", studentHoursUpd.getText().toString());
            updates.put("core", studentCoreUpd.getText().toString());
            updates.put("description", studentDescriptionUpd.getText().toString());


            UpdateInfo.updateDocument(collectionName, documentId, updates, new UpdateInfo.FirestoreUpdateCallback() {
                @Override
                public void onSuccess() {
                    // Handle success
                    // For example, display a toast or update the UI
                    Toast.makeText(UpdateCourseActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateCourseActivity.this, ViewCourses.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Exception e) {
                    // Handle failure
                    // For example, display an error message
                    Toast.makeText(UpdateCourseActivity.this, "Error Adding Information", Toast.LENGTH_SHORT).show();
                }
            });
        });

       // adding on click listener for delete button to delete our course.
        deleteCourseBtn.setOnClickListener(v -> {

            try {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.deleteRecord(documentId);

                Intent i = new Intent(UpdateCourseActivity.this, ViewCourses.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(UpdateCourseActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}