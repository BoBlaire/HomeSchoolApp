package com.example.gfgapp.DEAD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gfgapp.modal.CourseModal;

/*
    Name=Name
    Duration=Subject
    Tracks=Hours
    Description=Core
*/
public class UserDBHandler extends SQLiteOpenHelper {

    /* creating a constant variables for our database.
       below variable is for our database name. */
    private static final String DB_NAME = "users";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "records";
    // below variable is for our user table name.
    private static final String USER_TABLE_NAME = "information";

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

    private static final String EMAIL_COL = "email";

    private static final String PASSWORD_COL = "password";

    private static CourseModal courseModal;


    // creating a constructor for our database handler.
    public UserDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* on below line we are creating
           an sqlite query and we are
           setting our column names
           along with their data types. */
        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COL + " TEXT,"
                + PASSWORD_COL + " TEXT)";

        /* at last we are calling a exec sql
           method to execute above sql query */
        db.execSQL(query);
    }





    public void addUserInfo(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        /* on below line we are creating a
           variable for content values. */
        ContentValues values = new ContentValues();

        /* on below line we are passing all values
           along with its key and value pair. */

        values.put(EMAIL_COL, email);
        values.put(PASSWORD_COL, password);

        /* after adding all values we are passing
           content values to our table. */
        db.insert(USER_TABLE_NAME, null, values);

        /* at last we are closing our
           database after adding database. */
        db.close();
    }

    public void updateCourse(String newPassword, String email) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /* on below line we are passing all values
           along with its key and value pair. */
        values.put(PASSWORD_COL, newPassword);

        /* on below line we are calling a update method to update our database and passing our values.
           and we are comparing it with name of our course which is stored in original name variable. */
        db.update(USER_TABLE_NAME, values, "email=?", new String[]{email});
        db.close();
    }


    public Cursor retrieveEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT email FROM " + USER_TABLE_NAME + " WHERE " + EMAIL_COL + " = ?", new String[]{email});
        cursorCourses.moveToFirst();

        return cursorCourses;
    }


    public Cursor retrievePassword(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT password FROM " + USER_TABLE_NAME + " WHERE " + PASSWORD_COL + " = ?", new String[]{password});
        cursorCourses.moveToFirst();

        return cursorCourses;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}