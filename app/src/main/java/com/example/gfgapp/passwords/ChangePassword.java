package com.example.gfgapp.passwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.R;
import com.example.gfgapp.databases.*;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.ui.login.UserLogin;

public class ChangePassword extends AppCompatActivity {
    Button button;
    EditText newPassword;
    private UserDBHandler dbHandler;
    MainModal mainModal = MainModal.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        button = findViewById(R.id.reset);
        newPassword = findViewById(R.id.newPassword);

        dbHandler = new UserDBHandler(ChangePassword.this);

        String email = getIntent().getStringExtra("email");

        button.setOnClickListener(v -> {
            System.out.println("email: "+email);
            String password = newPassword.getText().toString();
            if (!password.trim().equals("")) {
                dbHandler.updateCourse(password, mainModal.getEmail());
                Intent i = new Intent(ChangePassword.this, UserLogin.class);
                startActivity(i);
            }
        });

    }
}