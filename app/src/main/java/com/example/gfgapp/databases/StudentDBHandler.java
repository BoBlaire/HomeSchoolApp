//package com.example.gfgapp.databases;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.example.gfgapp.modal.CourseModal;
//import com.example.gfgapp.MainActivity;
//import com.example.gfgapp.modal.MainModal;
//import com.example.gfgapp.modal.StudentModal;
//
//import java.util.ArrayList;
//
///*
//    Name=Name
//    Duration=Subject
//    Tracks=Hours
//    Description=Core
//*/
//public class StudentDBHandler extends SQLiteOpenHelper {
//
//    /* creating a constant variables for our database.
//       below variable is for our database name. */
//    private static final String DB_NAME = "hours";
//
//    // below int is our database version
//    private static final int DB_VERSION = 3;
//
//    // below variable is for our table name.
//    private static final String TABLE_NAME = "records";
//    // below variable is for our user table name.
//    private static final String USER_TABLE_NAME = "hourinfo";
//
//    // below variable is for our id column.
//    private static final String ID_COL = "id";
//
//    private static final String NAME_COL = "name";
//
//    private static final String GRADE_COL = "grade";
//
//    private static final String EMAIL_COL = "email";
//    MainModal mainModal = MainActivity.mainModal;
//
////    private Context context = ;
//
//    // below variable is for our student name column
//
//
//    private static CourseModal courseModal;
//
//
//    // creating a constructor for our database handler.
//    public StudentDBHandler(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    // below method is for creating a database by running a sqlite query
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        /* on below line we are creating
//           an sqlite query and we are
//           setting our column names
//           along with their data types. */
//        String query = "CREATE TABLE " + USER_TABLE_NAME + " ("
//                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + NAME_COL + " TEXT,"
//                + GRADE_COL + " TEXT,"
//                + EMAIL_COL + " TEXT)";
//
//        /* at last we are calling a exec sql
//           method to execute above sql query */
//        db.execSQL(query);
//    }
//
//
//    public void addUserInfo(String name, String grade, String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        /* on below line we are creating a
//           variable for content values. */
//        ContentValues values = new ContentValues();
//
//        /* on below line we are passing all values
//           along with its key and value pair. */
//
//        values.put(NAME_COL, name);
//        values.put(GRADE_COL, grade);
//        values.put(EMAIL_COL, email);
//
//        /* after adding all values we are passing
//           content values to our table. */
//        db.insert(USER_TABLE_NAME, null, values);
//
//        /* at last we are closing our
//           database after adding database. */
//        db.close();
//    }
//
//
//    public Cursor retrieveName(String name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursorCourses = db.rawQuery("SELECT name FROM " + USER_TABLE_NAME + " WHERE " + NAME_COL + " = ?", new String[]{name});
//        cursorCourses.moveToFirst();
//
//        return cursorCourses;
//    }
//
//
//    // we have created a new method for reading all the courses.
//    public ArrayList<StudentModal> readStudents(String email) {
//        /* on below line we are creating a
//           database for reading our database. */
//        SQLiteDatabase db = this.getReadableDatabase();
//
//
//        // on below line we are creating a cursor with query to read data from database.
//        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE "+ EMAIL_COL + " = ?", new String[]{email});
//
//        // on below line we are creating a new array list.
//        ArrayList<StudentModal> courseModalArrayList = new ArrayList<>();
//
//        // moving our cursor to first position.
//        if (cursorCourses.moveToFirst()) {
//            do {
//                // on below line we are adding the data from cursor to our array list.
//                courseModalArrayList.add(new StudentModal(
//                        cursorCourses.getString(1)
//
//
//                ));
//            } while (cursorCourses.moveToNext());
//            // moving our cursor to next.
//        }
//        /* at last closing our cursor
//           and returning our array list. */
//        cursorCourses.close();
//        return courseModalArrayList;
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // this method is called to check if the table exists already.
//        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
//        onCreate(db);
//    }
//}