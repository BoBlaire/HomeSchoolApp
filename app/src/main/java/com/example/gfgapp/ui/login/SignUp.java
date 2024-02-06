package com.example.gfgapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.R;
import com.example.gfgapp.dataadapter.AddUserInfo;
import com.example.gfgapp.databases.UserDBHandler;
import com.example.gfgapp.modal.MainModal;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class SignUp extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button button, buttonSignUp;
    MainModal mainModal = MainActivity.mainModal;
    private UserDBHandler userDBHandler;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        userDBHandler = new UserDBHandler(SignUp.this);

        buttonSignUp.setOnClickListener(v -> {
            signUp();
        });
    }

    public void signUp() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email is already in use
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result.getSignInMethods().isEmpty()) {
                                // Email is not in use, proceed with account creation
                                createAccount(email, password);
                            } else {
                                // Email is already in use
                                Toast.makeText(SignUp.this, "Email is already in use", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error checking email availability
                            Toast.makeText(SignUp.this, "Error checking email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createAccount(String email, String password) {
      AddUserInfo addInfo = new AddUserInfo();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Account creation successful
                        Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity or perform necessary actions
                        FirebaseUser user = task.getResult().getUser();

                        Intent i = new Intent(SignUp.this, UserLogin.class);
                        startActivity(i);
                        // Save additional user information to Firestore
                      addInfo.enterData(email, password, user.getUid());
                    } else {
                        // If account creation fails, display a message to the user.
                        Toast.makeText(SignUp.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}