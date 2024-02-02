package com.example.gfgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.dataadapter.AddInfo;
import com.example.gfgapp.databases.*;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private EditText studentName, studentHours, studentDescription;
    private RecyclerView courseRV;
    private CourseRVAdapter courseRVAdapter;
    private TextView name;
    public static MainModal mainModal = new MainModal();

//    private AutoCompleteTextView studentCore, studentSubject;


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

    @SuppressLint({"MissingInflatedId", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // initializing all our variables.
        studentName = findViewById(R.id.studentNameDb);
        studentHours = findViewById(R.id.studentHoursDb);
        AutoCompleteTextView studentSubject = (AutoCompleteTextView) findViewById(R.id.studentSubjectDb);
        AutoCompleteTextView studentCore = (AutoCompleteTextView) findViewById(R.id.studentCoreDb);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        readCourseBtn = findViewById(R.id.idBtnReadCourse);
        studentDescription = findViewById(R.id.studentDescriptionDb);

        courseRV = findViewById(R.id.idRVCourses);

        // initializing db class
        dbHandler = new DBHandler(MainActivity.this);


        studentName.setText(mainModal.getUserName());

//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("id", "210793");
//
//
//        // Add a new document with a generated ID
//        db.collection("id")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });

        String[] core = getResources().getStringArray(R.array.Core);
        String[] subjects = getResources().getStringArray(R.array.Subjects);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, core);
        ArrayAdapter<String> adapterSubjects = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjects);

        studentCore.setAdapter(adapter);
        studentSubject.setAdapter(adapterSubjects);

        addCourseBtn.setOnClickListener(v -> {
            AddInfo addInfo = new AddInfo();
            // below line is to get data from all edit text fields.
            String studentNameDb = studentName.getText().toString();
            String studentSubjectDb = studentSubject.getText().toString();
            String studentHoursDb = studentHours.getText().toString();
            String studentCoreDb = studentCore.getText().toString();
            String studentDescDb = studentDescription.getText().toString();
            //Getting date format for database
            String df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());


            // validating if the text fields are empty or not.
            if ((studentNameDb.isEmpty() || studentSubjectDb.equals("Subject") || studentHoursDb.isEmpty() || studentCoreDb.equals("Core") || studentDescDb.trim().isEmpty())) {
                Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();


            } else {


                if (mainModal.getUserEmail() == null) {
                    dbHandler.addNewCourse(studentNameDb, studentSubjectDb, studentHoursDb, studentCoreDb, df, mainModal.getGoogleEmail(), studentDescDb);
                    addInfo.enterData(studentNameDb, studentSubjectDb, studentHoursDb, studentCoreDb, df, mainModal.getGoogleEmail(), studentDescDb);
                } else {
                    dbHandler.addNewCourse(studentNameDb, studentSubjectDb, studentHoursDb, studentCoreDb, df, mainModal.getUserEmail(), studentDescDb);
                    addInfo.enterData(studentNameDb, studentSubjectDb, studentHoursDb, studentCoreDb, df, mainModal.getUserEmail(), studentDescDb);
                }


                Toast.makeText(MainActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                studentName.setText(mainModal.getUserName());
                studentSubject.setAdapter(adapterSubjects);
                studentHours.setText("");
                studentCore.setAdapter(adapter);
                studentDescription.setText("");
            }

            /* on below line we are calling a method to add new
               course to sqlite data and pass all our values to it.*/


            // after adding the data we are displaying a toast message.


        });


        readCourseBtn.setOnClickListener(v -> {
            // opening a new activity via a intent.
            Intent i = new Intent(MainActivity.this, ViewCourses.class);
            startActivity(i);

        });


    }


}
