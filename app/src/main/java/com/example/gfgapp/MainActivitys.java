//package com.example.gfgapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import android.os.Bundle;
//
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class MainActivitys extends AppCompatActivity {
//
//    // creating variables for our edittext, button and dbhandler
//
//    private Button addCourseBtn, readCourseBtn;
//    private DBHandler dbHandler;
//    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
//    private RecyclerView courseRV;
//    private CourseRVAdapter courseRVAdapter;
//    // creating variables for
//    // our ui components.
//
//
//    // variable for our adapter
//    // class and array list
//    private CourseRVAdapter adapter;
//    private ArrayList<CourseModal> courseModalArrayList;
//
//
//    // variable for our adapter
//    // class and array list
//    // private CourseRVAdapter adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//// initializing our variables.
//        courseRV = findViewById(R.id.idRVCourses);
//
//        // calling method to
//        // build recycler view.
//        //   buildRecyclerView();
//
//        // initializing our variables.
//        //  courseRV = findViewById(R.id.idRVCourses);
//
//        // calling method to
//        // build recycler view.
//
//
//        // initializing all our variables.
//        courseNameEdt = findViewById(R.id.idEdtCourseName);
//        courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
//        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
//        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
//        addCourseBtn = findViewById(R.id.idBtnAddCourse);
//        readCourseBtn = findViewById(R.id.idBtnReadCourse);
//
//
//        dbHandler = new DBHandler(MainActivitys.this);
//
//
//        // below line is to add on click listener for our add course button.
//        addCourseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // below line is to get data from all edit text fields.
//                String courseName = courseNameEdt.getText().toString();
//                String courseTracks = courseTracksEdt.getText().toString();
//                String courseDuration = courseDurationEdt.getText().toString();
//                String courseDescription = courseDescriptionEdt.getText().toString();
//
//                // validating if the text fields are empty or not.
//                if (courseName.isEmpty() && courseTracks.isEmpty() && courseDuration.isEmpty() && courseDescription.isEmpty()) {
//                    Toast.makeText(MainActivitys.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // on below line we are calling a method to add new
//                // course to sqlite data and pass all our values to it.
//                dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks);
//
//                // after adding the data we are displaying a toast message.
//                Toast.makeText(MainActivitys.this, "Course has been added.", Toast.LENGTH_SHORT).show();
//                courseNameEdt.setText("");
//                courseDurationEdt.setText("");
//                courseTracksEdt.setText("");
//                courseDescriptionEdt.setText("");
//            }
//        });
//
//
////        readCourseBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // opening a new activity via a intent.
////                Intent i = new Intent(MainActivitys.this, ViewCourses.class);
////                startActivity(i);
////            }
////        });
//
//
//
//
//
//    }
//    //
////     calling on create option menu
////     layout to inflate our menu file.
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        courseModalArrayList = dbHandler.readCourses();
////        // setContentView(R.layout.activity_view_courses);
////        // below line is to get our inflater
////        MenuInflater inflater = getMenuInflater();
////
////        // inside inflater we are inflating our menu file.
////        inflater.inflate(R.menu.search_menu, menu);
////
////        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
////            @Override
////            public boolean onMenuItemActionExpand(MenuItem menuItem) {
////                return true;
////            }
////
////            @Override
////            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
////                return true;
////            }
////        };
////
////        // below line is to get our menu item.
////        //   MenuItem searchItem = menu.findItem(R.id.actionSearch);
////
////        // getting search view of our item.eee
////        menu.findItem(R.id.actionSearch).setOnActionExpandListener(onActionExpandListener);
////        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
////
////        // below line is to call set on query text listener method.
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String newText) {
////                // inside on query text change method we are
////                // calling a method to filter our recycler view.
////                filter(newText);
////                return false;
////            }
////        });
////        return true;
////    }
////
////    private void filter(String text) {
////        courseModalArrayList = dbHandler.readCourses();
////        // creating a new array list to filter our data.
////        ArrayList<CourseModal> filteredlist = new ArrayList<>();
////
////        // running a for loop to compare elements.
////        for (CourseModal item : courseModalArrayList) {
////            // checking if the entered string matched with any item of our recycler view.
////            if (item.getCourseName().toLowerCase().contains(text.toLowerCase())) {
////                // if the item is matched we are
////                // adding it to our filtered list.
////                filteredlist.add(item);
////            }
////        }
////        if (filteredlist.isEmpty()) {
////            // if no item is added in filtered list we are
////            // displaying a toast message as no data found.
////            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
////        } else {
////            // at last we are passing that filtered
////            // list to our adapter class.
////            adapter.filterList(filteredlist);
////        }
////    }
//
//
//}