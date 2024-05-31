package com.example.gfgapp.email;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.StudentAdapter;
import com.example.gfgapp.StudentView;
import com.example.gfgapp.ViewCourses;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.passwords.ForgotPassword;
import com.example.gfgapp.passwords.GoogleVerification;

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

public class SendEmail {

    MainModal mainModal = MainModal.getInstance();

    String GMail = "1krackerapp@gmail.com"; //replace with you GMail
    String GMailPass = "oskg pwzy huzb xycu"; // replace with you GMail Password

    public void sendEmail(String to, String name, Context context) {





        //Check if 'To:' field is a valid email
        if (isValidEmail(to)) {

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.w("Error", "Error Occurred");
                }
            });
            sendEmail(to, name+"'s Hours", "this is ur pdf", context);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private void sendEmail(final String to, final String subject, final String message, Context context) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    PDFSender sender = new PDFSender(GMail,
                            GMailPass);
                    sender.sendMail(subject,
                            message,
                            GMail,
                            to, context.getFilesDir().getPath() + "/email.pdf");
                    Log.w("sendEmail", "Email successfully sent!");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            Log.w("sendEmail", "Email successfully sent!");
                        }
                    });

                } catch (final Exception e) {
                    Log.e("sendEmail", e.getMessage(), e);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.w("notSent", "Email not sent. \n\n Error: " + e.getMessage().toString());
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
