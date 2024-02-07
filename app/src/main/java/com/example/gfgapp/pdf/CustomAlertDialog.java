package com.example.gfgapp.pdf;

import static com.example.gfgapp.MainActivity.mainModal;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gfgapp.R;
import com.example.gfgapp.email.SendEmail;

public class CustomAlertDialog {

    public void showCustomDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.alert, null);
        builder.setView(dialogView);

        SendEmail sendEmail = new SendEmail();

        // Get views from custom layout
        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        Button positiveButton = dialogView.findViewById(R.id.positiveButton);
        Button negativeButton = dialogView.findViewById(R.id.negativeButton);

        // Customize views as needed (e.g., set text, onClick listeners)
        titleTextView.setText("This will send a pdf file with all your child's hours, and a projected year end.");

        AlertDialog alertDialog = builder.create();

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle positive button click
                // For example, get text from editText
                sendEmail.sendEmail(mainModal.getUserEmail(), mainModal.getUserName(), context);
                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle negative button click
                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }
}
