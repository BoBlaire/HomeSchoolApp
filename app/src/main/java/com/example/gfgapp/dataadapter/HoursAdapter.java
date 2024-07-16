package com.example.gfgapp.dataadapter;

import android.os.Build;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HoursAdapter {

    // Firestore collection and field names
    private static final String COLLECTION = "homeschool";
    private static final String HOURS_FIELD = "hours";
    private static final String SUBJECT_FIELD = "subject";

    private final FirebaseFirestore firestore;

    // Constructor initializes Firestore instance
    public HoursAdapter() {
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Retrieves total hours by subject and core for a given student.
     * @param studentName The student's name.
     * @param email       The student's email.
     * @return A CompletableFuture containing the total hours.
     */
    public CompletableFuture<Double> getTotalHoursBySubjectAndCore(String studentName, String email) {
        CompletableFuture<Double> future;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }

        // Query Firestore for documents matching the student's name and email
        firestore.collection(COLLECTION)
                .whereEqualTo("name", studentName)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalHours = 0;
                        // Iterate through query results
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.contains(SUBJECT_FIELD) && document.contains(HOURS_FIELD)) {
                                // Accumulate total hours
                                double hours = Double.parseDouble(Objects.requireNonNull(document.getString(HOURS_FIELD)));
                                totalHours += hours;
                            }
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            future.complete(totalHours); // Complete the future with total hours
                        }
                    } else {
                        // Handle query failure
                        if (task.getException() != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                future.completeExceptionally(task.getException()); // Complete the future with an exception
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                future.complete(0.0); // Complete the future with 0 hours if no exception
                            }
                        }
                    }
                });
        return future;
    }

    /**
     * Retrieves total subject hours for a given student, subject, and core.
     * @param name    The student's name.
     * @param subject The subject.
     * @param email   The student's email.
     * @param core    The core.
     * @return A CompletableFuture containing the total subject hours.
     */
    public CompletableFuture<Double> getTotalSubjectHours(String name, String subject, String email, String core) {
        CompletableFuture<Double> future;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }

        // Query Firestore for documents matching the student's name, email, subject, and core
        Query query = firestore.collection(COLLECTION)
                .whereEqualTo("name", name)
                .whereEqualTo("email", email)
                .whereEqualTo("subject", subject)
                .whereEqualTo("core", core);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                double sum = 0;
                // Iterate through query results
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.contains(HOURS_FIELD)) {
                        // Accumulate total hours
                        sum += Double.parseDouble(Objects.requireNonNull(document.getString(HOURS_FIELD)));
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.complete(sum); // Complete the future with total hours
                }
            } else {
                // Handle query failure
                if (task.getException() != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.completeExceptionally(task.getException()); // Complete the future with an exception
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(0.0); // Complete the future with 0 hours if no exception
                    }
                }
            }
        });
        return future;
    }
}
