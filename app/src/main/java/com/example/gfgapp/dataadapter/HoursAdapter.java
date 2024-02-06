package com.example.gfgapp.dataadapter;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HoursAdapter {

    private static final String COLLECTION = "homeschool";


    private static final String HOURS_FIELD = "hours";

    private static final String SUBJECT_FIELD = "subject";

    private FirebaseFirestore firestore;

    public HoursAdapter() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void getTotalHoursBySubjectAndCore(String studentName, String email, FirestoreCall callback) {
        firestore.collection(COLLECTION)
                .whereEqualTo("name", studentName)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalHours = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.contains(SUBJECT_FIELD) && document.contains(HOURS_FIELD)) {
                                String subject = document.getString(SUBJECT_FIELD);
                                double hours = Double.parseDouble(document.getString(HOURS_FIELD));

                                // Sum the hours for each subject
                                totalHours += hours;
                            }
                        }

                        // Callback to return the result
                        callback.onCall(totalHours);
                    } else {
                        // Handle errors
                        Exception e = task.getException();
                        if (e != null) {
                            e.printStackTrace();
                        }
                        callback.onCall(0); // Return 0 for failure
                    }
                });
    }
    public interface FirestoreCall {
        void onCall(double result);
    }


    public void getTotalSubjectHours(String name, String subject, String email, String core, FirestoreCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Construct the query to filter documents
        Query query = db.collection("homeschool")
                .whereEqualTo("name", name)
                .whereEqualTo("email", email)
                .whereEqualTo("subject", subject)
                .whereEqualTo("core", core);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                double sum = 0;

                // Iterate through the result documents and calculate the sum
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.contains("hours")) {
                        sum += Double.parseDouble(document.getString("hours"));
                    }
                }

                // Callback to return the result
                callback.onCallback(sum);

            } else {
                // Handle errors
                Exception e = task.getException();
                if (e != null) {
                    e.printStackTrace();
                }
                callback.onCallback(0); // Return 0 for failure
            }
        });
    }

    // Define a callback interface
      public interface FirestoreCallback {
        void onCallback(double result);
    }

}
