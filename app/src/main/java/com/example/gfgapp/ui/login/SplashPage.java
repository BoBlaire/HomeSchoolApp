package com.example.gfgapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.example.gfgapp.R;

public class SplashPage extends AppCompatActivity {
    private Button btnLogin, btnTryMe, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);


        btnLogin = findViewById(R.id.btnLogin);
        btnTryMe = findViewById(R.id.btnTryMe);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(SplashPage.this, UserLogin.class);
            startActivity(i);
        });

        btnTryMe.setOnClickListener(v -> {

        });

        btnSignUp.setOnClickListener(v -> {
            Intent i = new Intent(SplashPage.this, SignUp.class);
            startActivity(i);
        });


        TextView textView = findViewById(R.id.tvTitle);
        String sentence = "Home School Helper";

        // Split the sentence into words
        String[] words = sentence.split(" ");

        // Create a SpannableString from the sentence
        SpannableString spannableString = new SpannableString(sentence);

        int start = 0;
        // Loop through each word and apply a different color
        for (String word : words) {
            int end = start + word.length();
            // Apply a random color to each word
            spannableString.setSpan(new ForegroundColorSpan(getRandomColor()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1; // Move to the next word (+1 for the space)
        }

        // Set the SpannableString to the TextView
        textView.setText(spannableString);
    }

    // Generate a random color
    private int getRandomColor() {
        int[] colors = {
                ContextCompat.getColor(this, R.color.accent_blue),
                ContextCompat.getColor(this, R.color.accent_orange),
                ContextCompat.getColor(this, R.color.accent_red),
                ContextCompat.getColor(this, R.color.purple_500),
                ContextCompat.getColor(this, R.color.purple_200)
        };
        return colors[(int) (Math.random() * colors.length)];
    }
}