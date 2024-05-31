package com.example.gfgapp.passwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.R;
import com.example.gfgapp.databases.DBHandler;
import com.example.gfgapp.modal.MainModal;

public class GoogleVerification extends AppCompatActivity {

    private DBHandler dbHandler;
    EditText box1c, box2c, box3c, box4c, box5c, box6c;
    Button submit;
    MainModal mainModal = MainModal.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);

        submit = findViewById(R.id.submit_button);
        box1c = findViewById(R.id.box1);
        box2c = findViewById(R.id.box2);
        box3c = findViewById(R.id.box3);
        box4c = findViewById(R.id.box4);
        box5c = findViewById(R.id.box5);
        box6c = findViewById(R.id.box6);

        dbHandler = new DBHandler(GoogleVerification.this);
        String key = mainModal.getGoogleVerification();

        submit.setOnClickListener(v -> {
            if (box1c.getText().toString().equals(key.substring(0, 1)) && box2c.getText().toString().equals(key.substring(1, 2)) && box3c.getText().toString().equals(key.substring(2, 3)) && box4c.getText().toString().equals(key.substring(3, 4)) && box5c.getText().toString().equals(key.substring(4, 5)) && box6c.getText().toString().equals(key.substring(5, 6))) {
                Intent i = new Intent(GoogleVerification.this, ChangePassword.class);

                startActivity(i);
            }
        });
    }
}