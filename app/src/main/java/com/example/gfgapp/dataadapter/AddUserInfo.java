package com.example.gfgapp.dataadapter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.YourData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddUserInfo {

    /**
     * Enters user data into Firestore.
     * @param email    The user's email.
     * @param password The user's password.
     * @param uid      The user's unique ID.
     */
    public void enterData(String email, String password, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user data map
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("uid", uid);

        DocumentReference documentReference = db.collection("users").document(uid);

        // Add a new document with the specified ID
        documentReference.set(user).addOnSuccessListener((OnSuccessListener<Object>) o -> {
            // Successfully added the document
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add the document
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Retrieves user data from Firestore.
     * @param userEmail    The user's email.
     * @param userPassword The user's password.
     */
    public void retrieveData(String userEmail, String userPassword) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionRef = db.collection("homeschool");

        // Create a query to find documents matching the email and password
        Query query = collectionRef
                .whereEqualTo("email", userEmail)
                .whereEqualTo("password", userPassword);

        query.get().addOnCompleteListener(task -> {
            ArrayList<CourseModal> arrayValue = new ArrayList<>();
            if (task.isSuccessful()) {
                // Loop through each document in the query result
                for (QueryDocumentSnapshot document : task.getResult()) {
                    YourData data = document.toObject(YourData.class);

                    // Extract and organize the document data
                    String name = data.getName();
                    String subject = data.getSubject();
                    String hours = data.getHours();
                    String core = data.getCore();
                    String email = data.getEmail();
                    String date = data.getDf();
                    String desc = data.getDescription();
                    String docId = document.getId();

                    // Add the data to the list
                    arrayValue.add(new CourseModal(name, subject, hours, core, desc, docId, date));
                }
            } else {
                // Handle any errors that occurred during the query
                Log.e("FirestoreQuery", "Error getting documents: ", task.getException());
            }
        });
    }

}
