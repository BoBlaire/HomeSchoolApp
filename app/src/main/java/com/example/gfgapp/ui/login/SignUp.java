package com.example.gfgapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.R;
import com.example.gfgapp.databases.UserDBHandler;
import com.example.gfgapp.modal.MainModal;

public class SignUp extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button button, buttonSignUp;
    MainModal mainModal = MainActivity.mainModal;
    private UserDBHandler userDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        userDBHandler = new UserDBHandler(SignUp.this);


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
                    Toast.makeText(SignUp.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
                    if (strEmail.equals(userEmailLogin)) {
                        Toast.makeText(SignUp.this, "This Email Already In Use", Toast.LENGTH_SHORT).show();
                    } else if (strEmail.equals(userEmailLogin)) {
                        mainModal.setUserEmail(strEmail);
                        userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);

                        Toast.makeText(SignUp.this, "Signed Up!", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUp.this, UserLogin.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(SignUp.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                //Checking weather the login or signup is valid
                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {

                    mainModal.setUserEmail(userEmailLogin);
                    userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);

                    Toast.makeText(SignUp.this, "Signed Up!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(SignUp.this, UserLogin.class);
                    startActivity(i);

                } else {
                    Toast.makeText(SignUp.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}