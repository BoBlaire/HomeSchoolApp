package com.example.gfgapp;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gfgapp.dataadapter.AddInfo;
import com.example.gfgapp.DEAD.DBHandler;
import com.example.gfgapp.modal.SubjectModal;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.Modal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewCourses extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<CourseModal> courseModalArrayList;
    private DBHandler dbHandler;

    public ArrayList<CourseModal> getCourseModalArrayList() {
        return courseModalArrayList;
    }

    public void setCourseModalArrayList(ArrayList<CourseModal> courseModalArrayList) {
        this.courseModalArrayList = courseModalArrayList;
    }

    private CourseRVAdapter courseRVAdapter;

    private String subjects;
    private RecyclerView coursesRV;
    private Context context;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream;
    private TextView date, subject, hours, core, subjectText, coreText, hoursText, dateText;
    private int position;
    static Modal modal = new Modal(false);
    Button menuPopupWindow;

    MainModal mainModal = MainModal.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        subjectText = findViewById(R.id.subject);
        hoursText = findViewById(R.id.hours);
        coreText = findViewById(R.id.core);
        dateText = findViewById(R.id.date);


        resizeText();

        AddInfo addInfo = new AddInfo();

        // Call the retrieveData method with the callback
//        System.out.println("true?: "+mainModal.getGoogleEmail().isEmpty());
//        subjects = getIntent().getStringExtra("subjects");
        SubjectModal subs = SubjectModal.getInstance();
        String subject = subs.getSubject();


        try {
            if (mainModal.getGoogleEmail().isEmpty()) {
            } else {
                addInfo.retrieveData(mainModal.getUserName(), mainModal.getGoogleEmail(), subject, new AddInfo.FirestoreCallback() {
                    @Override
                    public void onCallback(ArrayList<CourseModal> arrayValue) {
                        // Handle the retrieved data here
                        // The 'arrayValue' variable contains the data.

                        // Update your UI or perform other actions with the retrieved data
                        updateUIWithData(arrayValue);
                    }
                });
            }
        } catch (Exception e) {
            addInfo.retrieveData(mainModal.getUserName(), mainModal.getUserEmail(), subject, new AddInfo.FirestoreCallback() {
                @Override
                public void onCallback(ArrayList<CourseModal> arrayValue) {
                    // Handle the retrieved data here
                    // The 'arrayValue' variable contains the data.

                    // Update your UI or perform other actions with the retrieved data
                    updateUIWithData(arrayValue);
                }
            });
        }


        // Getting display size
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int width_screen = displayMetrics.widthPixels;

        modal.setScreeWidth(width_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton button = findViewById(R.id.buttonPanel);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton pdf = findViewById(R.id.pdfPrint);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton buttonHome = findViewById(R.id.buttonHome);


        toolbar.setTitle(mainModal.getUserName());
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFFFF"));
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);


        // initializing our all variables.
        courseModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewCourses.this);

        // on below line passing our array lost to our adapter class.
        courseRVAdapter = new CourseRVAdapter(courseModalArrayList, ViewCourses.this);
        coursesRV = findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCourses.this, VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);

        //calling splash page
        button.setOnClickListener(v -> {
            Intent i = new Intent(ViewCourses.this, MainActivity.class);
            startActivity(i);
        });
        //calling delete all records
//        pdf.setOnClickListener(v -> {
//            onBackPressed();
//        });
        buttonHome.setOnClickListener(v -> {
            Intent i = new Intent(ViewCourses.this, Snapshots.class);
            startActivity(i);
        });

        // setting our adapter to recycler view.
        coursesRV.setAdapter(courseRVAdapter);

    }


    //resizing text
    public void reziseText(View view) {

//        name = view.findViewById(R.id.displayName);
        date = view.findViewById(R.id.displayDate);
        subject = view.findViewById(R.id.displaySubject);
        hours = view.findViewById(R.id.displayHours);
        core = view.findViewById(R.id.displayCore);

//        name.setWidth(modal.getScreeWidth() / 4);
        subject.setWidth(modal.getScreeWidth() / 4);
        hours.setWidth(modal.getScreeWidth() / 4);
        core.setWidth(modal.getScreeWidth() / 4);
        date.setWidth(modal.getScreeWidth() / 4);
    }

    public void resizeText() {
        subjectText.setWidth(modal.getScreeWidth() / 4);
        hoursText.setWidth(modal.getScreeWidth() / 4);
        coreText.setWidth(modal.getScreeWidth() / 4);
        dateText.setWidth(modal.getScreeWidth() / 4);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();
        // menuPopupWindow = findViewById(R.id.actionSort);

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);


        // below we are passing all our values.

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) ((MenuItem) searchItem).getActionView();

        searchView.setBackgroundResource(R.drawable.rounded_search);

        searchView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<CourseModal> filteredlist = new ArrayList<CourseModal>();

        if (text.isEmpty()) {
            Intent id = new Intent(ViewCourses.this, UpdateCourseActivity.class);
            id.putExtra("update", false);
            modal.setAdapterStatement(id.getBooleanExtra("update", false));
        } else if (!text.isEmpty()) {
            Intent id = new Intent(ViewCourses.this, UpdateCourseActivity.class);
            id.putExtra("update", true);
            modal.setAdapterStatement(id.getBooleanExtra("update", false));
        }

        // running a for loop to compare elements.
        for (CourseModal item : courseModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStudentSubject().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            } else if (item.getStudentCore().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            courseRVAdapter.filterList(filteredlist);
        }
    }

    private void updateUIWithData(ArrayList<CourseModal> data) {
        // Update your UI or perform other actions with the retrieved data
        // For example, update the adapter or any other UI components.

        courseModalArrayList.clear();
        courseModalArrayList.addAll(data);
        courseRVAdapter.notifyDataSetChanged();
    }
}