package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.dataadapter.AddStudentDB;
import com.example.gfgapp.databases.DBHandler;
//import com.example.gfgapp.databases.StudentDBHandler;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.StudentModal;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentView extends AppCompatActivity {

    FloatingActionButton addStudent, calendar;
    private static final int RC_SIGN_IN = 718;

    //    StudentDBHandler studentDBHandler;
    String studentName;

    StudentAdapter studentAdapter;
    private WebView calendarWebView;

    MainModal mainModal = MainActivity.mainModal;
    DBHandler dbHandler;

    AddStudentDB addStudentDB;
    private ArrayList<StudentModal> courseModelArrayList;
    private StudentAdapter courseAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        calendar = findViewById(R.id.calender);

        calendarWebView = findViewById(R.id.calendarWebView);

        calendar.setOnClickListener(v -> {
            // Load Google Calendar in the WebView
            loadGoogleCalendar();
        });

        // Configure the WebView
        WebSettings webSettings = calendarWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        calendarWebView.setWebViewClient(new WebViewClient() {
            // If you want to handle links within the WebView
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });




        addStudentDB = new AddStudentDB(this);

        if (mainModal.getUserEmail() != null) {
            addStudentDB.readStudents(mainModal.getUserEmail(), arrayValue -> updateUIWithData(arrayValue));
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

        calendar.setOnClickListener(v -> {

//            Intent signInIntent = googleSignInClient.getSignInIntent();
//            startActivityForResult(signInIntent, RC_SIGN_IN);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://calendar.google.com"));
            startActivity(intent);
        });

    }

    private void updateUIWithData(ArrayList<StudentModal> data) {
        // Update your UI or perform other actions with the retrieved data
        // For example, update the adapter or any other UI components.
        courseModelArrayList.clear();
        courseModelArrayList.addAll(data);
        courseAdapter.notifyDataSetChanged();
    }

    private void loadGoogleCalendar() {
        calendarWebView.setVisibility(WebView.VISIBLE);
        calendarWebView.loadUrl("https://calendar.google.com");
    }

    @Override
    public void onBackPressed() {
        if (calendarWebView.canGoBack()) {
            calendarWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

