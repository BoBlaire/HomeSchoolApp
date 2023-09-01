package com.example.gfgapp;

import static android.view.View.inflate;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class ViewCourses extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<CourseModal> courseModalArrayList;
    private DBHandler dbHandler;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView coursesRV;
    private Context context;

    private TextView name, subject, hours, core;
    private int position;
    static Modal modal = new Modal(false);


    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        // Getting display size
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int width_screen = displayMetrics.widthPixels;

        modal.setScreeWidth(width_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton button = findViewById(R.id.buttonPanel);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton buttonDelete = findViewById(R.id.buttonDeleteAll);
        toolbar.setTitle("Home School Records");
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);


        // initializing our all variables.
        courseModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewCourses.this);

        // getting our course array
        // list from db handler class.
        courseModalArrayList = dbHandler.readCourses();

        // on below line passing our array lost to our adapter class.
        courseRVAdapter = new CourseRVAdapter(courseModalArrayList, ViewCourses.this);
        coursesRV = findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCourses.this, VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);


        button.setOnClickListener(v -> {
            Intent i = new Intent(ViewCourses.this, MainActivity.class);

            startActivity(i);

        });

        buttonDelete.setOnClickListener(v -> {

            onBackPressed();

        });

        // setting our adapter to recycler view.
        coursesRV.setAdapter(courseRVAdapter);

    }

    @Override
    public void onBackPressed() {

        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCourses.this);

        // Instantiate DBHandler
        dbHandler = new DBHandler(ViewCourses.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete all records?");

        // Set Alert Title
        builder.setTitle("Delete All Records");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            dbHandler.massDeleteCourse();
            dialog.dismiss();
            finish();
            startActivity(getIntent());
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
            dialog.dismiss();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void reziseText(View view) {

        name = view.findViewById(R.id.displayName);
        subject = view.findViewById(R.id.displaySubject);
        hours = view.findViewById(R.id.displayHours);
        core = view.findViewById(R.id.displayCore);


        name.setWidth(modal.getScreeWidth() / 4);
        subject.setWidth(modal.getScreeWidth() / 4);
        hours.setWidth(modal.getScreeWidth() / 4);
        core.setWidth(modal.getScreeWidth() / 4);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);


        // below we are passing all our values.

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) ((MenuItem) searchItem).getActionView();

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

                Intent id = new Intent(getApplicationContext(), UpdateCourseActivity.class);
                id.putExtra("update", menu.hasVisibleItems());
                modal.setAdapterStatement(id.getBooleanExtra("update", false));

                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<CourseModal> filteredlist = new ArrayList<CourseModal>();


        // running a for loop to compare elements.
        for (CourseModal item : courseModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStudentName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.

                filteredlist.add(item);
//                filteredlist.get(item.getAdapterId());
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
}