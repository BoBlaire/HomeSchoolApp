package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SplashPage extends AppCompatActivity {

    FloatingActionButton buttonList, buttonAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);

        buttonAdd = findViewById(R.id.buttonAddFloat);
        buttonList = findViewById(R.id.buttonListFloat);


        buttonAdd.setOnClickListener(v -> {
            Intent i = new Intent(SplashPage.this, MainActivity.class);
            startActivity(i);
        });


        buttonList.setOnClickListener(v -> {
            Intent i = new Intent(SplashPage.this, ViewCourses.class);
            startActivity(i);
        });
    }
}
