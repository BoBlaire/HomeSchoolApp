package com.example.gfgapp.passwords;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.R;
import com.example.gfgapp.email.GMailSender;
import com.example.gfgapp.modal.MainModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button sendEmail;

    MainModal mainModal = MainModal.getInstance();
    EditText et_to, et_message, et_subject;
    Button btn_send;
    Context c;
    String GMail = "1krackerapp@gmail.com"; //replace with you GMail
    String GMailPass = "oskg pwzy huzb xycu"; // replace with you GMail Password

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        email = findViewById(R.id.userEmail);
        sendEmail = findViewById(R.id.sendEmail);


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });


        sendEmail.setOnClickListener(v -> {

            String str_to = email.getText().toString();

            if (!str_to.equals("")) {


                //Check if 'To:' field is a valid email
                if (isValidEmail(str_to)) {
                    email.setError(null);


                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(c, "Sending... Please wait", Toast.LENGTH_LONG).show();
                        }
                    });
                    String randomNum = getRandomNumberString();

                    Intent i = new Intent(ForgotPassword.this, GoogleVerification.class);
                    startActivity(i);
                    mainModal.setEmail(str_to);
                    mainModal.setGoogleVerification(randomNum);
                    sendEmail(str_to, "Password Reset", "This is your six digit code you will use to authenticate for use of resetting your password. \n\n Your code is: " + randomNum);
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            email.setError("Not a valid email");
                        }
                    });
                }
            } else {
                Toast.makeText(c, "There are empty fields.", Toast.LENGTH_LONG).show();
            }
        });
        c = this;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


    private void sendEmail(final String to, final String subject, final String message) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(GMail,
                            GMailPass);
                    sender.sendMail(subject,
                            message,
                            GMail,
                            to);
                    Log.w("sendEmail", "Email successfully sent!");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(c, "Email successfully sent!", Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (final Exception e) {
                    Log.e("sendEmail", e.getMessage(), e);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(c, "Email not sent. \n\n Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }


    // Check if parameter 'emailAddress' is a valid email
    public final static boolean isValidEmail(CharSequence emailAddress) {
        if (emailAddress == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        }
    }


}