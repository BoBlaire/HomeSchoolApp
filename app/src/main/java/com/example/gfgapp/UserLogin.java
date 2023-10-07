package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserLogin extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button button, buttonSignUp;
    MainModal mainModal = MainActivity.mainModal;
    private UserDBHandler userDBHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //creating variables
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        button = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        //calling user database
        userDBHandler = new UserDBHandler(UserLogin.this);

        //click listener to login
        button.setOnClickListener(v -> {

            //getting text of our two fields for database
            String userEmailLogin = userEmail.getText().toString();
            String userPasswordLogin = userPassword.getText().toString();

            //passing those two fields and getting our cursor back
            Cursor cursorEmail = userDBHandler.retrieveEmail(userEmailLogin);
            Cursor cursorPass = userDBHandler.retrievePassword(userPasswordLogin);

            //try statement for login, it trys to login if not the catch says no email
            try {

                //passing cursors to get an actual variable back
                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));
                @SuppressLint("Range") String strPass = cursorPass.getString(cursorPass.getColumnIndex("password"));

                //Checking weather the login or signup is valid
                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
                    if (strEmail.equals(userEmailLogin) && strPass.equals(userPasswordLogin)) {
                        mainModal.setUserEmail(strEmail);
                        Toast.makeText(UserLogin.this, "Logged In!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserLogin.this, SplashPage.class);
                        startActivity(i);
                    } else if (!strEmail.equals(userEmailLogin) || !strPass.equals(userPasswordLogin)) {
                        Toast.makeText(UserLogin.this, "Email Or Password Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(UserLogin.this, "No Account Attached To Email", Toast.LENGTH_SHORT).show();
            }
        });

        //click listener for signup
        buttonSignUp.setOnClickListener(v -> {

            //getting text for cursor
            String userEmailLogin = userEmail.getText().toString();
            String userPasswordLogin = userPassword.getText().toString();

            //passing text to cursor
            Cursor cursorEmail = userDBHandler.retrieveEmail(userEmailLogin);
            Cursor cursorPass = userDBHandler.retrievePassword(userPasswordLogin);

            //trying to signup, and if theres an email already valid the try says email in use
            try {
                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));

                //Checking weather the login or signup is valid
                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
                    if (strEmail.equals(userEmailLogin)) {
                        Toast.makeText(UserLogin.this, "This Email Already In Use", Toast.LENGTH_SHORT).show();
                    } else if (strEmail.equals(userEmailLogin)) {
                        mainModal.setUserEmail(strEmail);
                        userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);

                        Toast.makeText(UserLogin.this, "Signed Up!", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(UserLogin.this, SplashPage.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                //Checking weather the login or signup is valid
                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {

                    mainModal.setUserEmail(userEmailLogin);
                    userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);

                    Toast.makeText(UserLogin.this, "Signed Up!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(UserLogin.this, SplashPage.class);
                    startActivity(i);

                } else {
                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
