package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
    Name=Name
    Duration=Subject
    Tracks=Hours
    Description=Core
*/
class DBHandler extends SQLiteOpenHelper {

    /* creating a constant variables for our database.
       below variable is for our database name. */
    private static final String DB_NAME = "homeschool";

    // below int is our database version
    private static final int DB_VERSION = 3;

    // below variable is for our table name.
    private static final String TABLE_NAME = "records";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our student name column
    private static final String NAME_COL = "name";

    // below variable id for our student subject column.
    private static final String SUBJECT_COL = "subject";

    // below variable for our student core column.
    private static final String CORE_COL = "core";

    // below variable is for our student hours column.
    private static final String HOURS_COL = "hours";

    private static final String DATE_COL = "date";

    private static CourseModal courseModal;


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* on below line we are creating
           an sqlite query and we are
           setting our column names
           along with their data types. */
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + SUBJECT_COL + " TEXT,"
                + HOURS_COL + " TEXT,"
                + CORE_COL + " TEXT,"
                + DATE_COL + " TEXT)";

        /* at last we are calling a exec sql
           method to execute above sql query */
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String studentName, String studentSubject, String studentHours, String studentCore, String date) {

        /* on below line we are creating a variable for
           our sqlite database and calling writable method
           as we are writing data in our database. */
        SQLiteDatabase db = this.getWritableDatabase();

        /* on below line we are creating a
           variable for content values. */
        ContentValues values = new ContentValues();

        /* on below line we are passing all values
           along with its key and value pair. */
        values.put(NAME_COL, studentName);
        values.put(SUBJECT_COL, studentSubject);
        values.put(CORE_COL, studentCore);
        values.put(HOURS_COL, studentHours);
        values.put(DATE_COL, date);


        /* after adding all values we are passing
           content values to our table. */
        db.insert(TABLE_NAME, null, values);

        /* at last we are closing our
           database after adding database. */
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<CourseModal> readCourses() {
        /* on below line we are creating a
           database for reading our database. */
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<CourseModal> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new CourseModal(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4)
                ));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        /* at last closing our cursor
           and returning our array list. */
        cursorCourses.close();
        return courseModalArrayList;
    }

    // below is the method for updating our courses
    public void updateCourse(String orgStudentName, String studentName, String studentSubject,
                             String studentHours, String studentCore) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /* on below line we are passing all values
           along with its key and value pair. */
        values.put(NAME_COL, studentName);
        values.put(SUBJECT_COL, studentSubject);
        values.put(HOURS_COL, studentHours);
        values.put(CORE_COL, studentCore);



        /* on below line we are calling a update method to update our database and passing our values.
           and we are comparing it with name of our course which is stored in original name variable. */
        db.update(TABLE_NAME, values, "id=?", new String[]{orgStudentName});
        db.close();
    }


    // below is the method for deleting our course.
    public void deleteCourse(String studentName) {

        /* on below line we are creating
           a variable to write our database. */
        SQLiteDatabase db = this.getWritableDatabase();

        /* on below line we are calling a method to delete our
           course and we are comparing it with our course name. */
        db.delete(TABLE_NAME, "id=?", new String[]{studentName});
        db.close();
    }

    public void massDeleteCourse() {

        /* on below line we are creating
           a variable to write our database. */
        SQLiteDatabase db = this.getWritableDatabase();

        /* on below line we are calling a method to delete all records
           and we are comparing it with our course name. */

        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }


    public Cursor getDbId(String studentName) {

        SQLiteDatabase db = this.getWritableDatabase();

//        Cursor getDb = (db.rawQuery("SELECT " + ID_COL + " FROM " + TABLE_NAME + " WHERE " + NAME_COL + " = ?", new String[]{studentName}));
        Cursor getDbId = (db.rawQuery("SELECT * FROM " + TABLE_NAME, null));

        getDbId.moveToFirst();
        return getDbId;
    }

    public Cursor getDbIdTrue(String studentName) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getDbId = (db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME_COL + " = ?", new String[]{studentName}));

        getDbId.moveToFirst();
        return getDbId;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}