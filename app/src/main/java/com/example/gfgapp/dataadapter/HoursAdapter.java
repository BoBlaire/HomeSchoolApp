package com.example.gfgapp.dataadapter;

import android.os.Build;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.concurrent.CompletableFuture;

public class HoursAdapter {

    private static final String COLLECTION = "homeschool";
    private static final String HOURS_FIELD = "hours";
    private static final String SUBJECT_FIELD = "subject";

    private FirebaseFirestore firestore;

    public HoursAdapter() {
        firestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Double> getTotalHoursBySubjectAndCore(String studentName, String email) {
        CompletableFuture<Double> future;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }
        firestore.collection(COLLECTION)
                .whereEqualTo("name", studentName)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalHours = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.contains(SUBJECT_FIELD) && document.contains(HOURS_FIELD)) {
                                double hours = Double.parseDouble(document.getString(HOURS_FIELD));
                                totalHours += hours;
                            }
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            future.complete(totalHours);
                        }
                    } else {
                        if (task.getException() != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                future.completeExceptionally(task.getException());
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                future.complete(0.0);
                            }
                        }
                    }
                });
        return future;
    }

    public CompletableFuture<Double> getTotalSubjectHours(String name, String subject, String email, String core) {
        CompletableFuture<Double> future;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        } else {
            future = null;
        }
        Query query = firestore.collection(COLLECTION)
                .whereEqualTo("name", name)
                .whereEqualTo("email", email)
                .whereEqualTo("subject", subject)
                .whereEqualTo("core", core);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                double sum = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.contains(HOURS_FIELD)) {
                        sum += Double.parseDouble(document.getString(HOURS_FIELD));
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.complete(sum);
                }
            } else {
                if (task.getException() != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.completeExceptionally(task.getException());
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        future.complete(0.0);
                    }
                }
            }
        });
        return future;
    }
}
