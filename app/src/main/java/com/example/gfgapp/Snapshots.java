package com.example.gfgapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gfgapp.dataadapter.HoursAdapter;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.SubjectModal;
import com.example.gfgapp.pdf.CustomAlertDialog;
import com.example.gfgapp.pdf.PdfGenerator;

import java.util.concurrent.CompletableFuture;

public class Snapshots extends AppCompatActivity {
    private TableRow trHistory, trMath, trEnglish, trScience;

    private Button addRecord, buttonReports, buttonHome;
    private TextView name, sciHours, mathHours, histHours, elaHours;
    static private double sci, math, hist, ela;
    MainModal mainModal = MainActivity.mainModal;

    SubjectModal subs = SubjectModal.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snapshots);

        trEnglish = findViewById(R.id.trEnglish);
        trMath = findViewById(R.id.trMath);
        trHistory = findViewById(R.id.trHistory);
        trScience = findViewById(R.id.trScience);
        name = findViewById(R.id.tvTitle);
        name.setText(mainModal.getUserName());
        addRecord = findViewById(R.id.btnAddRecord);
        buttonReports = findViewById(R.id.btnReports);
        buttonHome = findViewById(R.id.btnHome);
        sciHours = findViewById(R.id.scienceHours);
        mathHours = findViewById(R.id.mathHours);
        histHours = findViewById(R.id.historyHours);
        elaHours = findViewById(R.id.englishHours);

        sciHours.setText(String.valueOf(sci));
        mathHours.setText(String.valueOf(math));
        histHours.setText(String.valueOf(hist));
        elaHours.setText(String.valueOf(ela));


        trEnglish.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, ViewCourses.class);
//            id.putExtra("subjects", "English");
            subs.setSubject("English");
            startActivity(id);
        });

        trMath.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, ViewCourses.class);
//            id.putExtra("subjects", "Math");
            subs.setSubject("Math");
            startActivity(id);
        });

        trHistory.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, ViewCourses.class);
//            id.putExtra("subjects", "History");
            subs.setSubject("History");
            startActivity(id);
        });

        trScience.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, ViewCourses.class);
//            id.putExtra("subjects", "Science");
            subs.setSubject("Science");
            startActivity(id);
        });


        addRecord.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, MainActivity.class);
            startActivity(id);
        });

        buttonHome.setOnClickListener(v -> {
            Intent id = new Intent(Snapshots.this, StudentView.class);
            startActivity(id);
        });

        buttonReports.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                onBackPressed();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBackPressed() {
        HoursAdapter hoursAdapter = new HoursAdapter();
        String userName = mainModal.getUserName();
        String userEmail = mainModal.getUserEmail();

        try {
            CompletableFuture<Double> mathCoreFuture = hoursAdapter.getTotalSubjectHours(userName, "Math", userEmail, "Yes");
            CompletableFuture<Double> mathFuture = mathCoreFuture.thenCompose(mathCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "Math", userEmail, "No")
            );

            CompletableFuture<Double> englishCoreFuture = mathFuture.thenCompose(math ->
                    hoursAdapter.getTotalSubjectHours(userName, "English", userEmail, "Yes")
            );
            CompletableFuture<Double> englishFuture = englishCoreFuture.thenCompose(englishCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "English", userEmail, "No")
            );

            CompletableFuture<Double> scienceCoreFuture = englishFuture.thenCompose(english ->
                    hoursAdapter.getTotalSubjectHours(userName, "Science", userEmail, "Yes")
            );
            CompletableFuture<Double> scienceFuture = scienceCoreFuture.thenCompose(scienceCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "Science", userEmail, "No")
            );

            CompletableFuture<Double> historyCoreFuture = scienceFuture.thenCompose(science ->
                    hoursAdapter.getTotalSubjectHours(userName, "History", userEmail, "Yes")
            );
            CompletableFuture<Double> historyFuture = historyCoreFuture.thenCompose(historyCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "History", userEmail, "No")
            );

            CompletableFuture<Double> peCoreFuture = historyFuture.thenCompose(history ->
                    hoursAdapter.getTotalSubjectHours(userName, "Pe", userEmail, "Yes")
            );
            CompletableFuture<Double> peFuture = peCoreFuture.thenCompose(peCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "Pe", userEmail, "No")
            );

            CompletableFuture<Double> extraCoreFuture = peFuture.thenCompose(pe ->
                    hoursAdapter.getTotalSubjectHours(userName, "Extracurriculars", userEmail, "Yes")
            );
            CompletableFuture<Double> extraFuture = extraCoreFuture.thenCompose(extraCore ->
                    hoursAdapter.getTotalSubjectHours(userName, "Extracurriculars", userEmail, "No")
            );
            sci = sci + scienceCoreFuture.join();
            math = math + mathCoreFuture.join();
            hist = hist + historyCoreFuture.join();
            ela = ela + englishCoreFuture.join();

            extraFuture.thenCompose(extra ->
                    hoursAdapter.getTotalHoursBySubjectAndCore(userName, userEmail)
            ).thenAccept(totalHours -> {
                String filePath = getFilesDir().getPath() + "/email.pdf";
                PdfGenerator.createPDF(filePath, userName,
                        mathCoreFuture.join(), mathFuture.join(),
                        englishCoreFuture.join(), englishFuture.join(),
                        scienceCoreFuture.join(), scienceFuture.join(),
                        historyCoreFuture.join(), historyFuture.join(),
                        peCoreFuture.join(), peFuture.join(),
                        extraCoreFuture.join(), extraFuture.join(),
                        totalHours);
            }).exceptionally(ex -> {
                System.out.println("Well Shit: " + ex);
                return null;
            });

            CustomAlertDialog alertDialog = new CustomAlertDialog();
            alertDialog.showCustomDialog(Snapshots.this);
        } catch (Exception e) {
            System.out.println("Well Shit: " + e);
        }
    }


}