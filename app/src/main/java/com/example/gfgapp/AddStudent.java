package com.example.gfgapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gfgapp.dataadapter.AddStudentDB;
import com.example.gfgapp.modal.MainModal;

public class AddStudent extends AppCompatActivity {

    private EditText nameEditText, gradeEditText, emailEditText;
    private Button addButton;
    private AddStudentDB addStudentDB;

    MainModal mainModal = MainModal.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        nameEditText = findViewById(R.id.studentNameEdit);
        gradeEditText = findViewById(R.id.studentGrade);
        addButton = findViewById(R.id.addStudentData);

        addStudentDB = new AddStudentDB(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String grade = gradeEditText.getText().toString().trim();

                if (name.isEmpty() || grade.isEmpty()) {
                    Toast.makeText(AddStudent.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addStudentDB.addUserInfo(name, grade, mainModal.getUserEmail(), new Runnable() {
                        @Override
                        public void run() {
                            // Callback to update the UI after adding a new student
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddStudent.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity and go back to the student list
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
